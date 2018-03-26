package ru.cwl.otus.hw16.msghub;

import connector.Connector;
import connector.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by vadim.tishenko
 * on 25.03.2018 16:43.
 * <p>
 * маршрутизирует сообщения.
 * не принимает и не передает.
 */
public class MsgRouter {

    private final Logger log = LoggerFactory.getLogger(MsgRouter.class);

    private ConcurrentMap<String, BlockingQueue<Message>> map = new ConcurrentHashMap<>();
    private BlockingQueue<Message> defQueue = new LinkedBlockingQueue<>();
    private ExecutorService exec = Executors.newCachedThreadPool();

    MsgRouter() {
        exec.execute(() -> {
            Thread.currentThread().setName("def-queue");
            try {
                while (true) {
                    Message msg = defQueue.take();
                    log.warn("queue {} not found! q.size: {}", msg.getTo(), defQueue.size());
                }
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
    }

    void route(Message message) {
        String to = message.getTo();
        try {
            map.getOrDefault(to, defQueue).put(message);
        } catch (InterruptedException e) {
            log.error("to: " + to, e);
        }
    }

    void attach(Connector conn) {
        String address = conn.getAddress();
        BlockingQueue<Message> msgQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> res = map.putIfAbsent(address, msgQueue);
        if (null != res) {
            log.warn("queue for address {} already registered", address);
            throw new RuntimeException("address already registered");
        }
        conn.onReceive(this::route);
        exec.execute(() -> {
            Thread.currentThread().setName("send-" + address);
            try {
                while (true) {
                    Message msg = msgQueue.take();
                    if (!conn.isOn()) {
                        BlockingQueue<Message> q = map.remove(address);
                        q.clear();
                        log.info("queue {} removed.", address);
                        break;
                    }
                    conn.send(msg);
                }
                log.info("send-" + address + "thread closiing...");
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        log.info("attach addr:{} conn:{}", address, conn);
    }

}

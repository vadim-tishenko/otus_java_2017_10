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
    final Logger log = LoggerFactory.getLogger(MsgRouter.class);
    ConcurrentMap<String, BlockingQueue<Message>> map = new ConcurrentHashMap<String, BlockingQueue<Message>>();
    BlockingQueue<Message> defQueue = new LinkedBlockingQueue<Message>();
    ExecutorService exec = Executors.newCachedThreadPool();

    MsgRouter() {
        exec.execute(() -> {
            Thread.currentThread().setName("def-queue");
            try {
                while (true) {
                    Message msg = defQueue.take();
                    log.warn("queue {} not found!", msg.getTo());
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
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> res = map.putIfAbsent(address, queue);
        if (null != res) {
            log.warn("queue for address {} already registered", address);
            throw new RuntimeException("address already registered");
        }
        conn.onReceive(this::route);
        exec.execute(() -> {
            Thread.currentThread().setName("send-" + address);
            try {
                while (true) {
                    Message msg = queue.take();
                    conn.send(msg);
                }
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        log.info("attach addr:{} conn:{}", address, conn);

    }
}

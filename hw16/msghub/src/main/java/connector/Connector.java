package connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by vadim.tishenko
 * on 25.03.2018 19:27.
 */
public class Connector {
    private Logger log = LoggerFactory.getLogger(Connector.class);

    private static ConcurrentHashMap<String, Class<?>> classMap;
    private final static ExecutorService exec;
    private static ObjectMapper mapper;

    static {
        classMap = new ConcurrentHashMap<>();
        exec = Executors.newCachedThreadPool();
        mapper = new ObjectMapper();
    }

    private Socket socket;
    private String address;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<Message> msgConsumer;
    private final ConnType type;
    private volatile boolean on = true;

    // server side
    public Connector(Socket socket) {
        type = ConnType.SERVER;
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            address = in.readLine();
            in.readLine();

        } catch (IOException e) {
            log.error("", e);
        }

    }

    // client side
    public Connector(Socket socket, String address) {
        type = ConnType.CLIENT;
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.address = address;
            out.println(address);
            out.println();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public String getAddress() {
        return address;
    }

    public void send(Message msg) {
        out.println(msg.getClass().getName());
        out.println(msg2json(msg));
        out.println();
    }

    public void close() {
        try {
            if (type == ConnType.CLIENT) {
                out.println("bye.");
                out.println();
                out.flush();
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            log.error("", e);
        }
        out.close();
    }

    public void onReceive(Consumer<Message> c) {
        msgConsumer = c;
        exec.execute(() -> {
            Thread.currentThread().setName("receive-" + address);
            try {
                while (true) {
                    String className = in.readLine();
                    if (type == ConnType.SERVER) {
                        if (className.equals("bye.")) {

                            // выписаться из адресной книги
                            // убрать обработчик
                            log.info("bye received.");

                            on = false;
                            // send unlock queue msg
                            msgConsumer.accept(new Message() {
                                @Override
                                public String getFrom() {
                                    return null;
                                }

                                @Override
                                public String getTo() {
                                    return address;
                                }
                            });

                            out.close();
                            in.close();
                            socket.close();

                            log.info("conn closed");
                            break;
                        }
                    }

                    Class<?> clazz = classMap.get(className);
                    if (clazz == null) {
                        clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                        classMap.put(className, clazz);
                    }

                    String json = in.readLine();
                    String eol = in.readLine();

                    Object o = json2msg(clazz, json);
                    Message msg = (Message) o;
                    msgConsumer.accept(msg);

                }
            } catch (ClassNotFoundException | IOException e) {
                log.error("", e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        });
    }

    private String msg2json(Message msg) {
        try {
            return mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        return "{}";
    }

    private Object json2msg(Class<?> clazz, String json) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public boolean isOn() {
        return on;
    }

    enum ConnType {
        CLIENT,
        SERVER;

        void close() {
        }
    }
}

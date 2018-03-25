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
    private static ConcurrentHashMap<String, Class<?>> classMap = new ConcurrentHashMap<>();

    private Socket socket;
    private String address;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();
    private Consumer<Message> msgConsumer;
    private final ExecutorService exec;

    public Connector(Socket socket) {
        exec = Executors.newCachedThreadPool();
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

    public Connector(Socket socket, String address) {
        exec = Executors.newCachedThreadPool();
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

    void close() {
        try {
            out.println("bye.");
            out.println();
            out.flush();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (className.equals("bye.")) {
                        // выписаться из адресной книги
                        // убрать обработчик

                        log.info("bye received.");

                        break;
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
}

package connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class Server {
    static Logger log = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    static ConcurrentHashMap<String, Class<?>> classMap = new ConcurrentHashMap<>();
    static BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

    Server() {
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Socket socket = serverSocket.accept();
        socket.getInputStream();
        socket.getOutputStream();*/
    }

    public static void main(String[] args) {

        HashMap<String, BlockingQueue> addrMap = new HashMap<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(() -> {
            try {
                while (true) {
                    Message msg = incoming.take();
                    // TODO: 21.03.2018 add routing
                    System.out.println(msg);
                }
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        int portNumber = 8000;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            log.info("server started on port: {}", portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // узнать адрес клиента
                // добавить в "адресную книгу"
                BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
                addrMap.put("addr", queue);
                // запустить обработчик
                exec.execute(() -> runIncomingHandler(clientSocket));
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    private static void runIncomingHandler(Socket socket) {
        ObjectMapper mapper = new ObjectMapper();
        log.info("accept  {}", socket.toString());

        try (//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

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

                Object o = mapper.readValue(json, clazz);
                Message msg = (Message) o;
                push(msg);

            }


            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            log.error("", e);
        }
    }

    static void push(Message o) {
        try {
            incoming.put(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

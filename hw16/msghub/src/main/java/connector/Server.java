package connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class Server {
    static Logger log = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
   static ConcurrentHashMap<String,Class<?>> classMap=new ConcurrentHashMap<>();

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
        ExecutorService exec = Executors.newCachedThreadPool();
        int portNumber = 8000;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            log.info("server started on port: {}", portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                exec.execute(()-> runHandler(clientSocket));
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    private static void runHandler(Socket socket) {
        ObjectMapper mapper = new ObjectMapper();
        log.info("accept  {}", socket.toString());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                String className = in.readLine();
                if (className.equals("bye.")) {
                    log.info("bye received.");
                    break;
                }
                //Class<?> clazz =classMap.get(className);
                Class<?> clazz =classMap.get(className);
                if(clazz==null){
                    clazz=Thread.currentThread().getContextClassLoader().loadClass(className);
                    classMap.put(className,clazz);
                }

                String json = in.readLine();

                String eol = in.readLine();

                Object o = mapper.readValue(json, clazz);
                //TODO add msg routing

            }


            socket.close();
        } catch (IOException e ) {
            log.error("", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

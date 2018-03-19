package connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class Server {

    private ServerSocket serverSocket;

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

    static Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        int portNumber = 8000;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            log.info("server started on port: {}", portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                runHandler(clientSocket);
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


            String inputLine, outputLine;

            // Initiate conversation with client
           /* KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);*/

            while (true) {
                String className = in.readLine();
                if (className.equals("bye.")) {
                    log.info("bye received.");
                    break;
                }
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                String json = in.readLine();

                String eol = in.readLine();

                Object o = mapper.readValue(json, clazz);
                //   log.info("received {} {}", className, o);


//                outputLine = kkp.processInput(inputLine);
//                out.println(outputLine);
//                if (outputLine.equals("Bye."))
//                    break;
            }

            socket.close();


        } catch (IOException | ClassNotFoundException e) {
            log.error("", e);
        }
    }
}

package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by vadim.tishenko
 * on 11.03.2018 16:28.
 */
public class SocketClient {
    static final Logger log = LoggerFactory.getLogger(SocketClient.class);

    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("ya.ru");
        Socket socket = new Socket(inetAddress, 80);
        log.info("{}", socket);
     /*   InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();*/

        try {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Вывод автоматически Output быталкивается PrintWriter'ом.
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println("GET / HTTP/1.1");
            out.println("Host: ya.ru");
            out.println();

            String l;
            while ((l = in.readLine()) != null) {
                log.info("{}", l);
                if (l.isEmpty()) break;
            }

        } finally {
            System.out.println("closing...");
            socket.close();
        }


    }
}

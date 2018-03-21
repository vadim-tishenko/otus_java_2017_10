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
import java.util.concurrent.TimeUnit;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class Client {
    public static final long COUNT = 100_000_000;
    static Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String hostName = "localhost";
        int portNumber = 8000;
        TestMsg msg = new TestMsg("from1", "to2", "val3");
        int len = String.format("%s\n%s\n\n", msg.getClass().getCanonicalName(), mapper.writeValueAsString(msg)).length();

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream());//, true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            long n0 = System.nanoTime();
            for (int i = 0; i < COUNT; i++) {
                out.println(msg.getClass().getName());
                out.println(mapper.writeValueAsString(msg));
                out.println();
                // log.info("msg {} sent", msg);
            }
            out.println("bye.");
            out.flush();
            long n1 = System.nanoTime();
            log.info("stat: IOPS: {}", COUNT * 1000.0d / TimeUnit.NANOSECONDS.toMillis(n1 - n0));
            log.info("stat: MBps: {}", len * COUNT * 1000.0d / TimeUnit.NANOSECONDS.toMillis(n1 - n0) / (1024 * 1024));


        } catch (IOException e) {
            log.error("", e);
        }
    }
}
/*
23:03:14.097 [main] INFO connector.Client - stat: IOPS: 185528.75695732838
23:03:14.097 [main] INFO connector.Client - stat: IOPS: 185528.75695732838
23:07:22.167 [main] INFO connector.Client - stat: IOPS: 674127.0055278414

 */
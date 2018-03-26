package connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class DbClient {
    private static final long COUNT = 100_000_000;
    static Logger log = LoggerFactory.getLogger(DbClient.class);

    public static void main(String[] args) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();
        String hostName = "localhost";
        int portNumber = 8000;
        // TestMsg msg = new TestMsg("front", "db", "val3");
        // int len = String.format("%s\n%s\n\n", msg.getClass().getCanonicalName(), mapper.writeValueAsString(msg)).length();

        try (
                Socket socket = new Socket(hostName, portNumber);
                /*PrintWriter out = new PrintWriter(socket.getOutputStream());//, true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
        ) {

            Connector conn = new Connector(socket, "db");
            conn.onReceive((m) -> {

            });
            Thread.sleep(100_000);
            conn.close();
            /*log.info("out conn: ", conn);


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
*/

        } catch (IOException e) {
            log.error("", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

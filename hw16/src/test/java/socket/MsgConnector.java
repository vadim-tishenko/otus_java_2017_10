package socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by vadim.tishenko
 * on 11.03.2018 17:59.
 */
public class MsgConnector {
    Logger log = LoggerFactory.getLogger(MsgConnector.class);
    PrintWriter out;
    BufferedReader in;
    ObjectMapper objectMapper = new ObjectMapper();

    void attach(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Вывод автоматически Output быталкивается PrintWriter'ом.
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    void attach(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }


    void send(Object msg) {
        out.printf("%s\r%s\r\r", msg.getClass().getCanonicalName(), toJson(msg));
    }

    private String toJson(Object msg) {
        try {
            return objectMapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    Object receive() {
        try {
            String className = in.readLine();
            Class<?> clazz = getClass().getClassLoader().loadClass(className);
            String content = in.readLine();
            String end = in.readLine();
            if (!content.isEmpty() && end.isEmpty()) {
                Object result = objectMapper.readValue(content, clazz);
                return result;
            } else {
                throw new IllegalStateException();
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }
}

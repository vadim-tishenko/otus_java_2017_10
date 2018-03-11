package socket;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

/**
 * Created by vadim.tishenko
 * on 11.03.2018 20:17.
 */
public class MsgConnectorTest {

    @Test
    public void send() {
        MsgConnector conn = new MsgConnector();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        conn.attach(null, pw);
        TestMsg testMsg = new TestMsg(20, "STRING25");
        conn.send(testMsg);
        String[] result = sw.toString().split("\r", 4);
        assertThat(result.length, is(4));
        assertThat(result[0], is("socket.TestMsg"));
        String json = result[1];
        assertThat(json, sameJSONAs("{\"num\":20,\"str\":\"STRING25\"}"));
        assertThat(result[2], is(""));
        assertThat(result[2], is(""));
    }

    @Test
    public void receive() {
        StringReader st = new StringReader("socket.TestMsg\r{\"num\":10,\"str\":\"STRING\"}\r\r");
        BufferedReader bufferedReader = new BufferedReader(st);
        MsgConnector conn = new MsgConnector();
        conn.attach(bufferedReader, null);
        Object result = conn.receive();
        assertThat(result, is(instanceOf(TestMsg.class)));
        TestMsg msg = (TestMsg) result;
        assertThat(msg.getNum(), is(10));
        assertThat(msg.getStr(), is("STRING"));
    }
}
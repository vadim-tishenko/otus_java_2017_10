package connector;

/**
 * Created by vadim.tishenko
 * on 19.03.2018 22:23.
 */
public class TestMsg {
    String from;
    String to;
    String value;

    public TestMsg() {
    }

    public TestMsg(String from, String to, String value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestMsg{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

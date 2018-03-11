package socket;

/**
 * Created by vadim.tishenko
 * on 11.03.2018 20:22.
 */
public class TestMsg {
    private int num;
    private String str;

    public TestMsg() {
    }

    public TestMsg(int num, String str) {
        this.num = num;
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "TestMsg{" +
                "num=" + num +
                ", str='" + str + '\'' +
                '}';
    }
}

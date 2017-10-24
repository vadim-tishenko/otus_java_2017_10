/**
 * Created by vadim.tishenko
 * on 21.10.2017 12:27.
 */
public class MainArray {
    public static void main(String[] args) {
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        long used = totalMemory - freeMemory;

        System.out.printf("total %d, free: %d, is used: %d\n",totalMemory,freeMemory,used);
//        System.out.println(Integer.MAX_VALUE);
//        ArrayList a = new ArrayList(Integer.MAX_VALUE);
//        System.out.println(a.size());
        Runtime.getRuntime().gc();
        Number n=0;

    }
}

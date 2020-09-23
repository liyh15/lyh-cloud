package wibo.cloud.custom.jvm;

/**
 * String的垃圾回收:
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  21:27
 */
public class StringTableDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 100000; j++) {
            String.valueOf(j).intern();
        }
        Thread.sleep(100000);
        System.out.println("aaa");
    }
}

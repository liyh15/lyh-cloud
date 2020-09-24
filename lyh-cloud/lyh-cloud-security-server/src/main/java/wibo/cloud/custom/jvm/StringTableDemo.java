package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * String的垃圾回收:
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  21:27
 */
public class StringTableDemo {
    public static void main(String[] args) throws InterruptedException {
        String a = null;
        int i = 1;
        List<String> b = new ArrayList<>();
        try {
            while(true) {
                a = i++ + "aaaaaaaaaaaaa";
                b.add(a.intern());
            }
        }finally {
            System.out.println("aaa" + i);
        }
    }
}

package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname HeapDemo
 * @Description TODO
 * @Date 2020/9/11 10:42
 * @Created by lyh
 */
public class HeapDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start...");
        StringBuilder builder = new StringBuilder();

        String a = "aa";
        String b = "b";
        String c = (a + "b").intern();
        String d = ("aa" + b).intern();
        System.out.println(c == d);
    }
}

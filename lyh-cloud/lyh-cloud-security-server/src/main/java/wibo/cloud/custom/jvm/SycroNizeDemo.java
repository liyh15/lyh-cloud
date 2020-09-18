package wibo.cloud.custom.jvm;

/**
 * @Classname SycroNizeDemo
 * @Description TODO
 * @Date 2020/9/18 9:55
 * @Created by lyh
 */
public class SycroNizeDemo {
    public static void main(String[] args) {
        SycroNizeDemo demo = new SycroNizeDemo();
        synchronized (demo) {
            System.out.println("Aaaa");
        }
    }
}

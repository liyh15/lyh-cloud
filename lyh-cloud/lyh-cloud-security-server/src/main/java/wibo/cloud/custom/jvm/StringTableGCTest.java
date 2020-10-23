package wibo.cloud.custom.jvm;

/**
 * @Classname StringTableGCTest
 * @Description TODO
 * @Date 2020/10/22 10:48
 * @Created by lyh
 */
public class StringTableGCTest {
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 100000; j++) {
            String.valueOf(j).intern();
        }
        Thread.sleep(10000);
    }
}

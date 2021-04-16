package wibo.cloud.custom.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Classname ThreadTest
 * @Description TODO
 * @Date 2021/4/16 14:51
 * @Created by lyh
 */
public class ThreadTest {

    private static AtomicLong A = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
               for (int i = 0;i < 10000000;i++) {
                   A.incrementAndGet();
               }
            }
        });
        a.start();
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10000000;i++) {
                    A.incrementAndGet();
                }
            }
        });
        b.start();
        b.join();
        a.join();
        System.out.println(A);
    }
}

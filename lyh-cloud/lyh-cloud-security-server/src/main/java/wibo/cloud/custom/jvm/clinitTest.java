package wibo.cloud.custom.jvm;

import javax.swing.plaf.TableHeaderUI;

/**
 * @Classname clinitTest
 * @Description TODO
 * @Date 2020/8/7 9:49
 * @Created by lyh
 */
public class clinitTest {

    static class test {
        static {
            System.out.println("asdasdasd");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 System.out.println("thread a");
                 test test = new test();
                 System.out.println("thread ccc");
             }
         }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread b");
                test test = new test();
                System.out.println("thread baaa");
            }
        }).start();
    }
}

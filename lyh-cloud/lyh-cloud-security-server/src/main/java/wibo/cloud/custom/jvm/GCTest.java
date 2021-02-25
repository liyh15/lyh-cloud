package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname GCTest
 * @Description TODO
 * @Date 2020/10/23 15:22
 * @Created by lyh
 */
public class GCTest {

    public static GCTest test = null;

    // TODO finallize只能调用一次，在进行第二次垃圾回收时，执行变成不可触及
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        test = this;
        System.out.println("已经调用了finalize");
    }

    public static void main(String[] args) throws InterruptedException {

        List<GCTest> gcTests = new ArrayList<>();
        for (int i = 0; i < 100000;i ++) {
            GCTest test = new GCTest();
            gcTests.add(test);
        }
        gcTests = null;
        Thread.sleep(20000);
        System.gc();
        Thread.sleep(10000000);
       /* if (test == null) {
            System.out.println("test has dead");
        }  else {
            System.out.println("test has live");
        }
        test = null;
        Thread.sleep(2000);
        if (test == null) {
            System.out.println("test has dead");
        }  else {
            System.out.println("test has live");
        }*/
    }
}

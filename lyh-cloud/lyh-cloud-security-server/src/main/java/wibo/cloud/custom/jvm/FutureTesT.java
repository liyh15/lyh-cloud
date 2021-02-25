package wibo.cloud.custom.jvm;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Classname FutureTeST
 * @Description TODO
 * @Date 2020/11/30 10:46
 * @Created by lyh
 */
public class FutureTesT {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable ca2 = new Callable() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "包子准备完毕";
            }
        };
        FutureTask<String> ft1 = new FutureTask<String>(ca2);
        new Thread(ft1).start();
        System.out.println(ft1.get());
    }
}

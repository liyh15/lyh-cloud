package wibo.cloud.custom.thread;

import java.util.concurrent.*;

import static java.util.concurrent.ThreadPoolExecutor.*;

/**
 * @Classname ThreadPool
 * @Description 采用ThreadPoolExcutor创建线程池
 * @Date 2021/4/16 16:07
 * @Created by lyh
 */
public class ThreadPool {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BlockingQueue queue = new ArrayBlockingQueue<Runnable>(2);
        BlockingQueue<Future<Integer>> queue2 = new ArrayBlockingQueue<Future<Integer>>(10);
        // TODO 设置最小线程数，最大线程数，空闲线程存活时间
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,6,10, TimeUnit.SECONDS,queue,new MyAbortHandler());
        for (int i = 0;i < 10;i ++) {
            int index = i + 1;
            queue2.add(threadPoolExecutor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println(Thread.currentThread().toString() + "::" + index);
                    Thread.sleep(10000);
                    return index;
                }
            }));
        }
        System.out.println(queue2.size());
        for (int i = 0;i < 10; i ++) {
            Future<Integer> future = queue2.take();
            System.out.println(future.isDone());
            Integer a = queue2.take().get();
            System.out.println(a);
        }
        System.out.println("asdasdasd");
    }

    static class MyAbortHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(Thread.currentThread().toString());
            System.out.println("被拒绝了");
        }
    }
}

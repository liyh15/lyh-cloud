package wibo.cloud.custom.jvm;

/**
 * @Classname StackAllocation
 * @Description TODO
 * @Date 2020/9/17 15:35
 * @Created by lyh
 */
public class StackAllocation {
    public static void main(String[] args) throws InterruptedException {
        long a = System.currentTimeMillis();
        for(int i = 0;i < 10000000; i++) {
            alloc();
        }
        System.out.println(System.currentTimeMillis() - a);
        Thread.sleep(100000);
    }

    public static void alloc() {
        StackTest stackTest = new StackTest();
    }
}

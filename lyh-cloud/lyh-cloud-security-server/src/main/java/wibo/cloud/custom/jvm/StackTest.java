package wibo.cloud.custom.jvm;

/**
 * @Classname StackTest
 * @Description TODO
 * @Date 2020/8/11 15:20
 * @Created by lyh
 */
public class StackTest {

    private static int count = 0;

    public void method1() {
        int a = 10;
        int b = 20;
        method2();
    }

    public void method2() {
        int c = 10;
        int d = 20;
    }

    public static void main(String[] args) {
        count ++;
        System.out.println(count);
        main(null);
    }
}

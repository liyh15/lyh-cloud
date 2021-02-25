package wibo.cloud.custom.jvm;

/**
 * @Classname SycroNizeDemo
 * @Description TODO
 * @Date 2020/9/18 9:55
 * @Created by lyh
 */
public class SycroNizeDemo implements InvotionTestMapper{

    public void test(Integer a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        SycroNizeDemo demo = new SycroNizeDemo();
        demo.one();
        demo.test(1);
        demo.test(2);
    }

    @Override
    public String one() {
        return null;
    }

    @Override
    public String two() {
        return null;
    }
}

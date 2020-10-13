package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TestDemo
 * @Description TODO
 * @Date 2020/9/14 14:54
 * @Created by lyh
 */
public class TestDemo {

    public void one() {
        System.out.println("aaa");
    }

    public void two(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        TestDemo demo = new TestDemo();
        demo.one();
        int b = 1;
        String c = "aaaaa";
        demo.two(c);
    }
}

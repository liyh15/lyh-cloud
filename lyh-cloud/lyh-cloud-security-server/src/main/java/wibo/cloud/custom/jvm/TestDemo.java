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
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        String b = "asd";
        while (true) {
            a.add(b);
            b = b + b;
        }
    }
}

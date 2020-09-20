package wibo.cloud.custom.controller;

import lombok.Data;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2020/9/2 20:18
 * @Created by lyh
 */
@Data
public class Test<T> {

    private T a;

    private String name = "lyh";

    private Integer age = 12;

    public static void main(String[] args) {
        Test t = new Test<>();
        t.setA("12");
        Test<String> t2 = t;
        System.out.println(t2.getA());
    }

}

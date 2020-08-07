package com.wibo;


import lombok.Data;

/**
 * @Classname Apple
 * @Description TODO
 * @Date 2020/7/28 11:47
 * @Created by lyh
 */
@Data
public class Apple {

    private int id = 1;

    private String name = "abcd";

    public void test() {
        System.out.println(name);
    }
}

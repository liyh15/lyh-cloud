package wibo.cloud.common.pojo;

import lombok.Data;

import java.util.UUID;

/**
 * @Classname Student
 * @Description TODO
 * @Date 2020/7/22 17:15
 * @Created by lyh
 */
public class Student {

    public String getName() {
        return UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}

package wibo.cloud.custom.controller;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2020/9/2 20:18
 * @Created by lyh
 */
@Data
@ToString
public class Test implements Serializable {

    private String name = "lyh";

    private Integer age = 12;

    private LocalDateTime localDateTime = LocalDateTime.now();
}

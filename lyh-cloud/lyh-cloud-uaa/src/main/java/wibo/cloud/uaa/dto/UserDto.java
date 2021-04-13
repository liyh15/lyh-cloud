package wibo.cloud.uaa.dto;

import lombok.Data;

import java.util.List;

/**
 * @Classname User
 * @Description TODO
 * @Date 2021/4/13 10:37
 * @Created by lyh
 */
@Data
public class UserDto {

    private Integer id;

    private String name;

    private String password;

    private List<Auth> userAuth;
}

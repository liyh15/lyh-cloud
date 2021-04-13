package wibo.cloud.uaa.mapper;

import org.apache.ibatis.annotations.Param;
import wibo.cloud.uaa.dto.UserDto;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2021/4/13 9:26
 * @Created by lyh
 */
public interface UserMapper {

    public Integer reg(@Param("user") UserDto user);

    public UserDto getUserByName(@Param("name") String name);
}

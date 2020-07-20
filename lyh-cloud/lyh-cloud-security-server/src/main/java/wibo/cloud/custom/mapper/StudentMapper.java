package wibo.cloud.custom.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Classname TestMapper
 * @Description TODO
 * @Date 2020/7/20 19:52
 * @Created by lyh
 */
public interface StudentMapper {

    public Integer update(@Param("id") Integer id);
}


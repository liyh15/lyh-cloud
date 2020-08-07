package wibo.cloud.custom.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import wibo.cloud.common.pojo.Theacher;

/**
 * @Classname TestMapper
 * @Description TODO
 * @Date 2020/7/20 19:52
 * @Created by lyh
 */
public interface TeacherMapper {

    public Integer update(@Param("id") Integer id);

    public Theacher forup(@Param("name") String name);

    public Integer insert(@Param("name") String name);
}


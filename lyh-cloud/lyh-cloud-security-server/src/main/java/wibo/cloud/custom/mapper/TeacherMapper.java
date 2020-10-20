package wibo.cloud.custom.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import wibo.cloud.common.pojo.TastJob;
import wibo.cloud.common.pojo.Theacher;

import java.util.List;

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

    public Integer insertId(@Param("id") String id);

    public Theacher forupId(@Param("id") String id);

    public Theacher select(@Param("id") Integer id);

    public Theacher selectN(@Param("name") String name);

    public Integer updateByName(@Param("name") String name);

    public List<Theacher> selectList();

    public TastJob selectById(@Param("id") Integer id);

    public TastJob seeee(@Param("id") Integer id);
}


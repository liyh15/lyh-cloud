package wibo.cloud.custom.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import wibo.cloud.common.pojo.STList;
import wibo.cloud.common.pojo.TastJob;
import wibo.cloud.common.pojo.Teacher;
import wibo.cloud.custom.config.PageTest;

import java.util.List;

/**
 * @Classname TestMapper
 * @Description TODO
 * @Date 2020/7/20 19:52
 * @Created by lyh
 */
public interface TeacherMapper {

    public Integer update(@Param("id") Integer id);

    public Integer insertMii(@Param("list") List<Teacher> list);

    public Teacher forup(@Param("name") String name);

    public Integer insert(@Param("name") String name);

    public Integer insertId(@Param("id") String id);

    public Teacher forupId(@Param("id") String id);

    public Teacher select(@Param("id") Integer id);

    public Teacher selectN(@Param("name") String name);

    public Integer updateByName(@Param("name") String name);

    public List<Teacher> selectList();

    public Teacher selectById(@Param("id") Integer id);

    public TastJob seeee(@Param("id") Integer id);

    public Integer updateAge(@Param("name") String name);

    public Integer updateName(@Param("id") Integer name);

    public Integer delete(@Param("id") Integer id);

    public Integer updateN(@Param("age") Integer age);

    public Integer updateA(@Param("name") String name);

    public List<Teacher> selectBatch(@Param("stList") List<STList> stList, @Param("name") String name, int age);

    public List<Teacher> selectByPage(PageTest pageTest);
}


package wibo.cloud.custom.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import wibo.cloud.common.pojo.Student;
import wibo.cloud.common.pojo.Teacher;

import java.util.List;

/**
 * @Classname TestMapper
 * @Description TODO
 * @Date 2020/7/20 19:52
 * @Created by lyh
 */
public interface StudentMapper {

    public Integer update(@Param("id") Integer id);

    public Integer insert(@Param("list") List<Student> student);

    public List<Student> select();

    public List<Teacher> selectBatch(List<Integer> list, String name, List<Integer> age);
}


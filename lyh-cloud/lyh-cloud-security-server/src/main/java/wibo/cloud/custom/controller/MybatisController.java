package wibo.cloud.custom.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.common.pojo.Teacher;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MybatisController
 * @Description TODO
 * @Date 2021/1/8 11:05
 * @Created by lyh
 */
@RestController
@RequestMapping("mybatisController")
public class MybatisController {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作A方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "one", method = RequestMethod.GET)
    @Transactional
    public String one() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        Teacher teacher = sqlSession.selectOne("wibo.cloud.custom.mapper.TeacherMapper.selectById", map);
        System.out.println(teacher);
        return "aaa";
    }
}

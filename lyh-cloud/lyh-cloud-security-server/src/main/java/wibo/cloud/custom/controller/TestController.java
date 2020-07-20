package wibo.cloud.custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.custom.mapper.StudentMapper;
import wibo.cloud.custom.mapper.TeacherMapper;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/7/20 19:17
 * @Created by lyh
 */
@RestController
public class TestController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     *
     * @param
     * @return
     * @throws
     * @description 数据库操作A方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "one", method = RequestMethod.GET)
    @Transactional
    public String one() throws InterruptedException {
        studentMapper.update(1);
        System.out.println("ccc");
        Thread.sleep(5000);
        teacherMapper.update(1);
        System.out.println("rrr");
        return "one";
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @description 数据库操作B方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "two",method = RequestMethod.GET)
    @Transactional
    public String two() throws InterruptedException {
        teacherMapper.update(1);
        System.out.println("ddd");
        Thread.sleep(10000);
        studentMapper.update(1);
        System.out.println("bbb");
        return "two";
    }
}

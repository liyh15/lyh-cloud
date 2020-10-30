package wibo.cloud.custom.controller;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.common.pojo.Student;
import wibo.cloud.common.pojo.Test;
import wibo.cloud.custom.config.AnnoTest;
import wibo.cloud.custom.config.Login;
import wibo.cloud.custom.mapper.StudentMapper;
import wibo.cloud.custom.mapper.TeacherMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/7/20 19:17
 * @Created by lyh
 */
@RestController("TestController")
public class TestController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

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
    public String one() throws InterruptedException {
        studentMapper.update(1);
        System.out.println("ccc");
        Thread.sleep(5000);
        teacherMapper.update(1);
        System.out.println("rrr");
        return "one";
    }

    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作B方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "two", method = RequestMethod.GET)
    @Transactional
    public String two() throws InterruptedException {
        teacherMapper.update(1);
        System.out.println("ddd");
        Thread.sleep(10000);
        studentMapper.update(1);
        System.out.println("bbb");
        return "two";
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @description 对没有加索引的字段进行forupdate，会全表锁定
     * @author liyuanhao
     * @date 2020/8/5 17:47
     */
    @RequestMapping(value = "forup", method = RequestMethod.GET)
    @Transactional
    public String forup(String name) throws InterruptedException {
        teacherMapper.forup(name);
        Thread.sleep(5000);
        System.out.println("xxxxxxxxxxx");
        return "two";
    }

    @RequestMapping(value = "forup3", method = RequestMethod.GET)
    @Transactional
    public String forup3(String name) throws InterruptedException {
        teacherMapper.forup(name);
        System.out.println("33333333333");
        Thread.sleep(5000);
        return "two";
    }

    /*
       TODO 当字段是字符串时，如果有索引但是字段区别度不高，则更新的时候还是全表锁
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    @Transactional
    public String update(String name) throws InterruptedException {
        teacherMapper.updateAge(name);
        System.out.println("33333333333");
        Thread.sleep(5000);
        return "two";
    }

    /*
     */
    @RequestMapping(value = "update22222", method = RequestMethod.GET)
    @Transactional
    public String update3(Integer age) throws InterruptedException {
        teacherMapper.updateName(age);
        System.out.println("4444444444444");
        Thread.sleep(5000);
        return "update2";
    }


    @RequestMapping(value = "update444444", method = RequestMethod.GET)
    @Transactional
    public String update444444(String name) throws InterruptedException {
        teacherMapper.updateA(name);
        System.out.println("AAAAAAAAAAAAAAAAAA");
        Thread.sleep(5000);
        return "two";
    }

    /*
     */
    @RequestMapping(value = "update555555", method = RequestMethod.GET)
    @Transactional
    public String update555555(Integer age) throws InterruptedException {
        teacherMapper.updateN(age);
        System.out.println("BBBBBBBBBBBBBBBBB");
        Thread.sleep(5000);
        return "update2";
    }

    @RequestMapping(value = "forup2", method = RequestMethod.GET)
    @Transactional
    public String forup2(String name) throws InterruptedException {
        // TODO 对于插入而，如果插入的记录对应索引字段的字符串前两个字符匹配，则会阻塞
        teacherMapper.insert(name);
        System.out.println("bbbbbbbbbbbbb");
        return "two";
    }

    @RequestMapping(value = "forup4", method = RequestMethod.GET)
    @Transactional
    public String forup4(String id) throws InterruptedException {
        // TODO 对于数字索引字段而言，如果存在查询语句对此索引加锁(不管此索引字段是否存在)，都会被阻塞
        teacherMapper.insertId(id);
        System.out.println("eeeeeeeeeeee");
        Thread.sleep(5000);
        return "two";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @Transactional
    public String delete(Integer id) throws InterruptedException {
        // TODO 对于数字索引字段而言，如果存在查询语句对此索引加锁(不管此索引字段是否存在)，都会被阻塞
        teacherMapper.delete(id);
        System.out.println("dddddddddddddd");
        Thread.sleep(5000);
        return "two";
    }

    @RequestMapping(value = "forup7", method = RequestMethod.GET)
    @Transactional
    public String forup7(String name) throws InterruptedException {
        teacherMapper.updateByName(name);
        System.out.println("uuuuuuuuuuuuuu");
        Thread.sleep(5000);
        return "two";
    }

    @RequestMapping(value = "forup8", method = RequestMethod.GET)
    @Transactional
    public String forup8(String name) throws InterruptedException {
        teacherMapper.updateByName(name);
        System.out.println("forup8");
        return "two";
    }

    @RequestMapping(value = "forup5", method = RequestMethod.GET)
    @Transactional
    public String forup5(String id) throws InterruptedException {
        teacherMapper.forupId(id);
        System.out.println("fffffffff");
        return "two";
    }

    @RequestMapping(value = "update1", method = RequestMethod.GET)
    @Transactional
    public String update1(Integer id) throws InterruptedException {
        // TODO 两个事务中如果存在交叉更新，则会出现死锁异常
        System.out.println("aaaaaaaaa");
        teacherMapper.updateAge("aaa");
        System.out.println("fffffffff");
        Thread.sleep(10000);
        System.out.println("ccccccccc");
        return "two";
    }

    @RequestMapping(value = "select", method = RequestMethod.GET)
    @Transactional
    public String select(Integer id) throws InterruptedException {
        // TODO 当使用for update查询时，如果存在无提交事务跟新的这条数据，则for update语句阻塞，和更新阻塞一种情况，可以把for update看做是一个更新语句(可以同时锁住多个行)
        teacherMapper.select(id);
        System.out.println("zzzzzzzz");
        return "select";
    }

    @RequestMapping(value = "update2", method = RequestMethod.GET)
    public String update2(Integer id) throws InterruptedException {
        System.out.println("bbbbbbbbbb");
        teacherMapper.update(1);
        System.out.println("xxxxxxxxxx");
        Thread.sleep(5000);
        return "two";
    }



    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作B方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "insert", method = RequestMethod.GET)
    @Transactional
    public String insert() throws InterruptedException {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            students.add(student);
        }
        studentMapper.insert(students);
        return "two";
    }

    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作B方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String post(HttpServletRequest request) throws InterruptedException, IOException {
        // System.out.println(test);
        InputStream inputStream = request.getInputStream();
        int a;
        while ((a = inputStream.read()) != -1) {
            System.out.println((char) a);
        }
        return "post";
    }

    @AnnoTest
    @RequestMapping(value = "annTest", method = RequestMethod.POST)
    public String annTest(HttpServletRequest request){
        System.out.println("xxxxxxxxxxxxxxxx");
        return "post";
    }

    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public String selectList(HttpServletRequest request) throws IOException {

        System.out.println(teacherMapper.selectList().size());
        return "post";
    }

    @RequestMapping(value = "selectById", method = RequestMethod.POST)
    public String selectById() {
        System.out.println(teacherMapper.seeee(1));
        return "post";
    }


    /**
     * 将一个字符串转化为输入流
     *
     * @param sInputString
     * @return
     */
    public static InputStream getStrToStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void testAaa() {
        int a = 1;
        int b = 2;
        int c = 3;
    }

    public static void main(String[] args) throws IOException {
        TestController testController = new TestController();
        int a = 1;
        int b = 2;
        int c = 3;
        testController.testAaa();
        String aa = "aaa";
    }
}


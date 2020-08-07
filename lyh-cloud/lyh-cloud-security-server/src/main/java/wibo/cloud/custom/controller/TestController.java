package wibo.cloud.custom.controller;
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

    @RequestMapping(value = "forup2", method = RequestMethod.GET)
    @Transactional
    public String forup2(String name) throws InterruptedException {
        teacherMapper.insert(name);
        System.out.println("bbbbbbbbbbbbb");
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
        for (int i = 0; i < 10000; i++) {
            Student student = new Student();
            students.add(student);
        }
        for (int i = 0; i < 100; i++) {
            studentMapper.insert(students);
        }
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

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            try {
                int a;
                while ((a = inputStream.read()) != -1) {
                    System.out.println((char) a);
                }
                outputStream.write(10);
            } finally {
                inputStream.close();
                socket.getOutputStream().close();
                socket.close();
            }
        }
    }


}


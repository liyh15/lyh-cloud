package wibo.cloud.custom.controller;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.netflix.ribbon.proxy.annotation.Http;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import wibo.cloud.common.config.TestException;
import wibo.cloud.common.pojo.STList;
import wibo.cloud.common.pojo.Student;
import wibo.cloud.common.pojo.TastJob;
import wibo.cloud.common.pojo.Teacher;
import wibo.cloud.common.response.BaseResponse;
import wibo.cloud.custom.aspect.TestInterface;
import wibo.cloud.custom.config.*;
import wibo.cloud.custom.mapper.StudentMapper;
import wibo.cloud.custom.mapper.TeacherMapper;
import wibo.cloud.custom.service.ServiceInterFace;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/7/20 19:17
 * @Created by lyh
 */
@RestController("TestController")
public class TestController {


    private ThreadLocal threadLocal = new ThreadLocal();

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ServiceInterFace serviceInterFace;

    @Value("${parmas.body}")
    private String name;

    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作A方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "teacher", method = RequestMethod.GET)
    @Transactional
    public String teacher(Integer age) throws InterruptedException {
        System.out.println(name);
        return "teacher";
    }

    @RequestMapping(value = "teacherIn", method = RequestMethod.GET)
    @Transactional
    public String teacherIn(Integer id) throws InterruptedException {
        teacherMapper.insertId(id);
        Thread.sleep(5000);
        return "teacher";
    }

    /**
     * @param
     * @return
     * @throws
     * @description 数据库操作A方法，分别更新两张表
     * @author liyuanhao
     * @date 2020/7/20 19:18
     */
    @RequestMapping(value = "teacher2", method = RequestMethod.GET)
    @Transactional
    public String teacher2(Integer age) throws InterruptedException {
        teacherMapper.updateN(age);
        return "teacher2";
    }

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

        List t = null;
        List<String> t2 = t;

        teacherMapper.updateN(age);
        System.out.println("BBBBBBBBBBBBBBBBB");
        Thread.sleep(5000);
        return "update2";
    }

    @RequestMapping(value = "forup2", method = RequestMethod.GET)
    public String forup2(String name) throws InterruptedException {
        // TODO 对于插入而，如果插入的记录对应索引字段的字符串前两个字符匹配，则会阻塞
        teacherMapper.insert(name);
        System.out.println(teacherMapper.selectList());
        return "two";
    }

    @RequestMapping(value = "forup4", method = RequestMethod.GET)
    @Transactional
    public String forup4(String id) throws InterruptedException {
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
        System.out.println("AAaaaaa");
        Teacher teacher = teacherMapper.select(id);
        System.out.println(teacher);
        System.out.println(teacherMapper.updateByName(teacher.getName()));
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

    @RequestMapping(value = "image", method = RequestMethod.GET)
    public void image(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {
         /*
             1.生成验证码
             2.把验证码上的文本存在session中
             3.把验证码图片发送给客户端
             */
        ImageVerificationCode ivc = new ImageVerificationCode();     //用我们的验证码类，生成验证码类对象
        BufferedImage image = ivc.getImage();  //获取验证码
        request.getSession().setAttribute("text", ivc.getText()); //将验证码的文本存在session中
        ivc.output(image, response.getOutputStream());//将验证码图片响应给客户端
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
        ByteArrayInputStream o = null;
        ByteArrayOutputStream o2 = null;
        System.out.println(teacherMapper.selectList().size());
        return "post";
    }

    @RequestMapping(value = "selectById", method = RequestMethod.POST)
    public Teacher selectById() {
        return teacherMapper.selectById(1);
    }

    @RequestMapping(value = "cocurrent1", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String cocurrent1() throws InterruptedException {
        System.out.println(teacherMapper.select(1));
        Thread.sleep(10000);
        // TODO 如果有更新语句则会清除MyBatis的所有cache
        // TODO 一级缓存是作用在SqlSession对象上面，二级缓存是作用在mapper上面，所以二级缓存的作用于最大
        teacherMapper.update(1);

        System.out.println(teacherMapper.select(1));
        return "post";
    }

    @RequestMapping(value = "cocurrent2", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String cocurrent2() {
        teacherMapper.update(1);
        return "post";
    }

    @RequestMapping(value = "insertMii", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String insertMii() {
        List<Teacher> list = new ArrayList<>();
        Teacher teacher  = new Teacher();
        teacher.setName("AAAAAAAAAAAAAA");
        teacher.setAge(14);
        for (int i = 0;i < 1; i ++) {
            list.add(teacher);
        }
        for (int i = 0;i < 1; i ++) {
            teacherMapper.insertMii(list);
        }
        return "post";
    }

    @RequestMapping(value = "selectMii", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String selectMii() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(30);
        BlockingQueue<Future<List<Teacher>>> queue = new LinkedBlockingQueue<Future<List<Teacher>>>();
        for (int i = 0;i < 30; i ++) {
            Future<List<Teacher>> future = service.submit(new Callable<List<Teacher>>() {
                @Override
                public List<Teacher> call() throws Exception {
                    List<Teacher> list = teacherMapper.selectList();
                    return list;
                }
            });
            queue.add(future);
        }
        int queueSize = queue.size();
        for (int i = 0; i < queueSize; i++) {
            List<Teacher> list = queue.take().get();
            System.out.println(list.size());
        }
        return "post";
    }

    @RequestMapping(value = "selectBatch", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String selectBatch(HttpServletResponse response) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i < 3;i++) {
            list.add(i);
        }
        SqlSession sqlSession;
        List<STList> stListList = new ArrayList<>();
        STList stList  =new STList();
        stList.setAge(list);
        stListList.add(stList);
        System.out.println(teacherMapper.selectBatch(stListList, "AAA",1));
        return "post";
    }

    @RequestMapping(value = "selectByPage", method = RequestMethod.POST)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String selectByPage(Integer current, Integer size) {
        PageTest pageTest = new PageTest();
        pageTest.setCurrent(current);
        pageTest.setSize(size);
        System.out.println(teacherMapper.selectByPage(pageTest));
        return "post";
    }

    @Advice
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "testAdvice", method = RequestMethod.POST)
    public BaseResponse testAdvice() {
        /*ThreadLocal threadLocal = new ThreadLocal();
        // threadLocal.set("asdasd");
        // threadLocal = null;
        System.gc();
        System.out.println(threadLocal.get());*/
        return new BaseResponse();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED) // TODO flushCache = true表示在执行过sql语句之后都会清空本地缓存
    @RequestMapping(value = "transationTest", method = RequestMethod.POST)
    public BaseResponse transationTest() {
        teacherMapper.update(1);
        serviceInterFace.updateTest();
        return new BaseResponse();
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
}


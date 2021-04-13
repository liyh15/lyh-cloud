package wibo.cloud.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.security.bean.TestBean;

import java.util.List;

/**
 * @Classname AopController
 * @Description TODO
 * @Date 2021/4/8 15:18
 * @Created by lyh
 */
@RestController
@RequestMapping("aop")
public class AopController {



    @PostMapping("around")
    public String around(TestBean bean) {
        return "around";
    }

    @PostMapping("annotaion")
    public String annotaion(TestBean bean) {
        return "around";
    }
}

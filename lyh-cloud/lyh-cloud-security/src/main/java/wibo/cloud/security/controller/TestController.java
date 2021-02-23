package wibo.cloud.security.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/2/21 11:24
 * @Created by lyh
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
      return "test";
    }


}

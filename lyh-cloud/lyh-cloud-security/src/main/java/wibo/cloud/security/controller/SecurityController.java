package wibo.cloud.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SecurityController
 * @Description TODO
 * @Date 2021/2/22 10:39
 * @Created by lyh
 */
@RestController
@RequestMapping("se")
public class SecurityController {

    @RequestMapping("test")
    public String test() {
        return "see";
    }
}

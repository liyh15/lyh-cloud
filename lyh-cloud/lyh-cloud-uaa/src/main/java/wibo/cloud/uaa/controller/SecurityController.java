package wibo.cloud.uaa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
public class SecurityController {

    @RequestMapping("/name")
    public String test() {
       return "name";
    }

    @RequestMapping("/age")
    public String age() {
        return "age";
    }
}

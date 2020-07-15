package wibo.cloud.uaa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAuthority('aaa')")
    public String age() {
        return "age";
    }

    @RequestMapping("/security")
    public String security() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "age";
    }
}

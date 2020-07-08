package wibo.cloud.custom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/r")
public class CustomController {

    @Value("${parmas.name}")
    private String name;

    @Value("${parmas.body}")
    private String body;

    @RequestMapping("body")
    // @PreAuthorize("hasAuthority('p1')")
    public String body() {
        return body;
    }

    @RequestMapping("name")
    // @PreAuthorize("hasAuthority('p2')")
    public String name() {
        return name;
    }
}

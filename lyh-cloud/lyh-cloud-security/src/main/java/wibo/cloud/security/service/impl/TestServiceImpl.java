package wibo.cloud.security.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import wibo.cloud.security.service.TestService;

/**
 * @Classname TestServiceImpl
 * @Description TODO
 * @Date 2021/4/12 10:20
 * @Created by lyh
 */
@Service
public class TestServiceImpl implements TestService {


    @Override
    public String aaa() {
        System.out.println("aaa");
        return "aaaasdasdaaaa";
    }

    public void syTest() {
        String a = "ad";
        synchronized (a) {
        }
    }
}

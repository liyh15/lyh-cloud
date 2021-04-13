package wibo.cloud.security.config;

import org.springframework.amqp.core.AbstractBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname ScopeBean
 * @Description TODO
 * @Date 2021/3/10 19:21
 * @Created by lyh
 */
@Component
@Scope("prototype")
public class ScopeBean {

    private final static String a = "hello";

    private static String b = "asdasd";

    private final String f = "fff";

    private String g = "ggg";


    {
        System.out.println("9999999999999999999999999999999999999999999999999999999999999999");
    }


    public ScopeBean aaa() {
        String c = "aaa" + a;
        String d = "bbb" + b;
        String ff = "fff" + f;
        String gg = "ggg" + g;
        StringBuilder builder;

        final String e  = "eee";
        String ee = "eee" + e;
        return this;
    }

    public static void main(String[] args) {
        ScopeBean scopeBean = new ScopeBean();
        scopeBean.aaa().aaa().aaa();
    }
}

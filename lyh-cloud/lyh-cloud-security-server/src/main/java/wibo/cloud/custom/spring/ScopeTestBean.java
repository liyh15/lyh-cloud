package wibo.cloud.custom.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Classname ScopeTestBean
 * @Description TODO
 * @Date 2021/1/15 15:24
 * @Created by lyh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeTestBean {

    private String name = UUID.randomUUID().toString();
}

package wibo.cloud.custom.spring;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Classname ConditionTestBean
 * @Description TODO
 * @Date 2021/1/15 14:34
 * @Created by lyh
 */
public class ConditionTestBean implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return true;
    }
}

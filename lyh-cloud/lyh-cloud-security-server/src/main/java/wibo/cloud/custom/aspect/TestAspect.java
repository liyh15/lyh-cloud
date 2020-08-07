package wibo.cloud.custom.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname TestAspect
 * @Description TODO
 * @Date 2020/8/5 15:16
 * @Created by lyh
 */
@Aspect
// @Configuration
public class TestAspect {

    @Pointcut("@annotation(wibo.cloud.custom.config.AnnoTest)")
    public void annPoc() {}

    @Around("annPoc()")
    public void annTest(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("annTest");
        jp.proceed();
    }

    @Around("execution(* wibo.cloud.custom.controller.*.*(..))")
    public void executionTest(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("executionTest");
        jp.proceed();
    }

    @Around("within(wibo.cloud.custom.controller.*)")
    public void withinTest(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("withinTest");
        jp.proceed();
    }

    @Around("bean(TestController)")
    public void beanTest(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("beanTest");
        jp.proceed();
    }
}

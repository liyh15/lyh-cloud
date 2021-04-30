package wibo.cloud.security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Classname ControllerAspect
 * @Description TODO
 * @Date 2021/4/26 14:43
 * @Created by lyh
 */
@Slf4j
@Aspect
@Configuration
public class ControllerAspect {

    @Pointcut("within(wibo.cloud.security.controller.*)")
    public void pointcut(){}

    @Pointcut("within(wibo.cloud.security.service.impl.*)")
    public void service(){}

    @Around("pointcut()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature pointSignature = (MethodSignature) point.getSignature();
        Method method =  pointSignature.getMethod();
        log.error("[开始调用]:{}:{}[接口],[入参]:{}",method.getDeclaringClass().getName(), method.getName(), Arrays.toString(point.getArgs()));
        point.proceed();
        log.error("[调用结束]:{}:{}[接口]",method.getDeclaringClass().getName(), method.getName());
    }

    @Around("service()")
    public Object service(ProceedingJoinPoint point) throws Throwable {
        MethodSignature pointSignature = (MethodSignature) point.getSignature();
        Method method =  pointSignature.getMethod();
        log.error("[service]:{}:{}[接口],[入参]:{}",method.getDeclaringClass().getName(), method.getName(), Arrays.toString(point.getArgs()));
        Object obj = point.proceed();
        log.error("[service]:{}:{}[接口]",method.getDeclaringClass().getName(), method.getName());
        return obj;
    }
}

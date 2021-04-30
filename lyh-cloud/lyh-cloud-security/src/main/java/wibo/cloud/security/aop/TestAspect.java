package wibo.cloud.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Classname TestAspect
 * @Description TODO
 * @Date 2021/4/8 15:23
 * @Created by lyh
 */
@Aspect
@Component
public class TestAspect {

    @Pointcut(value = "execution(* wibo.cloud.security.controller.AopController.around(..))")
    public void excution(){}

    @Around("excution()")
    public Object around(ProceedingJoinPoint point) throws Throwable {


        System.out.println(point.getThis());
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        System.out.println(methodSignature.getMethod()); // TODO 这种只能获取接口的方法

        Method method = point.getTarget().getClass().getMethod(methodSignature.getMethod().getName(), methodSignature.getParameterTypes());
        System.out.println(method);

        System.out.println(Arrays.toString(point.getArgs()));


        System.out.println(Arrays.toString(method.getAnnotations()));



        System.out.println(Arrays.toString(methodSignature.getMethod().getAnnotations()));

        return point.proceed();
    }





}

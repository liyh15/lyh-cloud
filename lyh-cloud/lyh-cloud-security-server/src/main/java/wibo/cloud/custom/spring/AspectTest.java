package wibo.cloud.custom.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname AspectTest
 * @Description TODO
 * @Date 2021/1/18 11:04
 * @Created by lyh
 */
@Configuration
@Aspect
public class AspectTest {

    // TODO 执行顺序  Around --> Before --> After --> AfterReturning / AfterThrowing

    @Pointcut("execution(* wibo.cloud.custom.spring.AspectBeanTest.aspectMethod())")
    public void pointCut() {}

    @Before("pointCut()")
    public void before() {
        System.out.println("这是before");
    }

    @After("pointCut()")
    public void after() {
        System.out.println("这是after");
    }

    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("这是afterReturning");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("这是afterThrowing");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint jp) {
        System.out.println("这是around开始");
        try {
            jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("这是around结束");
    }
}

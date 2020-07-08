package wibo.cloud.common.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wibo.cloud.common.response.BaseResponse;

/**
 * Feign接口的切面配置
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class FeginAspectConfig {

    /**
     *  切点一共有四个方法配置
     *  1、bean("userServiceImpl") 指bean名称为userServiceImpl里所有的方法
     *     bean("..ServiceImpl") 匹配所有bean名称后缀为ServiceImpl里所有的方法
     *  2、execution(* com.wibo.*.feign..*.*(..)) 第一个*表示方法返回值，倒数第一的*表示
     *     方法名，倒数第二的*表示类名，之前的都是包路径，*表示一层包路径，..表示子包所有路径
     *  3、within表达式应用于类级别，实现粗粒度的切入点表达式定义，案例分析：
     *     within("aop.service.UserServiceImpl")指定当前包中这个类内部的所有方法。
     *     within("aop.service.*") 指定当前目录下的所有类的所有方法。
     *     within("aop.service..*") 指定当前目录以及子目录中类的所有方法。
     *  4、@annotation
     *     @annotation表达式应用于方法级别，实现细粒度的切入点表达式定义，案例分析
     *     @annotation(anno.RequiredLog) 匹配有此注解描述的方法。
     *     @annotation(anno.RequiredCache) 匹配有此注解描述的方法。
     */
    @Pointcut("execution(* wibo.cloud.*.feign..*.*(..))")
    public void feignPointCut() {}

    @Around("feignPointCut()")
    public Object feignAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.error("feign开始调用接口{}", pjp.getTarget().getClass().getName());
        if (pjp.proceed() instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) pjp.proceed();
            log.error("feign接口的返回为{}", response);
            if (ErrorCode.Status.SUCESS.code.equals(response.getCode())) {
                // 如果feign接口调用成功，直接返回
                return response.getData();
            } else {
                // 如果feign那边出现业务异常，本地抛出异常
                BusinessException.throwExceptionByStatus(response.getCode(), response.getMessage());
            }
        } else {
            return pjp.proceed();
        }
        return null;
    }
}

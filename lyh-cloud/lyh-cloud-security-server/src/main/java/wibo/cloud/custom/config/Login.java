package wibo.cloud.custom.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 表示作用在方法上面
@Retention(RetentionPolicy.RUNTIME) // 表示整个运行时有效
public @interface Login {
}

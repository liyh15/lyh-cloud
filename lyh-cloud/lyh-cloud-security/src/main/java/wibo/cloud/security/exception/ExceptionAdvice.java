package wibo.cloud.security.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname ExceptionAdvice
 * @Description TODO
 * @Date 2021/3/9 14:40
 * @Created by lyh
 */
@Configuration
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exception(Exception exc) throws Exception {
        System.out.println("asdasdasda");
        throw new Exception("asdasd");
    }

    public static void main(String[] args) throws Exception {
        Float a = 1.32344435F;
        Float b = 0.01F;
        System.out.println(a);
        System.out.println(a + b);
        System.out.println(0.05 + 0.01);
    }
}

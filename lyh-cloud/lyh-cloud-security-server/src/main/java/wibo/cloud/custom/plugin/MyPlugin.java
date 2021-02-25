package wibo.cloud.custom.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * @Classname MyPlugin
 * @Description TODO
 * @Date 2021/1/8 16:11
 * @Created by lyh
 */
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare ",
                args = {Connection.class, Integer.class}) })
public class MyPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }
}

package wibo.cloud.custom.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.util.Properties;

/**
 * @Classname MyPlugin
 * @Description TODO
 * @Date 2021/1/12 16:16
 * @Created by lyh
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})})
@Slf4j
public class MyPlugin implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHanler = SystemMetaObject.forObject(statementHandler);
        Object object = null;
        while (metaStatementHanler.hasGetter("h")) {
            object = metaStatementHanler.getValue("h");
            metaStatementHanler = SystemMetaObject.forObject(object);
        }
        statementHandler = (StatementHandler) object;
        String sql = (String) metaStatementHanler.getValue("delegate.boundSql.sql");
        Long parameterObject = (Long) metaStatementHanler.getValue("delegate.boundSql.parameterObject");

        log.error("执行的SQL:【" + sql + "】");
        log.error("参数:【" + parameterObject + "】");
        Object obj = invocation.proceed();
        return obj;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

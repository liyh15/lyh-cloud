package wibo.cloud.custom.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;


import java.sql.Connection;

/**
 * @Classname PagePlugin
 * @Description TODO
 * @Date 2021/1/20 15:50
 * @Created by lyh
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})})
@Slf4j
public class PagePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHanler = SystemMetaObject.forObject(statementHandler);

        while (metaStatementHanler.hasGetter("h")) {
            Object plugin = metaStatementHanler.getValue("h");
            statementHandler = (StatementHandler) SystemMetaObject.forObject(plugin).getValue("target");
            metaStatementHanler = SystemMetaObject.forObject(statementHandler);
        }

        String sql = (String) metaStatementHanler.getValue("delegate.boundSql.sql");
        SqlSession sqlSession = null;

        log.error("PagePlugin:【" + sql + "】");
        log.error("PagePlugin:【" + metaStatementHanler.getValue("delegate.boundSql.parameterObject") + "】");
        Object obj = invocation.proceed();
        return obj;

    }
}

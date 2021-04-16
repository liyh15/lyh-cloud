package wibo.cloud.custom.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Map;

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
        BoundSql boundSql = (BoundSql) metaStatementHanler.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        SqlSessionTemplate sqlSession;
        log.error("PagePlugin:【" + boundSql.getSql() + "】");
        log.error("PagePlugin:【" + boundSql.getParameterObject() + "】");
        log.error("PagePlugin:【" + boundSql.getParameterMappings() + "】");
        PageTest pageTest = getPageTest(boundSql.getParameterObject());
        if (ObjectUtil.isNotNull(pageTest)) {
            Integer page = (pageTest.getCurrent() - 1) * pageTest.getSize();
            sql = "SELECT * FROM (" + sql + ") data limit " + page + "," + pageTest.getSize();
            metaStatementHanler.setValue("delegate.boundSql.sql", sql);
        }
        Object obj = invocation.proceed();
        return obj;
    }

    public PageTest getPageTest(Object paramObject) {
        if (ObjectUtil.isNotNull(paramObject)) {
            if (paramObject instanceof PageTest) {
                return (PageTest) paramObject;
            } else if (paramObject instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) paramObject;
                for (Object k : map.values()) {
                    if (k instanceof PageTest) {
                        return (PageTest) k;
                    }
                }
            }
        }
        return null;
    }
}

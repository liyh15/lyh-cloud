package wibo.cloud.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"wibo.cloud.custom.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig {

    @Autowired
    private MySqlConfiguration mySqlConfiguration;


    /**
     * @Conditional(TestCondition.class)
     *
     * 这句代码可以标注在类上面，表示该类下面的所有@Bean都会启用配置，也可以标注在方法上面，只是对该方法启用配置。
     *
     * @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
     * @ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）
     * @ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）
     * @ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）
     * @ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）
     * @ConditionalOnNotWebApplication（不是web应用）
     * DruidDataSourceAutoConfigure.class
     * @return
     */

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(mySqlConfiguration.getDriverClassName());
        druidDataSource.setUrl(mySqlConfiguration.getJdbcUrl());
        druidDataSource.setUsername(mySqlConfiguration.getUserName());
        druidDataSource.setPassword(mySqlConfiguration.getPassword());
        druidDataSource.setMaxActive(10);
        // TODO 设置最长等待连接时间，默认无限
        // druidDataSource.setMaxWait(1000);
        return druidDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        // TODO 数据库事务实现方式，会在线程中存入一个ThreadLocal<Map<Object, Object>> resources，这里面存储了SqlSession和Connection
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(
                resolver.getResources("classpath*:mapper/*Mapper.xml"));
        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factoryBean.getObject();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager imcTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}


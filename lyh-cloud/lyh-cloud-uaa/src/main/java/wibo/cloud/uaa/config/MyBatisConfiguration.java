package wibo.cloud.uaa.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * @Classname MyBatisConfiguration
 * @Description TODO
 * @Date 2021/4/13 9:19
 * @Created by lyh
 */
@Configuration
@MapperScan(basePackages = {"wibo.cloud.uaa.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfiguration {

    @Autowired
    private MyBatisProperties myBatisProperties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(myBatisProperties.getDriverClassName());
        druidDataSource.setUrl(myBatisProperties.getJdbcUrl());
        druidDataSource.setUsername(myBatisProperties.getUserName());
        druidDataSource.setPassword(myBatisProperties.getPassword());
        druidDataSource.setMaxActive(10);
        // TODO 设置最长等待连接时间，默认无限
        // druidDataSource.setMaxWait(1000);
        return druidDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
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

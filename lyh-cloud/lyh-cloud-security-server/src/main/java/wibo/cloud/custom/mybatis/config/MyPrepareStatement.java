package wibo.cloud.custom.mybatis.config;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Classname MyPrepareStatement
 * @Description 数据库初始化类
 * @Date 2021/1/26 14:50
 * @Created by lyh
 */
public class MyPrepareStatement {

    // 数据库连接
    private Connection connection;

    // 请求的sql.xml
    private MyMappedStatement myMappedStatement;

    // 请求入参
    private Object paramObject;


    public MyPrepareStatement(Connection connection, MyMappedStatement myMappedStatement, Object paramObject) {
        this.connection = connection;
        this.myMappedStatement = myMappedStatement;
        this.paramObject = paramObject;
    }

    /**
     * 创建预编译类
     * @param
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/26 15:02
     */
    public void setParam() {
        
    }
}

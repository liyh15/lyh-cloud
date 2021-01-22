package wibo.cloud.custom.mybatis.typehandler;

import java.sql.PreparedStatement;

/**
 * @Classname TypeHandler
 * @Description 类型转换
 * @Date 2021/1/22 17:24
 * @Created by lyh
 */
public interface TypeHandler {

    void setParam(PreparedStatement ps, int index, Object value);

    Object getResult(PreparedStatement ps, int index);
}

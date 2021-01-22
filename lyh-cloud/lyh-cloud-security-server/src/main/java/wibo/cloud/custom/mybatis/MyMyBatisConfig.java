package wibo.cloud.custom.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import wibo.cloud.common.pojo.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MyMyBatisConfig
 * @Description 自定义模拟mybaits配置类
 * @Date 2021/1/18 17:14
 * @Created by lyh
 */
@Configuration
public class MyMyBatisConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        SqlSession sqlSession;
        SqlSessionTemplate sqlSessionTemplate;
    }

    public static void update() {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.126.129:3306/test";
        String username = "root";
        String password = "851535";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO `test`.`teacher` (`id`, `name`, `age`) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 123);
            pstmt.setString(2, "asdasdasd");
            pstmt.setInt(3, 13);
            System.out.println(pstmt.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源,倒关
            try {
                if(rs != null) {
                    rs.close();
                }
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) {
                    con.close();
                }  //必须要关
            } catch (Exception e) {
            }
        }
    }

    public static void select() {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.126.129:3306/test";
        String username = "root";
        String password = "851535";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM teacher";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Teacher> list = new ArrayList<>();
            while (rs.next()) {
                 Integer id = rs.getInt("id");
                 String name = rs.getString("name");
                 Integer age = rs.getInt("age");
                 Teacher teacher = new Teacher(id, name, age);
                 list.add(teacher);
            }
            System.out.println(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源,倒关
            try {
                if(rs != null) {
                    rs.close();
                }
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) {
                    con.close();
                }  //必须要关
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
         select();
    }
}

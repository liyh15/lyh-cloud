package wibo.cloud.custom.mybatis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.models.auth.In;
import jodd.util.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import wibo.cloud.common.pojo.Teacher;
import wibo.cloud.custom.mybatis.config.MapperConfig;
import wibo.cloud.custom.mybatis.config.MyBoundSql;
import wibo.cloud.custom.mybatis.config.MyMappedStatement;
import wibo.cloud.custom.mybatis.config.TypeEnume;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname MyMyBatisConfig
 * @Description 自定义模拟mybaits配置类
 * @Date 2021/1/18 17:14
 * @Created by lyh
 */
@Configuration
@EnableConfigurationProperties
public class MyMyBatisConfig implements InitializingBean {

    @Autowired
    private MapperConfig mapperConfig;

    private static Map<String, MyMappedStatement> myMappedStatementMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        SqlSession sqlSession;
        SqlSessionTemplate sqlSessionTemplate;
    }

    public static void xmlTest() throws Exception {

        File mapperFile = new File("C:\\lyh-cloudd\\lyh-cloud\\lyh-cloud-security-server\\src\\main\\java\\wibo\\cloud\\custom\\mybatis\\xml");
        if (mapperFile.exists()) {
            if (mapperFile.isDirectory()) {
                File[]  fileList =  mapperFile.listFiles();
                for (File file : fileList) {
                    analysisMapperFile(file);
                }
            } else {
                throw new Exception("mapper File is not correct");
            }
        } else {
            throw new Exception("mapper File is not exist");
        }
    }

    /**
     * 分析xml文件
     * @param file
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/25 15:00
     */
    public static void analysisMapperFile(File file) {
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(file);
            Element element = document.getRootElement();
            // 获取mapper.xml的属性
            Attribute nameAttribute = element.attribute("namespace");
            if (ObjectUtil.isNull(nameAttribute)) {
                throw new Exception("namespace is null");
            }
            String nameSpace = nameAttribute.getValue();
            List<Element> elementList = element.elements();
            for (Element elem : elementList) {

                // 获取标签的类型，增删改查
                String name = elem.getName();
                TypeEnume typeEnume = TypeEnume.getTypeEnume(name);
                if (ObjectUtil.isNull(typeEnume)) {
                    throw new Exception(nameSpace + ":typeName:" + name + " is error, should is select,delete,update,insert");
                }
                // 获取标签所有的属性
                List<Attribute> attributeList = elem.attributes();
                if (CollUtil.isEmpty(attributeList)) {
                    throw new Exception(nameSpace + ":" +  typeEnume +  " attribute is empty");
                }
                // 获取标签属性集合
                Map<String, String> attributeMap = new HashMap<>();
                for (Attribute attribute : attributeList) {
                    attributeMap.put(attribute.getName(), attribute.getValue());
                }
                // 获取标签的id属性
                String id = attributeMap.get("id");
                if (StringUtil.isBlank(id)) {
                    throw new Exception(nameSpace + ":" +  elem.getText() +  " id is null");
                }
                // 判断查询标签是否有返回类型
                if (typeEnume.equals(TypeEnume.SELECT)) {
                    if (!attributeMap.containsKey("resultType")) {
                        throw new Exception(nameSpace + ":" +  elem.getText() +  " resultType is null");
                    }
                }
                nameSpace += "." +id;
                MyMappedStatement myMappedStatement = new MyMappedStatement(typeEnume, nameSpace, attributeMap, elem);

                Element element1 = elem.createCopy();

                myMappedStatementMap.put(nameSpace, myMappedStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        xmlTest();
        MyMappedStatement statement = myMappedStatementMap.get("wibo.cloud.custom.mapper.StudentMapper.selectBatch");
        Map<String, Object> map = new HashMap<>();
        List<Integer> aaa = new ArrayList<>();
        aaa.add(1);
        aaa.add(2);
        aaa.add(3);
        List<Integer> bbb = new ArrayList<>();
        bbb.add(4);
        bbb.add(5);
        bbb.add(6);
        List<Integer> eee = new ArrayList<>();
        eee.add(4);
        eee.add(5);
        eee.add(6);
        List<List<Integer>> ccc = new ArrayList<>();
        ccc.add(aaa);
        ccc.add(bbb);
        ccc.add(eee);
        map.put("age", ccc);
        map.put("name", "name");
        MyBoundSql.buildBoundSql(map, statement);


       /* Pattern pattern1 = Pattern.compile("\\$\\{(.*)\\}");
        String a = "asdasdasdasd#{aaa}asdaa#{sdasd}asds";
        Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = regex.matcher(a);
        String b = "aaa";
        a = a.replaceAll("#\\{"+b+"}","{aaa}");
        System.out.println(a);*/
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

    public static void insert() {
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

}

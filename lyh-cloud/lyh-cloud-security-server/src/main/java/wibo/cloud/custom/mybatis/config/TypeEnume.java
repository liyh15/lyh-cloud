package wibo.cloud.custom.mybatis.config;

/**
 * @Classname TypeEnume
 * @Description TODO
 * @Date 2021/1/18 17:19
 * @Created by lyh
 */
public enum  TypeEnume {
    INSERT,
    DELETE,
    SELECT,
    UPDATE;

    public static TypeEnume getTypeEnume(String name) {
        if (name.equals("insert")) {
             return INSERT;
        }
        if (name.equals("delete")) {
            return DELETE;
        }
        if (name.equals("update")) {
            return UPDATE;
        }
        if (name.equals("select")) {
            return SELECT;
        }
        return null;
    }
}

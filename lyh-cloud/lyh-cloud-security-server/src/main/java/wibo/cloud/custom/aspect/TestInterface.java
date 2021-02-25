package wibo.cloud.custom.aspect;

import org.apache.ibatis.annotations.Param;

/**
 * @Classname TestInterface
 * @Description TODO
 * @Date 2020/11/19 16:05
 * @Created by lyh
 */
public interface TestInterface {
    void aaa(@Param("xxxxx") String a);
}

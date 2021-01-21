package wibo.cloud.custom.config;

import lombok.Data;

/**
 * @Classname PageTest
 * @Description 分页参数
 * @Date 2021/1/21 15:09
 * @Created by lyh
 */
@Data
public class PageTest {

    /*
      当前页数
     */
    private Integer current;

    /*
      每页个数
     */
    private Integer size;
}

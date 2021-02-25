package wibo.cloud.custom.jvm;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname HeapDemo
 * @Description TODO
 * @Date 2020/9/11 10:42
 * @Created by lyh
 */
@Data
@ToString
public class HeapDemo {

    private String name;

    public HeapDemo(String name) {
        this.name = name;
    }

    public HeapDemo(){
    }

    public static void main(String[] args) throws InterruptedException {
        HeapDemo de1 = new HeapDemo("aaa");
        HeapDemo de2 = new HeapDemo("bbb");
        BeanUtils.copyProperties(de1,de2);
        System.out.println(de2);
    }
}

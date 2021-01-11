package wibo.cloud.custom.tiexin;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname TimeBeanResp
 * @Description 出诊安排返回实参
 * @Date 2021/1/11 14:23
 * @Created by lyh
 */
@Data
@ToString
public class TimeBeanResp {

    private String Message;

    private String Code;

    private List<TimeBean> Result;

}

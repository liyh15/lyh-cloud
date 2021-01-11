package wibo.cloud.custom.tiexin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname ArrangeResp
 * @Description TODO
 * @Date 2021/1/11 17:17
 * @Created by lyh
 */
@Data
@ToString
public class ArrangeResp {

    @JSONField(name = "TotalPage")
    private Integer TotalPage;

    @JSONField(name = "StartPage")
    private Integer StartPage;

    @JSONField(name = "PageSize")
    private Integer PageSize;

    @JSONField(name = "Message")
    private Integer Message;

    @JSONField(name = "TotalRecord")
    private Integer TotalRecord;

    @JSONField(name = "FromRecord")
    private Integer FromRecord;

    @JSONField(name = "ToRecord")
    private Integer ToRecord;

    @JSONField(name = "Code")
    private Integer Code;

    @JSONField(name = "SpecialName")
    private String SpecialName;

    @JSONField(name = "DateNow")
    private String DateNow;

    @JSONField(name = "Result")
    private List<ArrangeResult> Result;
}

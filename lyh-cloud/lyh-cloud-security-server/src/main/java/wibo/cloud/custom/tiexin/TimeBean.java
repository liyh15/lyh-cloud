package wibo.cloud.custom.tiexin;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname TimeBean
 * @Description 出诊安排
 * @Date 2021/1/11 14:07
 * @Created by lyh
 */
@Data
@ToString
public class TimeBean {

    private String DoctorSN;

    private String SerialNo;

    private String HintType;

    private String CommendTime;

    private String ArrangeID;

    private String Store;

    private String CommendScope;

    private String NumberSN;

    private String AccountType;

    private String ModeID;

    private String Remark;
}

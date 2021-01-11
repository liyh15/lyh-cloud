package wibo.cloud.custom.tiexin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Classname ArrangeResult
 * @Description TODO
 * @Date 2021/1/11 17:20
 * @Created by lyh
 */
@Data
@ToString
public class ArrangeResult {

    @JSONField(name = "TypeName")
    private String TypeName;

    @JSONField(name = "FHTimes")
    private String FHTimes;

    @JSONField(name = "ArrangeStatus")
    private Integer ArrangeStatus;

    private Integer securityDeposit;

    private String doctorname;

    @JSONField(name = "ModeId")
    private Integer ModeId;

    @JSONField(name = "IsPartTime")
    private Integer IsPartTime;

    @JSONField(name = "SpecialPriceManageWayName")
    private String SpecialPriceManageWayName;

    @JSONField(name = "StopRegisterDate")
    private String StopRegisterDate;

    private String doctorpic;

    private String clinicID;

    private Integer ghfeeway;

    @JSONField(name = "DiseaseName")
    private String DiseaseName;

    private Integer availablenum;

    private String hosname;

    private String diseaseAudit;

    private String deptname;

    @JSONField(name = "FHDays")
    private String FHDays;

    private String doctorid;

    private Integer levelid;

    @JSONField(name = "InvalidDate")
    private String InvalidDate;

    private Integer specialprice;

    private Integer timeid;

    private String photoprefix;

    private String cancelEndDate;

    private Integer houBuClosed;

    private Integer deptid;

    @JSONField(name = "AllFee")
    private Integer AllFee;

    private String appurl;

    @JSONField(name = "DiseaseId")
    private String DiseaseId;

    @JSONField(name = "WorkPlace")
    private String WorkPlace;

    private String isSetDisease;

    @JSONField(name = "DrawPoint")
    private String DrawPoint;

    @JSONField(name = "SpecialPriceManageWayRemark")
    private String SpecialPriceManageWayRemark;

    @JSONField(name = "ArrangeID")
    private Integer ArrangeID;

    @JSONField(name = "UnOpened")
    private Boolean UnOpened;

    private String levelname;

    @JSONField(name = "GhFee")
    private Integer GhFee;

    private String doctorGuid;

    private String sex;

    private Integer apptype;

    private String registerdate;

    private String special;

    private Integer isLockNum;

    private Integer marketing;

    @JSONField(name = "Type")
    private Integer Type;

    private Integer doctorsn;

    @JSONField(name = "EndDraw")
    private String EndDraw;

    @JSONField(name = "ArrRemark")
    private String ArrRemark;

    @JSONField(name = "IsCheckOrder")
    private Integer IsCheckOrder;

    @JSONField(name = "OverTime")
    private Integer OverTime;
}

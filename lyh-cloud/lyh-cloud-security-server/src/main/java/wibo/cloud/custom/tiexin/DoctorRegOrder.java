package wibo.cloud.custom.tiexin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Classname DoctorRegOrder
 * @Description TODO
 * @Date 2021/1/11 14:48
 * @Created by lyh
 */
@Data
@ToString
public class DoctorRegOrder {

    private String memberSn;

    private String memberName;

    private String memberPhone;

    private String memberAddress;

    private String memberBirthday;

    private String memberSex;

    private String memberId;

    private String othercard;

    private String guardianmembersn;

    private String identitytype;

    private String memberZhengjianid;

    private String memberIdcard;

    private String accManageGoodSn;

    private String waterId;

    private String waitingInfor;

    private String store;

    private String serialNo;

    private String regpaytype;

    private String cliniccard;

    private String applyNo;

    private String mobile;

    private String doctorSn;

    private String hosDeptId;

    private String hosDeptName;

    private String hospitalId;

    private String hospitalName;

    private String doctorService_gh;

    private String doctorUid;

    private String doctorName;

    private String doctorPic;

    private String doctorClinicName;

    @JSONField(name = "GH_HosProId")
    private String GH_HosProId;

    @JSONField(name = "GH_HosProName")
    private String GH_HosProName;

    @JSONField(name = "GH_HosCityId")
    private String GH_HosCityId;

    @JSONField(name = "GH_HosCityName")
    private String GH_HosCityName;

    private String registerDate;

    private Integer timeId;

    private Integer arrangeId;

    private Integer ghAmount;

    private Integer securityDeposit;

    private Integer ghfeeway;

    @JSONField(name = "ModeId")
    private Integer ModeId;

    @JSONField(name = "GhFee")
    private Integer GhFee;

    @JSONField(name = "AllFee")
    private Integer AllFee;

    private Integer availablenum;

    @JSONField(name = "UnOpened")
    private Boolean UnOpened;

    @JSONField(name = "FHTimes")
    private String FHTimes;

    @JSONField(name = "FHDays")
    private String FHDays;

    private Integer accountSn;

    private String cardNumber;

    private String loginId;

    private String channelId;

    private String utm_source;

    private String doctorOfficeName;
}

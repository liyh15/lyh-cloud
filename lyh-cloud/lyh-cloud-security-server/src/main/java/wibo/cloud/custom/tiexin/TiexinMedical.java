package wibo.cloud.custom.tiexin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname TiexinMedical
 * @Description 铁心桥预约破译
 * @Date 2021/1/11 11:54
 * @Created by lyh
 */
public class TiexinMedical {

    private static String registerUrl = "https://appoint.yihu.com/appoint/do/registerInfo/register";

    private static String timeUrl = "https://appoint.yihu.com/appoint/do/registerInfo/getNumbers";

    private static String arrangeUrl = "https://appoint.yihu.com/appoint/do/doctorArrange/getArrangeWater";

    private static String registerData1 = "{\"memberSn\":\"48455664\",\"memberName\":\"黄孖胤\",\"memberPhone\":\"18851048456\",\"memberAddress\":\"南京市雨花台区万科金色里程6栋911室\",\"memberBirthday\":\"1997-07-10\",\"memberSex\":\"2\",\"memberId\":\"48455664\",\"othercard\":\"\",\"guardianmembersn\":\"\",\"identitytype\":\"1\",\"memberZhengjianid\":\"3206**********7980\",\"memberIdcard\":\"3206**********7980\",\"accManageGoodSn\":null,\"waterId\":\"561430606\",\"waitingInfor\":\"08:30-09:00\",\"store\":\"\",\"serialNo\":\"28\",\"regpaytype\":\"\",\"cliniccard\":\"\",\"applyNo\":\"\",\"mobile\":\"18851048456\",\"doctorSn\":\"711071506\",\"hosDeptId\":\"7209582\",\"hosDeptName\":\"九价宫颈癌疫苗\",\"hospitalId\":\"1091777\",\"hospitalName\":\"铁心桥社区卫生服务中心\",\"doctorService_gh\":\"2\",\"doctorUid\":\"710743335\",\"doctorName\":\"九价宫颈癌疫苗\",\"doctorPic\":\"\",\"doctorClinicName\":\"医师\",\"GH_HosProId\":\"10\",\"GH_HosProName\":\"江苏\",\"GH_HosCityId\":\"77\",\"GH_HosCityName\":\"南京\",\"registerDate\":\"2021-01-18\",\"timeId\":1,\"arrangeId\":138235971,\"ghAmount\":0,\"securityDeposit\":0,\"ghfeeway\":0,\"ModeId\":0,\"GhFee\":0,\"AllFee\":0,\"availablenum\":10,\"UnOpened\":false,\"FHTimes\":\"07:30\",\"FHDays\":\"7\",\"accountSn\":122411285,\"cardNumber\":\"2052410106\",\"loginId\":\"off6ts-PX4i_EpE1ffZjB9iRokGk\",\"channelId\":\"1000031\",\"utm_source\":\"0.0.h.1026.bus010.0__0.0.h.1026.bus010.0\",\"doctorOfficeName\":\"\"}";

    private static String registerData2 = "[{\"keyValue\":\"南京市雨花台区万科金色里程6栋911室\",\"keyName\":\"familyaddress\"},{\"keyValue\":\"\",\"keyName\":\"name\"},{\"keyValue\":\"\",\"keyName\":\"ClinicCard\"},{\"keyValue\":\"320***********7980\",\"keyName\":\"CardNo\"},{\"keyValue\":\"2\",\"keyName\":\"sex\"},{\"keyValue\":\"18851048456\",\"keyName\":\"mobile\"},{\"keyValue\":\"1\",\"keyName\":\"cardtype\"},{\"keyValue\":\"56471\",\"keyName\":\"cmb_disease\"},{\"keyValue\":\"56471\",\"keyName\":\"cmb_disease\"},{\"keyValue\":\"预防接种\",\"keyName\":\"cmb_diseaseName\"}]\n";

    // private static String registerData1 = "{\"waterId\":\"561430614\",\"waitingInfor\":\"08:30-09:00\",\"store\":\"\",\"serialNo\":\"36\",\"memberSn\":\"48628473\",\"memberName\":\"李元浩\",\"memberPhone\":\"15720786592\",\"memberAddress\":\"南京市雨花台区万科金色里程6栋911室\",\"memberBirthday\":\"1997-10-25\",\"memberSex\":\"1\",\"memberId\":\"48628473\",\"othercard\":\"\",\"guardianmembersn\":\"\",\"identitytype\":\"1\",\"memberZhengjianid\":\"321***********2718\",\"memberIdcard\":\"321***********2718\",\"accManageGoodSn\":null,\"regpaytype\":\"\",\"cliniccard\":\"\",\"applyNo\":\"\",\"mobile\":\"15720786592\",\"doctorSn\":\"711071476\",\"hosDeptId\":\"7209535\",\"hosDeptName\":\"健康证办理\",\"hospitalId\":\"1091777\",\"hospitalName\":\"铁心桥社区卫生服务中心\",\"doctorService_gh\":\"2\",\"doctorUid\":\"710743326\",\"doctorName\":\"体检医生\",\"doctorPic\":\"\",\"doctorClinicName\":\"医师\",\"GH_HosProId\":\"10\",\"GH_HosProName\":\"江苏\",\"GH_HosCityId\":\"77\",\"GH_HosCityName\":\"南京\",\"registerDate\":\"2021-01-13\",\"timeId\":1,\"arrangeId\":138077068,\"ghAmount\":0,\"securityDeposit\":0,\"ghfeeway\":0,\"ModeId\":0,\"GhFee\":0,\"AllFee\":0,\"availablenum\":35,\"UnOpened\":false,\"FHTimes\":\"07:30\",\"FHDays\":\"7\",\"accountSn\":122411285,\"cardNumber\":\"2052410106\",\"loginId\":\"off6ts-PX4i_EpE1ffZjB9iRokGk\",\"channelId\":\"1000031\",\"utm_source\":\"0.0.h.1026.bus010.0__0.0.h.1026.bus010.0\",\"doctorOfficeName\":\"\"}";

    // private static String registerData2 = "[{\"keyValue\":\"南京市雨花台区万科金色里程6栋911室\",\"keyName\":\"familyaddress\"},{\"keyValue\":\"\",\"keyName\":\"name\"},{\"keyValue\":\"\",\"keyName\":\"ClinicCard\"},{\"keyValue\":\"320***********7980\",\"keyName\":\"CardNo\"},{\"keyValue\":\"2\",\"keyName\":\"sex\"},{\"keyValue\":\"18851048456\",\"keyName\":\"mobile\"},{\"keyValue\":\"1\",\"keyName\":\"cardtype\"},{\"keyValue\":\"56471\",\"keyName\":\"cmb_disease\"},{\"keyValue\":\"55337\",\"keyName\":\"cmb_disease\"},{\"keyValue\":\"体检\",\"keyName\":\"cmb_diseaseName\"}]\n";

    private static String cookie = "_SysTrackUUID=03185401801; jkzlAn_uuid=B4F46C4F-C6E2-4162-BEDF-90441E6B318E; jkzlAn_p=-1; jkzlAn_c=-1; _YyghSysTrackUUID=03185524128; HomeTemplateYiHu=%7B%22Code%22%3A10000%2C%22Message%22%3A%22%u6DFB%u52A0%u6210%u529F%22%2C%22Data%22%3A%7B%22Result%22%3A%5B%7B%22backColor%22%3A%22%22%2C%22backImg%22%3A%22%22%2C%22id%22%3A2691%2C%22insertTime%22%3A%222019-03-05%2010%3A10%3A07.0%22%2C%22jkzx%22%3A1%2C%22microWebId%22%3A14968%2C%22myzx%22%3A1%2C%22orgId%22%3A1091777%2C%22state%22%3A1%2C%22style%22%3A1%2C%22styleColor%22%3A%22133%2C187%2C215%3B88%2C182%2C230%3B82%2C161%2C202%22%2C%22template%22%3A5%2C%22transparency%22%3A%220%22%2C%22userBackImg%22%3A0%7D%5D%7D%7D; logintype=62; loginprovinceid=0; logincityid=0; BaseDoctorUid=0; BaseUserType=0; TOKEN_EE70CAEEDC43507F65ED1DBAE1FB8D5B=221E5D7E293A4297A4106292858E33BC; TOKEN_3D656A6DD6232C9C5B3CED6A1D327652=BBBF79487B1F424A8A12F4E0769BD962; jkzlAn_sid=9604F128-752C-492A-A23A-A3FD23ADCECB; jkzlAn_userid=122411285; YiHu_OpenId=eyJPcGVuSUQiOiJvZmY2dHMtUFg0aV9FcEUxZmZaakI5aVJva0drIiwiU2VjU3RyIjoiQ0NFRjI1NjJBMzQ5MTM1QjMyODY3OENCN0M4OUNDQ0EifQ%3D%3D; loginid=off6ts-PX4i_EpE1ffZjB9iRokGk; OpenID=off6ts-PX4i_EpE1ffZjB9iRokGk; LoginChannel=1000031; YiHu_UserJosn=eyJBY2NvdW50U24iOiIxMjI0MTEyODUiLCJDYXJkTnVtYmVyIjoiMjA1MjQxMDEwNiIsIkxvZ2luSWQiOiJvZmY2dHMtUFg0aV9FcEUxZmZaakI5aVJva0drIiwiVXNlck5hbWUiOiIiLCJTZWNTdHIiOiIzOTY4NjI0QTczODlBODI3MkU4OTQwNkE3Nzk3OEUyNCJ9; jkzlAn_channelid=1000031; jkzlAn_utm_source=0.0.h.1026.bus010.0__0.0.h.1026.bus010.0; jkzlAn_ct=1610351720353";

    public static void main(String[] args) {


        DoctorRegOrder doctorRegOrder = JSONObject.parseObject(registerData1, DoctorRegOrder.class);
        Map<String, Object> timeMap = new HashMap<>();
        timeMap.put("hospitalId", "1091777");
        timeMap.put("channelId", "1000031");
        Map<String, Object> registerMap = new HashMap<>();

        Map<String, Object> arrangeMap = new HashMap<>();
        arrangeMap.put("doctorSn", "711071506");
        arrangeMap.put("hospitalId", "1091777");
        arrangeMap.put("channelId", "1000031");
        // 获取安排入参
        HttpRequest arrangeRequest = HttpRequest.post(arrangeUrl).form(arrangeMap);

        // 获取就诊时间安排入参
        HttpRequest timeRequest = HttpRequest.post(timeUrl).form(timeMap);

        // 预约接口入参
        HttpRequest registerRequest = HttpRequest.post(registerUrl);


        registerRequest.header("Cookie", cookie);
        Boolean isEnd = true;
        List<TimeBean> timeBeanList = null;
        int a = 1;
        long st = System.currentTimeMillis();
        while (isEnd) {
            List<ArrangeResult> Result = JSONObject.parseObject(arrangeRequest.execute().body(), ArrangeResp.class).getResult();
            if (CollUtil.isNotEmpty(Result)) {
                ArrangeResult result = Result.get(1);
                if (result.getAvailablenum().compareTo(0) > 0) {
                    // 有剩余的名额
                    timeMap.put("arrangeId", result.getArrangeID());
                    HttpResponse httpResponse = timeRequest.form(timeMap).execute();
                    String tokenResult = httpResponse.body();
                    TimeBeanResp timeBean = JSONObject.parseObject(tokenResult, TimeBeanResp.class);
                    if (ObjectUtil.isNotNull(timeBean) && CollUtil.isNotEmpty(timeBeanList = timeBean.getResult())) {
                        TimeBean time = timeBeanList.get(0);
                        System.out.println(time);
                        // 设置排号时间
                        /*
                          获取安排可以拿到的值
                          FHTimes
                          timeid
                          FHDays
                          ArrangeID
                          ModeId
                          registerdate
                        */
                        doctorRegOrder.setWaterId(time.getNumberSN());
                        doctorRegOrder.setSerialNo(time.getSerialNo());
                        doctorRegOrder.setWaitingInfor(time.getCommendScope());
                        doctorRegOrder.setFHTimes(result.getFHTimes());
                        doctorRegOrder.setTimeId(result.getTimeid());
                        doctorRegOrder.setFHDays(result.getFHDays());
                        doctorRegOrder.setArrangeId(result.getArrangeID());
                        doctorRegOrder.setModeId(result.getModeId());
                        doctorRegOrder.setRegisterDate(result.getRegisterdate());
                        doctorRegOrder.setSecurityDeposit(result.getSecurityDeposit());
                        doctorRegOrder.setGhfeeway(result.getGhfeeway());
                        doctorRegOrder.setGhFee(result.getGhFee());
                        doctorRegOrder.setAllFee(result.getAllFee());
                        doctorRegOrder.setAvailablenum(result.getAvailablenum());
                        doctorRegOrder.setUnOpened(result.getUnOpened());
                        registerMap.put("doctorRegOrder", JSONObject.toJSONString(doctorRegOrder));
                        registerMap.put("ghFormCon", registerData2);
                        HttpResponse registerResponse = registerRequest.form(registerMap).execute();
                        System.out.println(JSONObject.toJSONString(doctorRegOrder));
                        System.out.println(registerResponse);
                        isEnd = false;
                    }
                }
            }
            if ((System.currentTimeMillis() - st) >= 1000) {
                st = System.currentTimeMillis();
                System.out.println(a);
                a = 0;
            }
            a = a + 1;
        }
    }
}

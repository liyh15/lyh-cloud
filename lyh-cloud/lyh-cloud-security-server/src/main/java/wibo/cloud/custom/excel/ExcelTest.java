package wibo.cloud.custom.excel;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ExcelTest
 * @Description TODO
 * @Date 2020/11/30 17:23
 * @Created by lyh
 */
public class ExcelTest {
    public static void main(String[] args) {
        BigExcelWriter writer = ExcelUtil.getBigWriter("C:\\Users\\lyh\\Desktop\\单词背诵\\rrrrrrrr.xlsx");
        List<TOrderUnpaidExportEntity> list = new ArrayList<>();
        TOrderUnpaidExportEntity exportEntity = new TOrderUnpaidExportEntity();
        exportEntity.setId(123L);
        exportEntity.setCardSn("asdasd");
        exportEntity.setLockStationName("rrrrrrrrrrrrrrrrrrrrr");
        list.add(exportEntity);
        list.add(exportEntity);
        writer.addHeaderAlias("id", "序号");
        writer.addHeaderAlias("cardSn", "卡号");
        writer.addHeaderAlias("orderNo", "锁卡订单号");
        writer.addHeaderAlias("lockTime", "锁卡时间");
        writer.addHeaderAlias("lockStationName", "锁卡设备");
        writer.addHeaderAlias("actualAmount", "交易金额");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("payStatus", "是否解锁");
        writer.addHeaderAlias("payTime", "解锁时间");
        writer.addHeaderAlias("unlockBranchName", "解锁网点");
        writer.addHeaderAlias("unlockStationName", "解锁终端");
        writer.write(list, true);
        Row row = writer.getOrCreateRow(writer.getCurrentRow());
        row.createCell(0).setCellValue("合计");
        row.createCell(5).setCellValue(123123123);
        System.out.println();
        writer.close();
    }
}

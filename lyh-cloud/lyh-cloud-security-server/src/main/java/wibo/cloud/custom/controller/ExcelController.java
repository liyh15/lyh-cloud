package wibo.cloud.custom.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wibo.cloud.common.pojo.Teacher;

import java.io.IOException;
import java.util.List;

/**
 * @Classname ExcelController
 * @Description TODO
 * @Date 2020/12/11 8:46
 * @Created by lyh
 */
@RestController
@Slf4j
public class ExcelController {

    @PostMapping("excel")
    public String excel(@RequestParam("file") MultipartFile file, @Validated Teacher teacher) throws IOException {
        ExcelReader pileReader= ExcelUtil.getReader(file.getInputStream(), "桩信息");
        ExcelReader gunReader = ExcelUtil.getReader(file.getInputStream(), "枪信息");
        System.out.println(teacher);
        pileReader.setIgnoreEmptyRow(false);
        List<List<Object>> list = pileReader.read(1);
        System.out.println(pileReader.isIgnoreEmptyRow());

        for (List<Object> list1 : list) {
            /*System.out.println(list1.get(1).getClass());
            String a = "aaa";
            System.out.println(a.length());
            System.out.println(StrUtil.length(a));*/
            System.out.println(list1);
        }
        return "aaa";
    }
}

package wibo.cloud.fastdfs.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wibo.cloud.common.response.BaseResponse;
import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public StorePath uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        BaseResponse baseResponse;
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        StorePath path = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
        return path;
    }
}

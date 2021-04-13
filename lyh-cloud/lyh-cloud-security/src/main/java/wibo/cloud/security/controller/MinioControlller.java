package wibo.cloud.security.controller;

import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wibo.cloud.security.minio.MinioServiceImpl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Classname MinioControlller
 * @Description TODO
 * @Date 2021/3/22 14:49
 * @Created by lyh
 */
//@RestController
public class MinioControlller {

    @Autowired
    private MinioServiceImpl  minioService;

    @PostMapping("uploadFile")
    public String uploadFile(MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InternalException {
        return minioService.uploadFile(file);
    }
}

package wibo.cloud.security.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wibo.cloud.security.config.MinioConfig;
import wibo.cloud.security.config.MinioConfigProperties;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Classname MinioServiceImpl
 * @Description TODO
 * @Date 2021/3/22 14:40
 * @Created by lyh
 */
//@Service
public class MinioServiceImpl {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioConfigProperties properties;

    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return properties.getUrl() + "/" + properties.getBucketName() + "/" + file.getOriginalFilename();
    }
}

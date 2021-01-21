package com.apocalypse.example;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/11/17
 */
public class AliyunOssTest {

    @Test
    void upload() {
        String accessKeyId = "LTAI4GDJfZFAs8iDiLZxUvFB";
        String accessKeySecret = "freAF6daVDdOurQaF41qqDlDlMHNtL";
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String bucketName = "apocalypse-oss";

        String objectName = "/Users/jingkaihui/develop/code/java/company/health-form/upload/2020/11/iShot2020-10-24 11.29.18.png-5854d2f2-2790-4ad9-91b0-7bfc659ca170.png";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建存储空间。
//        ossClient.createBucket(bucketName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "upload/2020/11/iShot2020-10-24 11.29.18.png-5854d2f2-2790-4ad9-91b0-7bfc659ca170.png", new File(objectName));
        ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();

    }
}

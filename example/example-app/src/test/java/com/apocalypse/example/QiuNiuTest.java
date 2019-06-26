package com.apocalypse.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.junit.Test;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/5
 */
public class QiuNiuTest {

    @Test
    public void upload() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = "H9L8g6MJv1jH0CwTO_MwBVMvZBmWolQS1anHx8m1";
        String secretKey = "qmzH_x98Np_8D9V3aRJwgX3JuCdlNErVgCRM72Xg";
        String bucket = "qiniu_bucket_01";
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            StringMap putPolicy = new StringMap();

            JSONObject jsonObject1 = new JSONObject()
                    .fluentPut("bucket", "${bucket}")
                    .fluentPut("key", "${key}")
                    .fluentPut("etag", "${etag}")
                    .fluentPut("fname", "${fname}")
                    .fluentPut("fsize", "${fsize}")
                    .fluentPut("mimeType", "${mimeType}")
                    .fluentPut("endUser", "${endUser}");

            //            putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
            putPolicy.put("returnBody", jsonObject1.toJSONString());
            String localFilePath = "C:\\Users\\8918\\Downloads\\谷雨.jpg";
            Response response = uploadManager.put(localFilePath, null, upToken);
            //解析上传成功的结果
            JSONObject jsonObject = JSON.parseObject(response.bodyString());
            System.out.println(JSON.toJSONString(jsonObject, true));
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}

package com.apocalypse.common.util;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
@Configuration
@PropertySource(value = "classpath:/system.properties")
public class PwdUtil {

    @Value("${rsa.private}")
    private String privateKey;

    @Value("${rsa.public}")
    private String publicKey;

    public static RSA rsa;

    public static Sign sign;

    @PostConstruct
    public void init() {
        rsa = SecureUtil.rsa(privateKey, publicKey);
        sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, privateKey, null);
    }

    /**
     * 对字符串加密，返回加密后的base64编码
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        return Base64.encode(rsa.encrypt(data, KeyType.PublicKey));
    }

    /**
     * 对加密后的Base64串进行解密得到原数据
     * @param data 加密后的Base64串
     * @return 原数据
     */
    public static String decrypt(String data) {
        String decrypt;
//        try {
            decrypt = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), "UTF-8");
//        } catch (Exception e) {
//            log.warn("解密失败");
//            throw new HjException(ResultCode.DECRYPT_ERROR);
//        }
        return decrypt;
    }

    /**
     * 根据用户密码和随机sign生成一个md5串作为密码
     *
     * @param password
     * @param sign
     * @return
     * @Description
     */
    public static String passwordGenerate(String password, String sign) {
        return SecureUtil.md5(password + sign).substring(5, 30);
    }

    /**
     * 把map中的元素使用&进行拼接并拼接随机字符串
     * @param params
     * @param randomStr
     * @return
     */
    public static String paramsJoin(Map<String, Object> params, String randomStr) {
        params.put("randomStr", randomStr);
        return MapUtil.join(MapUtil.sort(params), "&", "=", true);
    }

    /**
     * 对数据进行签名
     * @param params
     * @return
     */
    public static String sign(Map<String, Object> params) {
        return signInternal(params);
    }

    /**
     * 对数据进行签名
     * @param params
     * @return
     */
    public static String sign(JSONObject params) {
        return signInternal(params);
    }

    private static String signInternal(Map<String, Object> params) {
        String joinStr = paramsJoin(params, IdUtil.fastSimpleUUID());
        //对待发送数据进行签名
        byte[] originalSign = sign.sign(joinStr.getBytes(Charset.forName("UTF-8")));

        //对签名进行Base64编码
        return Base64.encode(originalSign);
    }
}

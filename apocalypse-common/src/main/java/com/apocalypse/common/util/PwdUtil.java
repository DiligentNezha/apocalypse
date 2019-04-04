package com.apocalypse.common.util;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@PropertySource(value = "classpath:/system.properties")
public class PwdUtil {

    @Value("${rsa.private}")
    private String privateKey;

    @Value("${rsa.public}")
    private String publicKey;

    public static RSA rsa;

    @PostConstruct
    public void init() {
        rsa = SecureUtil.rsa(privateKey, publicKey);
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
        String decrypt = null;
        try {
            decrypt = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), "UTF-8");
        } catch (Exception e) {
            log.warn("解密失败");
            throw new RuntimeException(e);
        }
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
}

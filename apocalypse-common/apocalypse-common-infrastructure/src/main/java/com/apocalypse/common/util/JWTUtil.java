package com.apocalypse.common.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@PropertySource(value = "classpath:/system.properties")
public class JWTUtil {

    @Value("${rsa.private}")
    private String privateKey;

    @Value("${rsa.public}")
    private String publicKey;

    private static RSAKeyProvider RSA_KEY_PROVIDER;

    private static Algorithm ALGORITHM;

    @PostConstruct
    private void init() {
        RSA rsa = SecureUtil.rsa(privateKey, publicKey);
        RSA_KEY_PROVIDER = new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) {
                return (RSAPublicKey) rsa.getPublicKey();
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return (RSAPrivateKey) rsa.getPrivateKey();
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };
        ALGORITHM = Algorithm.RSA256(RSA_KEY_PROVIDER);
    }

    public static JWTCreator.Builder newBuilder() {
        JWTCreator.Builder builder = JWT.create();
        return builder;
    }

    public static Verification newVerify() {
        return JWT.require(ALGORITHM);
    }

    /**
     * 根据builder进行签名
     * @param builder
     * @return
     */
    public static String sign(JWTCreator.Builder builder) {
        return builder.sign(ALGORITHM);
    }

}

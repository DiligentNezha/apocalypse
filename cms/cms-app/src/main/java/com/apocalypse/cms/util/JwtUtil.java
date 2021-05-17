package com.apocalypse.cms.util;

import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2021/3/9
 */
public class JwtUtil {

    //过期时间:秒
    public static final int EXPIRE = 5;

    // publicKey MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIZWZUiTIJ6STPwusev5UBV1ltF7qk3wXKxIhBgcCdLh57Zo2vOYDnfiug4D3sa0PusHwqRAY0dV1h9M8/stOySURjYaZYVeG2oKEtn2ZfrrxXi+1mc6dhuygcjGwwCJu0zdjD59auwoLco6FgkDfLvQaVDwpIDQQxyRlCk0wNQwIDAQAB
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIZWZUiTIJ6STPwusev5UBV1ltF7qk3wXKxIhBgcCdLh57Zo2vOYDnfiug4D3sa0PusHwqRAY0dV1h9M8/stOySURjYaZYVeG2oKEtn2ZfrrxXi+1mc6dhuygcjGwwCJu0zdjD59auwoLco6FgkDfLvQaVDwpIDQQxyRlCk0wNQwIDAQAB";

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAPublicKey) SecureUtil.rsa(null, PUBLIC_KEY).getPublicKey(), null)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }
}

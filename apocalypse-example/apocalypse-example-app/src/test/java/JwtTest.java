import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.util.JWTUtil;
import com.apocalypse.example.ExampleApplication;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class JwtTest {
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRXYFYTGI9uVipfl9P5loLAWLRIQPpSznBc1ACIpCO" +
            "/ptKYLXjzunWz2TyCj5OV1yjs9pEIcyOnxs6ESplsUOsEakf6wDgox6sU3A51mQmQlm6ALxtfguurZGOJ0Ksg/gL1q97YWTSMsH9R1slDV95nvMKsQAd4Yd/6i+2/ihaxQIDAQAB";

    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJFdgVhMYj25WKl+X0/mWgsBYtEhA" +
            "+lLOcFzUAIikI7+m0pgtePO6dbPZPIKPk5XXKOz2kQhzI6fGzoRKmWxQ6wRqR/rAOCjHqxTcDnWZCZCWboAvG1+C66tkY4nQqyD+AvWr3thZNIywf1HWyUNX3me8wqxAB3hh3/qL7b+KFrFAgMBAAECgYAoC4BEWNZ/dn3MQ3nyQtzvRzR3tAal0AlbF28lB2yXV+BjvvycgzW77Wo7m0LKxhpJJpSsTDtT1tTjTDNHzGt9oxscdWDYAgaQZpDaL/nBH4A9AJG2gPfszJUGrN7HtAQJWNAQUDwcdMSuz9rJRUt3T1e/QEz8nobzuPQ1AWACgQJBAMQZ8MaCGGcBcowKMY6F3RWP7pCe2ukJtsiH4FwqE3K5R8g2+/CWn3oRbHz7erydBpgToQJWbccbUs2FAJNc+VUCQQC9xEdwiV5xKprzTziCHohXgatv6CgcgK5oiuxMgfHgq1WkN2zq8XemjUrghy6q4AxE9viVnj78DkZaZ/vKRBuxAkAnwG5rfxG9R7DVrHdRQdeIOG4OyPTtSnfP/KNBa5IXrnFbp7G4mn/necK5Ly05MMeWalw4IhcMxoApgy2TscQlAkEAlblTlFsOBMPU1bvfnepxMHnCxdyqKTLuaNWTcxnjuZv1Skfgy84Q1XwNY/HExFVZ2N/zajkdAMpSf+ojI4dxQQJAO7Nio7zdggqd8nu2Cy7K9FWg3XEqff4HMZ8PKakwhOaq2p0gOUc4aVM1ajd2FzGU8yb1Rcwu5EfM19bv5lRsCQ==";

    @Test
    public void test1() {
        JWTCreator.Builder builder = JWTUtil.newBuilder()
                .withClaim("age", 26)
                .withClaim("name", "小明")
                .withClaim("salary", 12000);

        String sign = JWTUtil.sign(builder);

        JWTVerifier verifier = JWTUtil.newVerify().build();

        DecodedJWT verify = verifier.verify(sign);
        System.out.println(verify.getClaim("name").asString());
        System.out.println(JSONObject.toJSONString(verify, true));

    }

    @Test
    public void test() throws Exception{
        Algorithm algorithm = Algorithm.HMAC256("secret");

        DateTime now = DateUtil.date();
        String token = JWT.create()
                //header
                //playloader
                //发行者
                .withIssuer("admin")
                //签发主体
                .withSubject("jiusuqianbao")
                //受众
                .withAudience("xiangming", "xiaowang")
                //过期时间
                .withExpiresAt(now.offsetNew(DateField.MINUTE,5))
                //不可用时间
                .withNotBefore(now.offsetNew(DateField.SECOND, 3))
                //签发时间
                .withIssuedAt(now)
                //jwt唯一标识
                .withJWTId(IdUtil.simpleUUID())
                .withClaim("hello", "world")
                .withClaim("name", "zhangsan")
                .sign(algorithm);

        System.out.println(token);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("admin")
                .withSubject("jiusuqianbao")
                .build();

        Thread.sleep(3000);
        DecodedJWT verify = verifier.verify(token);

        Map<String, Claim> claims = verify.getClaims();
        System.out.println(JSONObject.toJSONString(claims, true));

        Date notBefore = verify.getNotBefore();
        DateTime currentDate = DateUtil.date();
        System.out.println(currentDate.isBefore(notBefore));

        System.out.println(JSONObject.toJSONString(verify, true));

        RSAKeyProvider rsaKeyProvider = new RSAKeyProvider() {

            @Override
            public RSAPublicKey getPublicKeyById(String keyId) {
                return null;
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return null;
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };

    }

}

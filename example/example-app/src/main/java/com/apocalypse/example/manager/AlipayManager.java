package com.apocalypse.example.manager;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/15
 */
@Slf4j
@Data
@Component
@PropertySource("classpath:/alipay.properties")
public class AlipayManager {

    /**
     * 应用授权回调地址
     */
    @Value("${app.authorize.redirect}")
    private String authorizeRedirect;

    /**
     * 支付宝网关
     */
    @Value("${alipay.gateway}")
    private String alipayGateway;

    /**
     * 支付宝授权地址
     */
    @Value("${alipay.authorize}")
    private String alipayAuthorize;

    /**
     * 支付宝网关
     */
    @Value("${alipay.appId}")
    private String appId;

    /**
     * 应用的私钥
     */
    @Value("${app.rsa.privateKey}")
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @Value("${alipay.rsa.publicKey}")
    private String alipayPublicKey;



    public AlipayClient alipayClient;

    @PostConstruct
    public void init() {
        alipayClient = new DefaultAlipayClient(alipayGateway, appId, privateKey, "json", StandardCharsets.UTF_8.name(),
                alipayPublicKey, "RSA2");
    }

}

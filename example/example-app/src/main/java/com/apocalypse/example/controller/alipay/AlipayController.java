package com.apocalypse.example.controller.alipay;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.domain.SettleDetailInfo;
import com.alipay.api.domain.SettleInfo;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.example.manager.AlipayManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/19
 */
@Slf4j
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    public static final String APP_ID = "";

    public static final String METHOD = "";

    public static final String CHARSET = "utf=8";

    public static final String SIGN_TYPE = "RSA2";


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AlipayManager alipayManager;

    @PostMapping("/trade/precreate")
    public Rest<BaseResponse> precreate() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2019061565614081",
                alipayManager.getPrivateKey(),
                "json",
                "utf-8",
                alipayManager.getAlipayPublicKey(),
                "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        AlipayTradePrecreateModel tradePrecreateModel = new AlipayTradePrecreateModel();
        String outTradeNo = IdUtil.objectId();
        tradePrecreateModel.setOutTradeNo(outTradeNo);

        tradePrecreateModel.setTotalAmount("0.1");
        tradePrecreateModel.setSubject("易学习");

        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(IdUtil.objectId());
        goodsDetail.setGoodsName("易学习账号");
        goodsDetail.setQuantity(1L);
        goodsDetail.setPrice("0.1");

        tradePrecreateModel.setGoodsDetail(Arrays.asList(goodsDetail));

        SettleInfo settleInfo = new SettleInfo();

        SettleDetailInfo settleDetailInfo = new SettleDetailInfo();
        settleDetailInfo.setTransInType("loginName");
        settleDetailInfo.setTransIn("17765823217");
        settleDetailInfo.setAmount("0.1");

        settleInfo.setSettleDetailInfos(Arrays.asList(settleDetailInfo));

        tradePrecreateModel.setSettleInfo(settleInfo);

        request.setBizModel(tradePrecreateModel);

        redisTemplate.opsForValue().set("alipay_request_" + outTradeNo, request);

        AlipayTradePrecreateResponse response = alipayClient.execute(request);



        QrCodeUtil.generate(response.getQrCode(), 900, 1600, FileUtil.file("alipay.jpg"));
        redisTemplate.opsForValue().set("alipay_response_" + outTradeNo, response);

        return Rest.vector("alipay", response, AlipayResponse.class);
    }
}

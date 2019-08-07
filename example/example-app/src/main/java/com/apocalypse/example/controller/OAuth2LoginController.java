package com.apocalypse.example.controller;

import cn.hutool.core.util.IdUtil;
import com.apocalypse.example.manager.AlipayManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/15
 */
@Slf4j
@Controller
@RequestMapping("/oauth/login")
@Api(value = "OAuth2案例", tags = {"OAuth2案例"}, consumes = "application/json")
public class OAuth2LoginController {

    @Autowired
    private AlipayManager alipayManager;
    /**
     * 支付宝登录跳转地址
     * @return
     */
    @GetMapping("/alipay")
    public ModelAndView alipay() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(alipayManager.getAlipayAuthorize());
        sb.append("?app_id=");
        sb.append(alipayManager.getAppId());
        sb.append("&scope=auth_user");
        sb.append("&state=");
        sb.append(IdUtil.simpleUUID());
        sb.append("&redirect_uri=");
        sb.append(URLEncoder.encode(alipayManager.getAuthorizeRedirect(), "UTF-8"));
        return new ModelAndView(sb.toString());
    }


}

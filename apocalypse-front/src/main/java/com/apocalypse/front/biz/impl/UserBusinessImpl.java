package com.apocalypse.front.biz.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apocalypse.common.constant.VersionConsant;
import com.apocalypse.common.exception.BusinessException;
import com.apocalypse.example.dubbo.simple.DubboUserService;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.front.biz.UserBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl implements UserBusiness {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessImpl.class);

    @Reference(version = VersionConsant.VERSION)
    private DubboUserService dubboUserService;

    @Override
    public String hello(String name) {
        return dubboUserService.sayHello(name);
    }

    @Override
    public UserModel getUser(String userId) throws BusinessException {
        UserModel user = null;
        try {
            user = dubboUserService.getUser(userId);
            //处理业务时，预知到的异常
            if (RandomUtil.randomInt() % 2 == 0) {
                logger.warn("Business 层具体业务异常描述信息（此处会记录在日志中）");
                throw new BusinessException("240002", "Business 具体业务异常描述（此处会返回给前端）");
            }
            //处理业务时，抛出可能未预知到的异常
            int i = 10 / RandomUtil.randomInt(3);
        } catch (Exception e) {
            String template = "Business 获取用户异常（此处会记录在日志中）用户编号:{}，用户姓名:{}";
            throw new BusinessException(logger, "140002", "Business 获取用户异常（此处会返回给前端）", e, template, "1021212", "小哪吒");
        }
        return user;
    }
}

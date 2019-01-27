package com.apocalypse.front.biz.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.apocalypse.common.constant.VersionConsant;
import com.apocalypse.common.exception.FrontException;
import com.apocalypse.common.exception.ServiceException;
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
    public UserModel getUser(String userId) throws FrontException {
        UserModel userModel = null;
        try {
            userModel = dubboUserService.getUser(userId);
        } catch (ServiceException e) {
            logger.error(e.getMsg(), e);
            throw new FrontException(e.getCode(), e.getMsg(), e);
        }
        return userModel;
    }
}

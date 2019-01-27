package com.apocalypse.example.dubbo.simple.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.apocalypse.common.exception.ServiceException;
import com.apocalypse.example.dubbo.simple.DubboUserService;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class DubboUserServiceImpl implements DubboUserService {

    private static final Logger logger = LoggerFactory.getLogger(DubboUserServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public UserModel getUser(String userId) throws ServiceException {
        return userService.getUser(userId);
    }
}

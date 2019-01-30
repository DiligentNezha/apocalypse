package com.apocalypse.example.dubbo.simple.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.apocalypse.common.exception.DubboException;
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
    public UserModel getUser(String userId) throws DubboException {
        UserModel user = null;
        try {
            user = userService.getUser(userId);
            //处理业务时，预知到的异常
            if (RandomUtil.randomInt() % 2 == 0) {
                logger.warn("Dubbo 层具体业务异常描述信息（此处会记录在日志中）");
                throw new DubboException("230002", "Dubbo 具体业务异常描述（此处会返回给前端）");
            }
            //处理业务时，抛出可能未预知到的异常
            int i = 10 / RandomUtil.randomInt(3);
        } catch (Exception e) {
            String template = "Service 获取用户异常（此处会记录在日志中）用户编号:{}，用户姓名:{}";
            throw new DubboException(logger, "130002", "Dubbo 获取用户异常（此处会返回给前端）", e, template, "1021212", "小哪吒");
        }
        return user;
    }
}

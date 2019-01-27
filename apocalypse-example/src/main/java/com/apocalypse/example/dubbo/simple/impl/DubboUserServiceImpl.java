package com.apocalypse.example.dubbo.simple.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.apocalypse.example.dubbo.simple.DubboUserService;

@Service(version = "1.0.0")
public class DubboUserServiceImpl implements DubboUserService {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}

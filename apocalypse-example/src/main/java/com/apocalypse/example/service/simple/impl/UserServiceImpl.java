package com.apocalypse.example.service.simple.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.simple.UserModelMapper;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail jingkaihui@adpanshi.com
 */
@Transactional(rollbackFor = Exception.class)
@org.springframework.stereotype.Service
@Service(version = "1.0.0",delay =-1,retries = 0,timeout = 1000*10)
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;

}

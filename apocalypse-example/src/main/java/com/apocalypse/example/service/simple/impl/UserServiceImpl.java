package com.apocalypse.example.service.simple.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.simple.UserModelMapper;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;

}

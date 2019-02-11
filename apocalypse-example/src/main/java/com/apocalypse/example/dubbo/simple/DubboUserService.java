package com.apocalypse.example.dubbo.simple;

import com.apocalypse.common.exception.DubboException;
import com.apocalypse.example.dto.LoginInfoDTO;
import com.apocalypse.example.model.UserModel;

public interface DubboUserService {
    String sayHello(String name);

    UserModel getUser(String userId) throws DubboException;

    LoginInfoDTO login(LoginInfoDTO loginInfoDTO);
}

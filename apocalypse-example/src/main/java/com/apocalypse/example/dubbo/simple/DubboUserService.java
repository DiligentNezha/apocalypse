package com.apocalypse.example.dubbo.simple;

import com.apocalypse.common.exception.ServiceException;
import com.apocalypse.example.model.UserModel;

public interface DubboUserService {
    String sayHello(String name);

    UserModel getUser(String userId) throws ServiceException;
}

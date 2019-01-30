package com.apocalypse.front.biz;

import com.apocalypse.common.exception.BusinessException;
import com.apocalypse.example.model.UserModel;

public interface UserBusiness {
    String hello(String name) throws BusinessException;

    UserModel getUser(String userId) throws BusinessException;
}

package com.apocalypse.front.biz;

import com.apocalypse.common.exception.FrontException;
import com.apocalypse.example.model.UserModel;

public interface UserBusiness {
    String hello(String name) throws FrontException;

    UserModel getUser(String userId) throws FrontException;
}

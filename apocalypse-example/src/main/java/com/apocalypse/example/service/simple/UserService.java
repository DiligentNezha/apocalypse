package com.apocalypse.example.service.simple;

import com.apocalypse.common.exception.ServiceException;
import com.apocalypse.common.service.BaseService;
import com.apocalypse.example.model.UserModel;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
public interface UserService extends BaseService<UserModel> {

    UserModel getUser(String userId) throws ServiceException;

}

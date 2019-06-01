package com.apocalypse.user.service;

import com.apocalypse.common.service.BaseService;
import com.apocalypse.user.dto.UserRegisterDTO;
import com.apocalypse.user.model.UserDO;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
public interface UserService extends BaseService<UserDO, Long> {

    Long register(UserRegisterDTO userRegister);

}

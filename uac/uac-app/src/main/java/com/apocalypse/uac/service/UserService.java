package com.apocalypse.uac.service;

import com.apocalypse.common.service.BaseService;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.model.UserDO;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
public interface UserService extends BaseService<UserDO, Long> {

    Long register(UserRegisterDTO userRegister);

}

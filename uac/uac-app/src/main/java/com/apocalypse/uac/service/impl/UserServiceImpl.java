package com.apocalypse.uac.service.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.uac.convert.UserConvert;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.mapper.single.UserDOMapper;
import com.apocalypse.uac.model.UserDO;
import com.apocalypse.uac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDO, Long> implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserConvert userConvert;

    @Override
    public Long register(UserRegisterDTO userRegister) {
        UserDO user = userConvert.convert(userRegister);

        userDOMapper.insertSelective(user);
        return user.getId();
    }
}

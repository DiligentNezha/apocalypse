package com.apocalypse.user.convert;

import com.apocalypse.user.dto.UserRegisterDTO;
import com.apocalypse.user.model.UserDO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserDO convert(UserRegisterDTO userRegisterDTO);
}

package com.apocalypse.uac.convert;

import com.apocalypse.uac.dto.SecurityUserDTO;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.model.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserDO convert(UserRegisterDTO userRegisterDTO);


    @Mappings({
            @Mapping(source = "accountLocked", target = "accountNonLocked", qualifiedByName = "accountNonLockedProcess"),
            @Mapping(source = "enabled", target = "enabled", qualifiedByName = "enabledProcess")
    })
    SecurityUserDTO convert(UserDO userDO);

    @Named("accountNonLockedProcess")
    default boolean accountNonLockedProcess(Integer accountLocked) {
        return accountLocked != 1;
    }

    @Named("enabledProcess")
    default boolean enabledProcess(Integer enabled) {
        return enabled == 1;
    }
}

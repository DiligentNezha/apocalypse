package com.apocalypse.system.convert;

import com.apocalypse.system.dto.RoleCreateDTO;
import com.apocalypse.system.model.RoleDO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/12
 */
@Mapper(componentModel = "spring")
public interface RoleConvert {

    RoleDO convert(RoleCreateDTO roleCreateDTO);
}

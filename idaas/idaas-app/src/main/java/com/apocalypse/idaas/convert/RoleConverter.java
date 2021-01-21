package com.apocalypse.idaas.convert;

import com.apocalypse.idaas.dto.RoleCreateDTO;
import com.apocalypse.idaas.model.RoleDO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/12
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleDO convert(RoleCreateDTO roleCreateDTO);
}

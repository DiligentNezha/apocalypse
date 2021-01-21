package com.apocalypse.idaas.convert;

import com.apocalypse.idaas.model.AdminDO;
import com.apocalypse.idaas.vo.LoginVO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/14
 */
@Mapper(componentModel = "spring")
public interface AdminConverter {

    LoginVO convert(AdminDO adminDO);
}

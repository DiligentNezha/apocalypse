package com.apocalypse.system.convert;

import com.apocalypse.system.model.AdminDO;
import com.apocalypse.system.vo.LoginVO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/14
 */
@Mapper(componentModel = "spring")
public interface AdminConvert {

    LoginVO convert(AdminDO adminDO);
}

package com.apocalypse.example.mappings;

import com.apocalypse.example.dto.LoginInfoDTO;
import com.apocalypse.example.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author jingkaihui
 * @date 2019/2/11
 */
@Mapper
public interface UserModelMapping {

    UserModelMapping INSTANCE = Mappers.getMapper(UserModelMapping.class);

    UserModel loginInfoDTO2UserModel(LoginInfoDTO loginInfoDTO);

    LoginInfoDTO userModel2LoginInfoDTO(UserModel userModel);
}

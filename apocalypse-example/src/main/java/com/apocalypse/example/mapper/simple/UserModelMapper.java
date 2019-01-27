package com.apocalypse.example.mapper.simple;

import com.apocalypse.common.aspect.ThrowAbleAnnotation;
import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.UserModel;
import org.apache.ibatis.annotations.Select;

public interface UserModelMapper extends MyMapper<UserModel> {

    @ThrowAbleAnnotation
    @Select("select * from t_example_user where id = #{1} aaa")
    UserModel getUser(String userId);
}
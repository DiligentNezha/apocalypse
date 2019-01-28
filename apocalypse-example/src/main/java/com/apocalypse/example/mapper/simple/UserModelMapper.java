package com.apocalypse.example.mapper.simple;

import com.apocalypse.common.exception.DaoException;
import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.UserModel;
import org.apache.ibatis.annotations.Select;

public interface UserModelMapper extends MyMapper<UserModel> {

    @Select("select * from t_example_user where id = #{1}")
    UserModel getUser(String userId) throws DaoException;
}
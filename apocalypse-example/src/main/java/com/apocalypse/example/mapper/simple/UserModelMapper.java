package com.apocalypse.example.mapper.simple;

import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.model.UserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserModelMapper extends MyMapper<UserModel> {
    long countByExample(UserModelExample example);

    int deleteByExample(UserModelExample example);

    List<UserModel> selectByExample(UserModelExample example);

    int updateByExampleSelective(@Param("record") UserModel record, @Param("example") UserModelExample example);

    int updateByExample(@Param("record") UserModel record, @Param("example") UserModelExample example);
}
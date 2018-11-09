package com.apocalypse.example.mapper.simple;

import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.model.UserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserModelMapper extends MyMapper<UserModel> {

}
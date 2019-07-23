package com.apocalypse.example.mapper.single;

import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.ShardingTableDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShardingTableDOMapper extends MyMapper<ShardingTableDO, Long> {

}

package com.apocalypse.example.mapper.single;

import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.example.model.ShardingTableDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShardingTableDOMapper extends MyMapper<ShardingTableDO, Long> {

    @Insert("INSERT INTO sharding_table ( id, remark ) VALUES( #{id}, #{remark})")
    void insertDB(ShardingTableDO shardingTableDO);

    @Select("select id, remark, deleted, create_time as createTime, update_time as updateTime from sharding_table " +
            "where id = #{id}")
    ShardingTableDO queryById(Long id);

    @Select("select id, remark, deleted, create_time as createTime, update_time as updateTime from sharding_table " +
            "where id between #{begin} and #{end}")
    List<ShardingTableDO> queryBetween(@Param("begin") Long begin, @Param("end") Long end);

    List<ShardingTableDO> queryIn(@Param("ids") List<Long> ids);
}

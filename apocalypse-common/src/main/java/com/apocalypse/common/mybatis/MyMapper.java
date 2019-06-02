package com.apocalypse.common.mybatis;

import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.update.differ.UpdateByDifferMapper;
import tk.mybatis.mapper.additional.update.force.UpdateByPrimaryKeySelectiveForceMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface MyMapper<T, PK> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>, AggregationMapper<T>,
        IdListMapper<T, PK>, InsertListMapper<T>, UpdateByDifferMapper<T>,
        UpdateByPrimaryKeySelectiveForceMapper<T>, CustomMapper<T> {

}

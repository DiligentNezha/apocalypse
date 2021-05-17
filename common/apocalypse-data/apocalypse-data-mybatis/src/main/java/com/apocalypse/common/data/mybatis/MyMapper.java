package com.apocalypse.common.data.mybatis;

import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.update.differ.UpdateByDifferMapper;
import tk.mybatis.mapper.additional.update.force.UpdateByPrimaryKeySelectiveForceMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface MyMapper<T, PK> extends Mapper<T>, IdsMapper<T>, AggregationMapper<T>,
        IdListMapper<T, PK>, InsertListMapper<T>, UpdateByDifferMapper<T>, UpdateByPrimaryKeySelectiveForceMapper<T>,
        SelectByPropertyMapper<T>, DeleteByPropertyMapper<T> {

}

package com.apocalypse.cms.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/19
 */
public interface ComplexMapper {

    @Select("select * from table_name")
    Map<String, Object> complexSelect();
}

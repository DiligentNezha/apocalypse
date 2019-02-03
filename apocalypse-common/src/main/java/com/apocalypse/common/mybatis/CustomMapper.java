package com.apocalypse.common.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @param <T> 不能为空
 * @author jingkaihui
 * @date 2019/2/2
 */
public interface CustomMapper<T> {

    /**
     * 根据属性及对应值进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param property 查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = CustomSelectProvider.class, method = "dynamicSQL")
    T selectOneByProperty(@Param("property") String property, @Param("value") Object value);
}

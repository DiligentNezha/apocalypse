package com.apocalypse.common.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * @param <T> 不能为空
 * @author jingkaihui
 * @date 2019/2/2
 */
@RegisterMapper
public interface CustomMapper<T> {

    /**
     * 根据属性及对应值进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param property 查询属性
     * @param value    属性值
     * @return
     */
    @SelectProvider(type = CustomSelectProvider.class, method = "dynamicSQL")
    T selectOneByProperty(@Param("property") String property, @Param("value") Object value);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param property 查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = CustomSelectProvider.class, method = "dynamicSQL")
    List<T> selectByProperty(@Param("property") String property, @Param("value") Object value);

    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param property 查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = CustomSelectProvider.class, method = "dynamicSQL")
    boolean existsWithProperty(@Param("property") String property, @Param("value") Object value);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param property 查询属性
     * @param value    属性值
     * @return
     */
    @SelectProvider(type = CustomSelectProvider.class, method = "dynamicSQL")
    int selectCountByProperty(@Param("property") String property, @Param("value") Object value);
}

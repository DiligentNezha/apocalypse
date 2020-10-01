package com.apocalypse.common.data.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.weekend.Fn;

/**
 * @param <T> 不能为空
 * @author jingkaihui
 * @date 2020/3/30
 */
@RegisterMapper
public interface DeleteByPropertyMapper<T> {

    /**
     * 根据实体中的属性删除，条件使用等号
     *
     * @param fn 属性
     * @param value    属性值
     * @return
     */
    @DeleteProvider(type = com.apocalypse.common.data.mybatis.DeletePropertyProvider.class, method = "dynamicSQL")
    int deleteByProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

    /**
     * 根据实体中的属性删除，条件使用 in
     *
     * @param fn 属性
     * @param value    属性值
     * @return
     */
    @DeleteProvider(type = com.apocalypse.common.data.mybatis.DeletePropertyProvider.class, method = "dynamicSQL")
    int deleteInByProperty(@Param("fn") Fn<T, ?> fn, @Param("values") Object value);

}

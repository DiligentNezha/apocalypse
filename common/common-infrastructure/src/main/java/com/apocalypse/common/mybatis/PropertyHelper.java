package com.apocalypse.common.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description 通用mapper方法扩展相关工具方法
 * @date 2019/4/8
 */
public class PropertyHelper {

    /**
     * 根据实体Class和属性名获取对应的表字段名
     * @param entityClass 实体Class对象
     * @param property 属性名
     * @return
     */
    public static String getColumnByProperty(Class<?> entityClass, String property) {
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        EntityColumn entityColumn = entityTable.getPropertyMap().get(property);
        return entityColumn.getColumn();
    }

    public static boolean isEmpty(Object value, boolean notEmpty) {
        boolean empty = false;
        if (ObjectUtil.isNull(value)) {
            empty = true;
        } else {
            if (value instanceof LocalDateTime || value instanceof LocalDate || value instanceof LocalTime) {
                empty = false;
            }
            if (notEmpty) {
                empty = StrUtil.isEmptyIfStr(value);
            }
        }
        return empty;
    }
}

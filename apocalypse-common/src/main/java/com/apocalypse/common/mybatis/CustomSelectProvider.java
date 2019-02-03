package com.apocalypse.common.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author jingkaihui
 * @date 2019/2/2
 */
public class CustomSelectProvider extends MapperTemplate {

    public CustomSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据属性查询
     *
     * @param ms
     * @return
     */
    public String selectOneByProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnSet) {
            sql.append("<if test=\"property == '" + column.getProperty() + "'\">\n");
            sql.append(column.getColumn() + " = #{value,javaType=" + column.getJavaType().getSimpleName() + "}\n");
            sql.append("</if>");
        }
        sql.append("</where>");
        return sql.toString();
    }

}

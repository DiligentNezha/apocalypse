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

    /**Ba
     * 根据属性查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
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
        sql.append("<where>\n");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnSet) {
            sql.append("<if test=\"");
            sql.append("value != null");
            if (isNotEmpty() && column.getJavaType().equals(String.class)) {
                sql.append(" and ");
                sql.append(" value != '' ");
            }
            sql.append(" and property == '").append(column.getProperty());
            sql.append("'\">\n");
            sql.append(column.getColumn() + " = #{value,javaType=" + column.getJavaType().getSimpleName() + "}\n");
            sql.append("</if>\n");
        }
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 根据属性查询，查询条件使用等号
     *
     * @param ms
     * @return
     */
    public String selectByProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnSet) {
            sql.append("<if test=\"");
            sql.append("value != null");
            if (isNotEmpty() && column.getJavaType().equals(String.class)) {
                sql.append(" and ");
                sql.append(" value != '' ");
            }
            sql.append(" and property == '").append(column.getProperty());
            sql.append("'\">\n");
            sql.append(column.getColumn() + " = #{value,javaType=" + column.getJavaType().getSimpleName() + "}\n");
            sql.append("</if>\n");
        }
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 根据属性查询总数，查询条件使用等号
     *
     * @param ms
     * @return
     */
    public String existsWithProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCountExists(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnSet) {
            sql.append("<if test=\"");
            sql.append("value != null");
            if (isNotEmpty() && column.getJavaType().equals(String.class)) {
                sql.append(" and ");
                sql.append(" value != '' ");
            }
            sql.append(" and property == '").append(column.getProperty());
            sql.append("'\">\n");
            sql.append(column.getColumn() + " = #{value,javaType=" + column.getJavaType().getSimpleName() + "}\n");
            sql.append("</if>\n");
        }
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 根据属性查询总数，查询条件使用等号
     *
     * @param ms
     * @return
     */
    public String selectCountByProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnSet) {
            sql.append("<if test=\"");
            sql.append("value != null");
            if (isNotEmpty() && column.getJavaType().equals(String.class)) {
                sql.append(" and ");
                sql.append(" value != '' ");
            }
            sql.append(" and property == '").append(column.getProperty());
            sql.append("'\">\n");
            sql.append(column.getColumn() + " = #{value,javaType=" + column.getJavaType().getSimpleName() + "}\n");
            sql.append("</if>\n");
        }
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }
}

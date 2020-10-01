package com.apocalypse.common.data.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.MetaObjectUtil;

/**
 * @author jingkaihui
 * @date 2020/3/30
 */
public class DeletePropertyProvider extends MapperTemplate {

    public DeletePropertyProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据属性删除，条件使用等号
     *
     * @param ms
     * @return
     */
    public String deleteByProperty(MappedStatement ms) {
        String propertyHelper = PropertyHelper.class.getName();
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        // 如果是逻辑删除，则修改为更新表，修改逻辑删除字段的值
        if (SqlHelper.hasLogicDeleteColumn(entityClass)) {
            sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
            sql.append("<set>");
            sql.append(SqlHelper.logicDeleteColumnEqualsValue(entityClass, true));
            sql.append("</set>");
            MetaObjectUtil.forObject(ms).setValue("sqlCommandType", SqlCommandType.UPDATE);
        } else {
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        }
        sql.append("<where>\n");
        sql.append("<if test=\"false==");
        sql.append("@");
        sql.append(propertyHelper);
        sql.append("@isNull(value, ");
        sql.append(getConfig().isSafeDelete());
        sql.append(")");
        sql.append("\">\n");
        String entityClassName = entityClass.getName();
        //通过实体类名获取运行时属性对应的字段
        String ognl = new StringBuilder("${@")
                .append(propertyHelper)
                .append("@getColumnByProperty(@java.lang.Class@forName(\"")
                .append(entityClassName)
                .append("\"), @tk.mybatis.mapper.weekend.reflection.Reflections@fnToFieldName(fn))}").toString();
        sql.append(ognl + " = #{value}\n");
        sql.append("</if>\n");
        sql.append("</where>");
        return sql.toString();
    }

     /**
     * 根据属性删除，条件使用等号
     *
     * @param ms
     * @return
     */
    public String deleteInByProperty(MappedStatement ms) {
        String propertyHelper = PropertyHelper.class.getName();
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        // 如果是逻辑删除，则修改为更新表，修改逻辑删除字段的值
        if (SqlHelper.hasLogicDeleteColumn(entityClass)) {
            sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
            sql.append("<set>");
            sql.append(SqlHelper.logicDeleteColumnEqualsValue(entityClass, true));
            sql.append("</set>");
            MetaObjectUtil.forObject(ms).setValue("sqlCommandType", SqlCommandType.UPDATE);
        } else {
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        }
        sql.append("<where>\n");
        String entityClassName = entityClass.getName();
        String sqlSegment =
                "${@" + propertyHelper + "@getColumnByProperty(@java.lang.Class@forName(\"" + entityClassName + "\"),"
                        +   "@tk.mybatis.mapper.weekend.reflection.Reflections@fnToFieldName(fn))} in"
                        +   "<foreach open=\"(\" close=\")\" separator=\",\" collection=\"values\" item=\"obj\">\n"
                        +      "#{obj}\n"
                        +   "</foreach>\n";
        sql.append(sqlSegment);
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }
}

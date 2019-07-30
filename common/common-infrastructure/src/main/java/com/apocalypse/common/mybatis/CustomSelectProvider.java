package com.apocalypse.common.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

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
        String propertyHelper = PropertyHelper.class.getName();
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        sql.append("<if test=\"false==");
        sql.append("@");
        sql.append(propertyHelper);
        sql.append("@isEmpty(value, ");
        sql.append(isNotEmpty());
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
        String propertyHelper = PropertyHelper.class.getName();
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        sql.append("<if test=\"false==");
        sql.append("@");
        sql.append(propertyHelper);
        sql.append("@isEmpty(value, ");
        sql.append(isNotEmpty());
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
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 根据属性查询，查询条件使用 in
     *
     * @param ms
     * @return
     */
    public String selectInByProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        String entityClassName = entityClass.getName();
        String propertyHelper = PropertyHelper.class.getName();
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

    /**
     * 根据属性查询，查询条件使用 between
     *
     * @param ms
     * @return
     */
    public String selectBetweenByProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        String entityClassName = entityClass.getName();
        String propertyHelper = PropertyHelper.class.getName();
        String sqlSegment =
                "${@" + propertyHelper + "@getColumnByProperty(@java.lang.Class@forName(\"" + entityClassName + "\"),"
                        + "@tk.mybatis.mapper.weekend.reflection.Reflections@fnToFieldName(fn))} "
                        + "between #{begin} and #{end}";
        sql.append(sqlSegment);
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
        String propertyHelper = PropertyHelper.class.getName();
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCountExists(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        sql.append("<if test=\"false==");
        sql.append("@");
        sql.append(propertyHelper);
        sql.append("@isEmpty(value, ");
        sql.append(isNotEmpty());
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
        String propertyHelper = PropertyHelper.class.getName();

        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append("<where>\n");
        sql.append("<if test=\"false==");
        sql.append("@");
        sql.append(propertyHelper);
        sql.append("@isEmpty(value, ");
        sql.append(isNotEmpty());
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
        // 逻辑删除的未删除查询条件
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");
        return sql.toString();
    }
}

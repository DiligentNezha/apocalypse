package com.apocalypse.common.service;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.aggregation.AggregateCondition;
import tk.mybatis.mapper.weekend.Fn;

import java.util.List;

public interface BaseService<T, PK> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record 实体
     * @return 1为成功， 0为失败
     */
    int insert(T record);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record 实体
     * @return 1为成功， 0为失败
     */
    int insertSelective(T record);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    boolean deleteByPrimaryKey(Object key);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    T selectOne(T record);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    List<T> select(T record);

    /**
     * 查询全部结果
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    int selectCount(T record);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    boolean existsWithPrimaryKey(Object key);

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    List<T> selectByIds(String ids);

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    int deleteByIds(String ids);

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    int selectCountByExample(Object example);

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    int deleteByExample(Object example);

    /**
     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(T record, Object example);

    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(T record, Object example);

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
    int insertList(List<T> recordList);

    /**
     * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
     *
     * @param record
     * @return
     */
    int insertUseGeneratedKeys(T record);

    /**
     * 根据属性及对应值进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param fn 查询属性
     * @param value    属性值
     * @return
     */
    T selectOneByProperty(Fn<T, ?> fn, Object value);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param fn 查询属性
     * @param value 属性值
     * @return
     */
    List<T> selectByProperty(Fn<T, ?> fn, Object value);

    /**
     * 根据实体中的属性值进行查询，查询条件使用in
     *
     * @param fn 查询属性
     * @param values 属性值集合
     * @return
     */
    List<T> selectInByProperty(Fn<T, ?> fn, List<?> values);

    /**
     * 根据实体中的属性值进行查询，查询条件使用 between
     *
     * @param fn 查询属性
     * @return
     */
    List<T> selectBetweenByProperty(Fn<T, ?> fn, Object begin, Object end);

    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param fn 查询属性
     * @param value 属性值
     * @return
     */
    boolean existsWithProperty(Fn<T, ?> fn, Object value);


    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param fn 查询属性
     * @param value 属性值
     * @return
     */
    int selectCountByProperty(Fn<T, ?> fn, Object value);


    /**
     * 根据example和aggregateCondition进行聚合查询
     * 分组不支持having条件过滤， 如需要建议使用xml文件
     *
     * @param example
     * @param aggregateCondition 可以设置聚合查询的属性和分组属性
     * @return 返回聚合查询属性和分组属性的值
     */
    List<T> selectAggregationByExample(Object example, AggregateCondition aggregateCondition);

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param idList
     * @return
     */
    List<T> selectByIdList(List<PK> idList);

    /**
     * 根据Example条件进行查询一条结果
     *
     * @param example
     * @return
     */
    T selectOneByExample(Object example);
}

package com.apocalypse.common.service.impl;

import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.additional.aggregation.AggregateCondition;
import tk.mybatis.mapper.weekend.Fn;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<T, PK> implements BaseService<T, PK> {

    private Class<T> clazz;

    @Autowired
    private MyMapper<T, PK> myMapper;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        //得到泛型化的超类
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public int insert(T record) {
        return myMapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return myMapper.insertSelective(record);
    }

    @Override
    public int delete(T record) {
        return myMapper.delete(record);
    }

    @Override
    public boolean deleteByPrimaryKey(Object key) {
        return myMapper.deleteByPrimaryKey(key) == 1;
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return myMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return myMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public T selectOne(T record) {
        return myMapper.selectOne(record);
    }

    @Override
    public List<T> select(T record) {
        return myMapper.select(record);
    }

    @Override
    public List<T> selectAll() {
        return myMapper.selectAll();
    }

    @Override
    public int selectCount(T record) {
        return myMapper.selectCount(record);
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return myMapper.selectByPrimaryKey(key);
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return myMapper.existsWithPrimaryKey(key);
    }

    @Override
    public List<T> selectByIds(String ids) {
        return myMapper.selectByIds(ids);
    }

    @Override
    public int deleteByIds(String ids) {
        return myMapper.deleteByIds(ids);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return myMapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Object example) {
        return myMapper.selectCountByExample(example);
    }

    @Override
    public int deleteByExample(Object example) {
        return myMapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return myMapper.updateByExample(record, example);
    }

    @Override
    public int updateByExampleSelective(T record, Object example) {
        return myMapper.updateByExampleSelective(record, example);
    }


    @Override
    public int insertList(List<T> recordList) {
        return myMapper.insertList(recordList);
    }

    @Override
    public int insertUseGeneratedKeys(T record) {
        return myMapper.insertUseGeneratedKeys(record);
    }

    @Override
    public T selectOneByProperty(Fn<T, ?> fn, Object value) {
        return myMapper.selectOneByProperty(fn, value);
    }

    @Override
    public List<T> selectByProperty(Fn<T, ?> fn, Object value) {
        return myMapper.selectByProperty(fn, value);
    }

    @Override
    public List<T> selectInByProperty(Fn<T, ?> fn, List<?> values) {
        return myMapper.selectInByProperty(fn, values);
    }

    @Override
    public List<T> selectBetweenByProperty(Fn<T, ?> fn, Object begin, Object end) {
        return myMapper.selectBetweenByProperty(fn, begin, end);
    }

    @Override
    public boolean existsWithProperty(Fn<T, ?> fn, Object value) {
        return myMapper.existsWithProperty(fn, value);
    }

    @Override
    public int selectCountByProperty(Fn<T, ?> fn, Object value) {
        return myMapper.selectCountByProperty(fn, value);
    }

    @Override
    public List<T> selectAggregationByExample(Object example, AggregateCondition aggregateCondition) {
        return myMapper.selectAggregationByExample(example, aggregateCondition);
    }

    @Override
    public List<T> selectByIdList(List<PK> idList) {
        return myMapper.selectByIdList(idList);
    }

    @Override
    public T selectOneByExample(Object example) {
        return myMapper.selectOneByExample(example);
    }

}

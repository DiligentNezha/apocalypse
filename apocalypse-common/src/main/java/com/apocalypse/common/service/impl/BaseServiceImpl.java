package com.apocalypse.common.service.impl;


import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private MyMapper<T> myMapper;

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
    public int deleteByPrimaryKey(Object key) {
        return myMapper.deleteByPrimaryKey(key);
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
}

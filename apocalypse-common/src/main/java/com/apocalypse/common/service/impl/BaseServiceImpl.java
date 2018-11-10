package com.apocalypse.common.service.impl;


import com.apocalypse.common.exception.EmptyingDataException;
import com.apocalypse.common.mybatis.MyMapper;
import com.apocalypse.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    private Class<T> clazz;

    @Autowired
    private MyMapper<T> myMapper;

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
    public int delete(T record, boolean... ignoreRisk) throws EmptyingDataException {
        //record不为null，不存在删除整张表的风险
        if (!isNullModel(record)) {
            return myMapper.delete(record);
        } else {
            //record为null，如果ignoreRisk为true,调用方明确忽略删除整张表的风险
            if (ignoreRisk != null && ignoreRisk.length > 0 && ignoreRisk[0]) {
                return myMapper.delete(record);
            } else {
                //调用方的操作可能因为record为null，或者ignoreRisk不合法（ignoreRisk不为null但是长度为0或者ignoreRisk长度大于1，但是
                //第一个元素为false，即调用明确不忽略删除整张表的风险，但是record为null会导致删除整张表），不执行操作并抛出异常告知调用方原因
                throw new EmptyingDataException();
            }
        }
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

    /**
     * 根据record判断record是否为null以及record中所有成员属性是否为null
     * @param record T类型的对象实例
     * @return 当record为null或者record中所有成员属性为null返回true，否则返回false
     */
    private boolean isNullModel(T record) {
        boolean nullModel = true;
        if (record != null) {
            //获取全部列
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field: declaredFields) {
                try {
                    String name = field.getName();
                    Method declaredMethod =
                            clazz.getDeclaredMethod("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
                    Object invoke = declaredMethod.invoke(record);
                    //判断是否有值不为null的成员属性
                    if (invoke != null) {
                        nullModel = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return nullModel;
    }
}

package com.apocalypse.system.service.complex.impl;

import com.apocalypse.system.service.complex.TransactionalExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Service
public class TransactionalExampleServiceImpl implements TransactionalExampleService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionalUseAnnotation() {
        int i = 10 / 0;
    }

    @Override
    public void transactionalUseProgramming() {
    }
}

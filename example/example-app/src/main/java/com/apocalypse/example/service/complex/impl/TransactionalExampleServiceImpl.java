package com.apocalypse.example.service.complex.impl;

import com.apocalypse.example.service.complex.TransactionalExampleService;
import com.apocalypse.example.service.single.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Service
public class TransactionalExampleServiceImpl implements TransactionalExampleService {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionalUseAnnotation() {
        exampleService.deleteByPrimaryKey(1134296278184890368L);
        int i = 10 / 0;
    }

    @Override
    public void transactionalUseProgramming() {
        //定义事务属性
        DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        //设置事务传播行为属性
        dtd.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        //获得事务状态
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(dtd);
        try {
            exampleService.deleteByPrimaryKey(1134296278184890368L);
            //提交事务
            int i = 10 / 0;
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }
}

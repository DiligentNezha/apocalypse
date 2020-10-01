package com.apocalypse.idaas.service.complex;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
public interface TransactionalExampleService {

    /**
     * 使用注解进行声明式事务
     */
    void transactionalUseAnnotation();

    /**
     * 使用 org.springframework.transaction.interceptor.TransactionAspectSupport#currentTransactionStatus().setRollbackOnly() 编程式事务
     */
    void transactionalUseProgramming();
}

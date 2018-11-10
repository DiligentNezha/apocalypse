package com.apocalypse.common.exception;

/**
 * @author 景凯辉
 * @date 2018/11/10
 * @mail jingkaihui@adpanshi.com
 */
public class EmptyingDataException extends Exception {

    public EmptyingDataException() {
        super("您的删除条件会删除整张表，本次操作并没有执行，引起此异常的原因是您的查询对象为null，且您没有明确指定忽略此风险，" +
                "如果您确定要删除整张表，请您调用该方法时明确指定忽略此风险ignoreRisk为true");
    }
}

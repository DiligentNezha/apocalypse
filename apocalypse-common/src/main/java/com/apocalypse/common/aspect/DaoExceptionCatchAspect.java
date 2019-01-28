package com.apocalypse.common.aspect;

import com.apocalypse.common.exception.DaoException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoExceptionCatchAspect {

    @Pointcut("execution(* com.apocalypse.*.mapper.*.*.*(..)) || @annotation(com.apocalypse.common.aspect" +
            ".ThrowAbleAnnotation)")
    public void pointCut(){}


    @AfterThrowing(throwing = "e", value = "pointCut()")
    public void daoExceptionCatch(Throwable e) throws DaoException {
        throw new DaoException("100001", "数据库异常", e);
    }
}

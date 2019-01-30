package com.apocalypse.common.aspect;

import com.apocalypse.common.exception.DaoException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoExceptionCatchAspect {

    @Pointcut("execution(* com.apocalypse.*.mapper.*.*.*(..)) || @annotation(com.apocalypse.common.aspect" +
            ".ThrowAbleAnnotation)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Object resultValue;
        try {
            resultValue = pjp.proceed();
        } catch (Exception e) {
            Logger logger = getLogger(pjp);
            logger.error("数据库异常", e);
            throw new DaoException("11008", "数据库异常", e);
        }
        return resultValue;
    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }
}

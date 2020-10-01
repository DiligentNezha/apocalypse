package com.apocalypse.common.boot.aspect;

import com.apocalypse.common.data.mybatis.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 用于打印方法执行前参数及执行后的结果
 */
@Aspect
@Component
@Slf4j
public class InvokeRecordAspect {

    /**
     *
     * 申明一个切点 里面是 execution表达式
     */
    @Pointcut("execution(public * com.apocalypse..controller..*(..))")
    private void controllerAspect() {
    }

    @ResponseBody
    @Around("controllerAspect()")
    public Object doRecord(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Object result;
        log.info("<------请求内容------");
        // 打印请求内容
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求方式:" + request.getMethod());
        log.info("请求类方法:" + joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        Object[] objects = Arrays.stream(args).filter(x -> (
                !(x instanceof HttpServletResponse) &&
                        !(x instanceof HttpServletRequest) &&
                        !(x instanceof MultipartFile)
        )).toArray();
        log.info("请求类方法参数:" + JsonUtil.toJsonStr(objects));
        log.info("<------请求内容------");

        result = joinPoint.proceed(args);

        log.info("------返回内容------>");
        log.info("Response内容:" + JsonUtil.toJsonStr(result));
        log.info("------返回内容------>");
        return result;
    }

}

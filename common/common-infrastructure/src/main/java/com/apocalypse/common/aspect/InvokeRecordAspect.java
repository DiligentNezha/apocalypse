package com.apocalypse.common.aspect;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.apocalypse.common.dto.Rest;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Pointcut("execution(public * com.apocalypse.*.controller..*.*(..))")
    private void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object doRecord(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Object result;
        String uuid = IdUtil.fastSimpleUUID();
        log.info("<------请求内容------");
        // 打印请求内容
        log.info("请求编号【{}】请求地址【{}】", uuid, request.getRequestURL().toString());
        log.info("请求编号【{}】请求方式【{}】", uuid, request.getMethod());
        log.info("请求编号【{}】请求类方法【{}】", uuid, joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        Stream<Object> stream = Arrays.stream(args);
        List<Object> logArgs = stream
                .filter(arg -> (
                        !(arg instanceof HttpServletRequest)
                        && !(arg instanceof HttpServletResponse))
                        && !(arg instanceof MultipartFile)
                        && !(arg instanceof  MultipartFile[]))
                .collect(Collectors.toList());

        log.info("请求编号【{}】请求类方法参数【{}】", uuid, JSONObject.toJSONString(logArgs, new SimplePropertyPreFilter() {
            @Override
            public boolean apply(JSONSerializer serializer, Object object, String name) {
                return !StrUtil.containsAnyIgnoreCase(name, "rights");
            }
        }));
        log.info("<------请求内容------");

        result = joinPoint.proceed(args);

        log.info("------返回内容------>");
        log.info("请求编号【{}】Response内容【{}】", uuid, result instanceof Rest ? JSONObject.toJSONString(result) : result.toString());
        log.info("------返回内容------>");
        return result;
    }

}

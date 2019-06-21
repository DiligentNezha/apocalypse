package com.apocalypse.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description RequestContext上下文工具类
 * @date 2019/5/6
 */
public class HttpContextUtil {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前请求的URL
     * @return
     */
    public static String getUrl() {
        return getHttpServletRequest().getRequestURL().toString();
    }

    /**
     * 获取当前请求的域名
     * @return
     */
    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * 获取当前请求的来源
     * @return
     */
    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }
}

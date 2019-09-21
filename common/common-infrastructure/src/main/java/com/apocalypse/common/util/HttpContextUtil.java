package com.apocalypse.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description RequestContext上下文工具类
 * @date 2019/5/6
 */
public class HttpContextUtil {

    public static String getClientIP() {
        return ServletUtil.getClientIP(getHttpServletRequest());
    }

    public static String getUserAgent() {
        return ServletUtil.getHeaderIgnoreCase(getHttpServletRequest(), "User-Agent");
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    public static Enumeration<String> getHeaders(String name) {
        return getHttpServletRequest().getHeaders(name);
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


    public static HttpServletResponse getHttpServletResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static void write(HttpServletResponse response, Object obj) {
        PrintWriter writer = null;
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            writer = response.getWriter();
            writer.write(JSON.toJSONString(obj));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}

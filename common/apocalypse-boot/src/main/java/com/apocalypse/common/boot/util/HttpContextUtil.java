package com.apocalypse.common.boot.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.apocalypse.common.data.mybatis.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description RequestContext上下文工具类
 * @date 2020/3/30
 */
@Slf4j
public class HttpContextUtil {

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.defaultObjectMapper();

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
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            writer = response.getWriter();
            writer.write(OBJECT_MAPPER.writeValueAsString(obj));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } finally {
            writer.close();
        }
    }
}

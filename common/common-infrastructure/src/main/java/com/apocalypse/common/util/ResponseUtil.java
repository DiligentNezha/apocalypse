package com.apocalypse.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@Slf4j
public class ResponseUtil {

    /**
     * 快速设置允许跨域的头信息
     * @param response
     */
    public static void allCors(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(Charset.forName("UTF-8").name());
        response.setContentType("application/json;charset=UTF-8");
        // 使用Access-Control-Allow-Credentials 时， Access-Controller-Allow-Origin 不应该设置为 *
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
        response.setHeader("Access-Control-Max-Age", "1800");
        response.setHeader("Vary", "Origin, Access-Control-Request-Method, Access-Control-Request-Headers");
    }
}

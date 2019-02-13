package com.apocalypse.front.interception;

import com.apocalypse.front.http.CustomBodyRequestWrapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jingkaihui
 * @date 2019/2/13
 */
public class MyInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof CustomBodyRequestWrapper) {
//            byte[] contentAsByteArray = ((CustomBodyRequestWrapper) request).getContentAsByteArray();
//            String sb = new String(contentAsByteArray, "UTF-8");
            System.out.println(((CustomBodyRequestWrapper) request).getBodyString());
        }
        return true;
    }

}

package com.apocalypse.front.filter;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.front.http.CustomBodyRequestWrapper;
import com.apocalypse.front.http.CustomBodyResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jingkaihui
 * @date 2019/2/13
 */
@Component
public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomBodyResponseWrapper customBodyResponseWrapper = new CustomBodyResponseWrapper(response);
        filterChain.doFilter(new CustomBodyRequestWrapper(request), customBodyResponseWrapper);

        String contentType = customBodyResponseWrapper.getContentType();
        byte[] contentAsByteArray = customBodyResponseWrapper.getContentAsByteArray();
        String originalBody = new String(contentAsByteArray, StandardCharsets.UTF_8);
        if (contentType != null && !contentType.contains("problem") && !originalBody.startsWith("[")) {

            JSONObject jsonObject = JSONObject.parseObject(originalBody).fluentPut("mykey", "myValue");
            originalBody = jsonObject.toJSONString();
            System.out.println(originalBody);
        }

        if (!customBodyResponseWrapper.isCommitted()) {
            customBodyResponseWrapper.getContent().reset();
        }
        customBodyResponseWrapper.content.write(originalBody.getBytes("UTF-8"));

        customBodyResponseWrapper.copyBodyToResponse();


    }


}

package com.apocalypse.front.filter;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.front.http.CustomBodyRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jingkaihui
 * @date 2019/2/13
 */
@Component
public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(new CustomBodyRequestWrapper(request), contentCachingResponseWrapper);

        String contentType = contentCachingResponseWrapper.getContentType();
        byte[] contentAsByteArray = contentCachingResponseWrapper.getContentAsByteArray();
        String originalBody = new String(contentAsByteArray, "UTF-8");
        if (contentType != null && !contentType.contains("text") && !contentType.contains("javascript") && !contentType.contains("octet-stream") && !contentType.contains("image") && !originalBody.startsWith("[")) {

            JSONObject jsonObject = JSONObject.parseObject(originalBody).fluentPut("mykey", "myValue");
            originalBody = jsonObject.toJSONString();
            System.out.println(originalBody);
        }

        ContentCachingResponseWrapper.ResponsePrintWriter writer =
                ((ContentCachingResponseWrapper.ResponsePrintWriter) contentCachingResponseWrapper.getWriter());
//        ServletOutputStream outputStream = contentCachingResponseWrapper.getOutputStream();
//        outputStream.flush();
//        outputStream.write(originalBody.getBytes("UTF-8"));
//        contentCachingResponseWrapper.copyBodyToResponse();
        contentCachingResponseWrapper.content.reset();
        contentCachingResponseWrapper.content.write(originalBody.getBytes("UTF-8"));

//        writer.write(originalBody);
//        writer.flush();
//        writer.close();
        contentCachingResponseWrapper.copyBodyToResponse();
//        contentCachingResponseWrapper.getWriter().print(jsonObject.toJSONString());


    }


}

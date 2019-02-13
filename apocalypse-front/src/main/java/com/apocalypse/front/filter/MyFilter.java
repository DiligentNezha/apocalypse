package com.apocalypse.front.filter;

import com.apocalypse.front.http.CustomBodyRequestWrapper;
import com.apocalypse.front.http.CustomBodyResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jingkaihui
 * @date 2019/2/13
 */
@Component
public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new CustomBodyRequestWrapper(request), new CustomBodyResponseWrapper(response));
    }



}

package com.jackssy.common.config;

import com.jackssy.common.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(filterName = "validFilter", urlPatterns = "/admin/product/**")
//@Order()
public class ValidFilter implements Filter {



    private final static Logger logger = LoggerFactory.getLogger(ValidFilter.class);

    private String validSgin="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWNrc3N5dXYiLCJqdGkiOiIyNjVmN2VlOC0xNWFjLTQ1YzctODcyOC1hNzBmMjQ3OGZjZjgiLCJpYXQiOjE1NjI4MzgxMzQsImV4cCI6MTU2MzcwMjEzNH0.U93-73saAo_-MEQ23Cj6AbNklZoYmvRULOPxRlvyJN0";

    public ValidFilter(String validSgin) {
        this.validSgin = validSgin;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(" ValidFilter init");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("ValidFilter execute");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getRequestURI().startsWith("/admin/product/")){
//            if(JwtUtil.checkJWT(validSgin)){
//                filterChain.doFilter(servletRequest, servletResponse);
//            }else{
//                return ;
//            }
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            System.out.println("this is MyFilter,url :"+request.getRequestURI());
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }
    @Override
    public void destroy() {
        System.out.println("ValidFilter destroy");
    }
}

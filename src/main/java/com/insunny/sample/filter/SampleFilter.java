 package com.insunny.sample.filter;

 import com.alibaba.fastjson.JSON;
 import com.insunny.sample.config.WebConfig;
 import org.springframework.stereotype.Component;

 import javax.servlet.*;
 import javax.servlet.annotation.WebFilter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.List;

 /**
  * Created by 陈继业 on 2018/9/19 0019.
  * Version 1.0019
  */
 @Component
 @WebFilter(filterName = "smapleFilter",urlPatterns = "/*")
 public class SampleFilter implements Filter {
     private List<String> whiteList = new ArrayList<>();
     boolean isPass = false;

     @Override
     public void init(FilterConfig filterConfig) throws ServletException {
         System.out.println("过滤器初始化");
         whiteList.add(".js");
         whiteList.add(".css");
         whiteList.add(".png");
         whiteList.add(".jpg");
         whiteList.add(".woff");
         whiteList.add("/login");
         whiteList.add("/sku");

     }

     @Override
     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest req = (HttpServletRequest) servletRequest;
         HttpServletResponse res = (HttpServletResponse) servletResponse;
         String uri = req.getRequestURI();
         System.out.println("请求进来 uri:"+uri);
         HttpSession session = req.getSession();
         String isLogin = (String) session.getAttribute("isLogin");
         isLogin = isLogin==null?"0":isLogin;
         //1 静态资源直接放行
         if(!(uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith(".png")||uri.endsWith(".jpg")||uri.contains(".woff")||uri.contains(".map")||uri.contains(".ttf")||uri.endsWith("/login")||uri.startsWith("/sku"))){
             //1.1 非静态资源判断是否session中isLogin是否为1
             if(WebConfig.ALREADY_LOGGED_IN.equals(isLogin)){
                 System.out.println("已登录");
             }else{
                 System.out.println("未登录");
                 System.out.println("重定向");
                 res.sendRedirect("/sample/login");
                 return;
             }
         }

         filterChain.doFilter(servletRequest,servletResponse);
         System.out.println("请求出去");
     }

     @Override
     public void destroy() {

     }
 }

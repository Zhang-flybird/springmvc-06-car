package com.etoak.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.etoak.bean.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截的地址 - {}", request.getRequestURI());
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if (user == null) {
            log.warn("用户未登录");
            // 如果用户为空，那么返回到登录页面
            // 使用response重定向到登录请求
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/user/toLogin");
            return false;
        }
        return true;
    }
    
}

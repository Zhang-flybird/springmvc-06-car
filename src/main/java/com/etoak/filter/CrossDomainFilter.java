package com.etoak.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 允许跨域请求的过滤器
 * 
 * @author 姓名 工号
 * @version [版本号, 2020年3月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
// @Slf4j
// @Component
public class CrossDomainFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置允许哪些源、允许哪些请求方法、允许哪些自定义的请求头、是否允许携带身份凭证(Cookie)、预检请求有效期
        HttpServletResponse resp = (HttpServletResponse)response;
        // 1. 设置哪些源可以访问我们的资源，可以是使用*表示所有源、单个源、多个源(用逗号隔开)
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        // 2. 允许请求方法，必须要允许OPTIONS预检请求
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        // 3. 允许携带请求头字段
        resp.setHeader("Access-Control-Allow-Headers", "Accept, Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires,Content-Type, X-E4M-With,Authorization, refresh-token");
        // 4. 允许携带身份信息(Cookie)
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        // 5. 预检请求有效期，单位是秒。在有效期内同一请求不会再次返送预检请求
        resp.setHeader("Access-Control-Max-Age", "3600"); // 1小时之内
        // 最后
        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("==========CrossDomainFilter init========");
    }
    
    @Override
    public void destroy() {
        
    }
    
}

package com.gaorch.myBlog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaorch.myBlog.utils.JwtUtil;
import com.gaorch.myBlog.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String token = request.getHeader("X-token");
        if(JwtUtil.isAuthrize(token))
        {
            return true;
        }
        else
        {
            // 将 Result 对象序列化为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(Response.unAuthorized());
            // 设置响应的内容类型为 JSON
            response.setContentType("application/json;charset=UTF-8");
            // 将 JSON 字符串写入响应体
            response.getWriter().write(jsonResponse);
            return false;
        }
    }


}

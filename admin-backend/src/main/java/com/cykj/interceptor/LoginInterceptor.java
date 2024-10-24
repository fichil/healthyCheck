package com.cykj.interceptor;

import com.alibaba.fastjson.JSON;
import com.cykj.dto.ResponseDto;
import com.cykj.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置响应类型
        response.setContentType("application/json;charset=UTF-8");
        // 从请求头获取token
        String token = request.getHeader("enter_token");
        // 判断token是否为空
        if (token == null || token.isEmpty()) {
            // token为空，返回401
            ResponseDto dto = new ResponseDto(401, "token不存在，未登陆", null);
            response.getWriter().write(JSON.toJSONString(dto));
            return false;
        }
        //通用token
        if (token=="iamadmin"){
            return true;
        }
        //解析token
        try {
            Claims claims = JwtUtils.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token解析出错，返回401
            System.out.println("错误token"+token);
            ResponseDto dto = new ResponseDto(401, "token解析出错,登陆过期", null);
            response.getWriter().write(JSON.toJSONString(dto));
            return false;
        }
        // token不为空，放行
        return true;
    }
}

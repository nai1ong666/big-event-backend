package com.itheima.interceptors;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if(redisToken==null){
                //token已经失效了
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // ============新增：权限校验逻辑================
            if(handler instanceof org.springframework.web.method.HandlerMethod){
                org.springframework.web.method.HandlerMethod handlerMethod = (org.springframework.web.method.HandlerMethod) handler;
                // 获取@RequireAdmin注解
                com.itheima.annotation.RequireAdmin anno = handlerMethod.getMethodAnnotation(com.itheima.annotation.RequireAdmin.class);
                if(anno != null){
                    //这个接口需要admin角色
                    String roleName = (String) claims.get("roleName");
                    if(!"admin".equals(roleName)){
                        //权限不足 返回403
                        response.setStatus(403);
                        return false;
                    }
                }
            }
            // ============================================

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}

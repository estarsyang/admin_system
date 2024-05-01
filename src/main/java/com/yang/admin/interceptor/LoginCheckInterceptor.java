package com.yang.admin.interceptor;

import com.yang.admin.common.ErrorCode;
import com.yang.admin.exception.BusinessException;
import com.yang.admin.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");

        if (StringUtils.isBlank(token)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return true;
    }
}

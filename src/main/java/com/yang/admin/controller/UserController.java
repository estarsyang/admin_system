package com.yang.admin.controller;

import com.yang.admin.common.BaseResponse;
import com.yang.admin.common.ErrorCode;
import com.yang.admin.common.ResultUtils;
import com.yang.admin.exception.BusinessException;
import com.yang.admin.model.domain.User;
import com.yang.admin.model.domain.request.UserLoginRequest;
import com.yang.admin.service.UserService;
import com.yang.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {


        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();

//        1. is blank
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = userService.userLogin(account, password);
        if (user != null){
            Map<String, Object> claims = new HashMap<>();

            claims.put("id", user.getId());
            claims.put("name", user.getName());
//            generate jwt
            String jwt = JwtUtils.generateJwt(claims);
            return ResultUtils.success(jwt);
        }
        return ResultUtils.error(ErrorCode.LOGIN_NOT_MATCH);
    }

    @PostMapping("/logout")
    public void userLogout(){

    }

    @PostMapping("/info")
    public BaseResponse userInfo(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");

        if (StringUtils.isBlank(token)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        try {
//            JwtUtils.parseJWT(token);
            Claims claims = JwtUtils.parseJWT(token);
            Integer operatorId = (Integer) claims.get("id");
            User user = userService.userInfo(operatorId);
            User safeUser = userService.getSafeUser(user);
            if (user != null){
                return ResultUtils.success(safeUser);
            }
        } catch (Exception e){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }


        return ResultUtils.error(ErrorCode.NO_AUTH);
    }
}

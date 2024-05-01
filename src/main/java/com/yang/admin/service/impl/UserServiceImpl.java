package com.yang.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.admin.common.ErrorCode;
import com.yang.admin.exception.BusinessException;
import com.yang.admin.model.domain.User;
import com.yang.admin.service.UserService;
import com.yang.admin.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author ddayang
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-05-01 16:06:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(String account, String password) {

//        blank
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        账号名不符合要求
        if (account.length()<4 || account.length() > 20){
            throw new BusinessException(ErrorCode.LOGIN_NOT_MATCH);
        }
        String validPattern = "[a-zA-Z0-9]+";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);

        if (!matcher.find()) {
            throw new BusinessException(ErrorCode.LOGIN_NOT_MATCH);
        }
//        query
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account",account);
        queryWrapper.eq("password",password);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_NOT_MATCH);
        }

        User safeUser = getSafeUser(user);

        return safeUser;
    }

    @Override
    public User userInfo(Integer id) {
        User user = userMapper.selectById(id);

        if (user != null){
            User safeUser = getSafeUser(user);
            return safeUser;
        }

        throw new BusinessException(ErrorCode.NO_AUTH);
    }

    @Override
    public User getSafeUser(User user) {
        User safeUser = new User();

        safeUser.setId(user.getId());
        safeUser.setAccount(user.getAccount());
        safeUser.setName(user.getName());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setStatus(user.getStatus());
        safeUser.setRole(user.getRole());
        safeUser.setCreate_time(user.getCreate_time());

        return safeUser;
    }
}





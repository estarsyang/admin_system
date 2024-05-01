package com.yang.admin.service;

import com.yang.admin.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ddayang
* @description 针对表【user】的数据库操作Service
* @createDate 2024-05-01 16:06:03
*/
public interface UserService extends IService<User> {

    /**
     *
     * @param account
     * @param password
     * @return
     */
    public User userLogin(String account, String password);
    public User userInfo(Integer id);

    User getSafeUser(User user);
}

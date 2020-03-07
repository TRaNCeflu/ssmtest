package com.zh.service.impl;

import com.zh.dao.IUserDao;
import com.zh.domain.User;
import com.zh.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl  implements IUserService {
    @Resource
    private IUserDao dao;

    public User selectUser(Integer id){
        return this.dao.selectUser(id);
    }

}

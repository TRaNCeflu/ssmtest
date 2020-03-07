package com.zh.dao;

import com.zh.domain.User;

public interface IUserDao {
    User selectUser(Integer id);
}

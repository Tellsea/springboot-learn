package com.tosea.service;

import com.tosea.pojo.User;

public interface UserService {

    User findByUserName(String userName);
}

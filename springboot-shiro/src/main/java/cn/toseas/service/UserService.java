package cn.toseas.service;

import cn.toseas.pojo.User;

public interface UserService {

    User findByUserName(String userName);
}

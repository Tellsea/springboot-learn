package cn.tellsea.service;

import cn.tellsea.pojo.User;

public interface UserService {

    User findByUserName(String userName);
}

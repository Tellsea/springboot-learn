package cn.tellsea.service;

import cn.tellsea.bean.User;

public interface UserService {

    User findByUserName(String userName);
}

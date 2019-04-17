package cn.tellsea.service;

import cn.tellsea.bean.User;

public interface AuthService {

    String login(String username, String password);

    User selectByNamePwd(String username, String password);

    void register(User user);
}

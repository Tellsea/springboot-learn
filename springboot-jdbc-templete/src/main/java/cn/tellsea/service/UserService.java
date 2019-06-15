package cn.tellsea.service;

import cn.tellsea.bean.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getUserList();

    int add(User user);

    int update(User user);

    int delete(Long id);
}

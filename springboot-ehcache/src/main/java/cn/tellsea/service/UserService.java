package cn.tellsea.service;

import cn.tellsea.bean.User;

import java.util.List;

public interface UserService {

    void insert(User user);

    void update(User user);

    void delete(Long id);

    User get(Long id);

    List<User> list();
}

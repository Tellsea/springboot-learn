package cn.tellsea.service;

import cn.tellsea.pojo.User;

public interface UserService {

    void insert(User user);

    void update(User user);

    void delete(Long id);

    void get(Long id);

    void list();

    void page();

    void pageByExample();
}

package com.tosea.service;

import com.tosea.pojo.User;

public interface UserService {

    void insert(User user);

    void update(User user);

    void delete(Long id);

    void get(Long id);

    void list();

    void page();

    void pageByExample();
}

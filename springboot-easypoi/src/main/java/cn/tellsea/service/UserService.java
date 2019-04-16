package cn.tellsea.service;

import cn.tellsea.bean.User;

import java.util.List;

public interface UserService {

    List<User> selectAll();

    void insertList(List<User> list);
}

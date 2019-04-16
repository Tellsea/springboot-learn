package cn.tellsea.service.impl;

import cn.tellsea.bean.User;
import cn.tellsea.mapper.UserMapper;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public void insertList(List<User> list) {
        userMapper.insertList(list);
    }
}

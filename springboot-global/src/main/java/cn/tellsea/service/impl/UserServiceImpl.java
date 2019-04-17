package cn.tellsea.service.impl;

import cn.tellsea.bean.User;
import cn.tellsea.mapper.UserMapper;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 默认spring事务只在发生未被捕获的 RuntimeException 时才回滚
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertList(List<User> list) {
        for (User user : list) {
            if ("".equals(user.getName())) {
                System.out.println(1 / 0); // 第四个用户抛出异常：java.lang.ArithmeticException: / by zero
            }
            userMapper.insert(user);
        }
    }
}

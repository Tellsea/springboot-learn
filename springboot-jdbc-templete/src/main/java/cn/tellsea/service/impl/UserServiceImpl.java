package cn.tellsea.service.impl;

import cn.tellsea.bean.User;
import cn.tellsea.dao.UserDao;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(Long id) {
        return userDao.delete(id);
    }
}

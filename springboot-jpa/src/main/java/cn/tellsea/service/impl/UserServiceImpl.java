package cn.tellsea.service.impl;

import cn.tellsea.bean.User;
import cn.tellsea.repository.UserRepository;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        // 新增和更新调用的接口一样，区别就是更新的参数中有id
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        // 新增和更新调用的接口一样，区别就是更新的参数中有id
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        // 新增和更新调用的接口一样，区别就是更新的参数中有id
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        // 新增和更新调用的接口一样，区别就是更新的参数中有id
        return userRepository.findAll();
    }

    @Override
    public Page<User> listByPage(int pageCode, int pageSize) {

//        Sort sort = new Sort(Sort.Direction.ASC, "id"); 排序
//        Pageable pageable = new PageRequest(pageCode, pageSize, sort);
        Pageable pageable = PageRequest.of(pageCode, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByName(String userName) {
        return userRepository.finByName(userName);
    }
}

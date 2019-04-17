package cn.tellsea.service;

import cn.tellsea.bean.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void save(User user);

    void update(User user);

    void delete(Integer id);

    User findById(Integer id);

    List<User> findAll();

    Page<User> listByPage(int pageCode, int pageSize);

    User findByUserName(String userName);

    User findByName(String userName);
}

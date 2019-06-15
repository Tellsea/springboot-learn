package cn.tellsea.dao.impl;

import cn.tellsea.bean.User;
import cn.tellsea.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository // 用于标注数据访问组件
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(Long id) {
        List<User> list = jdbcTemplate.query("select * from tb_user where id = ?", new Object[]{id}, new BeanPropertyRowMapper(User.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUserList() {
        List<User> list = jdbcTemplate.query("select * from tb_user", new Object[]{}, new BeanPropertyRowMapper(User.class));
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public int add(User user) {
        return jdbcTemplate.update("insert into tb_user(username, password, ctime) values(?, ?, ?)",
                user.getUsername(), user.getPassword(), new Date());
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update("update tb_user SET username = ? , password = ? WHERE id=?",
                user.getUsername(), user.getPassword(), user.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("delete from tb_user where id = ? ", id);
    }

}

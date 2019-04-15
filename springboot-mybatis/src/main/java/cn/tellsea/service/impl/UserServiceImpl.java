package cn.tellsea.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.tellsea.mapper.UserMapper;
import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void insert(User user) {
        int count = userMapper.insert(user);
        if (count != 1) {
            System.out.println("新增失败");
        }
    }

    public void update(User user) {
        int count = userMapper.updateByPrimaryKey(user);
        if (count != 1) {
            System.out.println("更新失败");
        }
    }

    public void delete(Long id) {
        int count = userMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            System.out.println("删除失败");
        }
    }

    public void get(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            System.out.println("结果为空");
        } else {
            System.out.println(user);
        }
    }

    public void list() {
        List<User> list = userMapper.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("结果为空");
        } else {
            System.out.println(list);
        }
    }

    public void page() {
        PageHelper.startPage(1, 3);
        List<User> list = userMapper.selectAll();
        PageInfo<User> info = new PageInfo<>(list);
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("结果为空");
        } else {
            System.out.println(info);
        }
    }

    public void pageByExample() {
        PageHelper.startPage(1, 3);
        Example example = new Example(User.class);
        // 当名字含有a
        example.createCriteria().orLike("name", "%a%").orEqualTo("letter");
        List<User> list = userMapper.selectByExample(example);
        PageInfo<User> info = new PageInfo<>(list);
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("结果为空");
        } else {
            System.out.println(info);
        }
    }

    /*public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) throws LyException {
        // 分页
        PageHelper.startPage(page, rows);
        Example example = new Example(Brand.class);
        // 过滤
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key.toUpperCase()); // 设立条件
        }
        // 排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        // 解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }*/
}

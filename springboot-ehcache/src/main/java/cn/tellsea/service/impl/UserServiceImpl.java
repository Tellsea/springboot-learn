package cn.tellsea.service.impl;

import cn.tellsea.mapper.UserMapper;
import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "users")//缓存名字
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //这里的单引号不能少，否则会报错，被识别是一个对象
    private static final String CACHE_KEY = "'user'";

    //新增
    @Override
    @CachePut(key = CACHE_KEY)//先执行方法，然后将返回值存入缓存
    public void insert(User user) {
        int count = userMapper.insert(user);
        if (count != 1) {
            System.out.println("新增失败");
        }
    }

    //更新
    @Override
    @CachePut(key = "'user_'+#user.getId()")//先执行方法，然后将返回值存入缓存
    public void update(User user) {
        int count = userMapper.updateByPrimaryKey(user);
        if (count != 1) {
            System.out.println("更新失败");
        }
    }

    //删除
    @Override
    @CacheEvict(key = "'user_'+#id") //清除缓存
    public void delete(Long id) {
        int count = userMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            System.out.println("删除失败");
        }
    }

    //查找
    @Override
    @Cacheable(key = "'user_'+#id")//先查缓存，有就直接返回，没得就把返回值加入缓存
    public User get(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(key = "'users'")
    public List<User> list() {
        List<User> list = userMapper.selectAll();
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    /*
     * @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，
     *              而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     * @CacheEvict : 清除缓存。
     * @CachePut : 也可以声明一个方法支持缓存功能。使用@CachePut标注的方法在执行前不会去检查缓存
     *              中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * 这三个方法中都有两个主要的属性：value 指的是 ehcache.xml 中的缓存策略空间；key 指的是缓存的标识，同时可以用 # 来引用参数。
     * */
}

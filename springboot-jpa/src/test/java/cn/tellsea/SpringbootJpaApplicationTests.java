package cn.tellsea;

import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaApplicationTests {

    @Autowired
    private UserService userService;

    // 新增
    @Test
    public void save() {
        User user = new User();
        user.setUserName("Lingda");
        user.setUserPassword("123456");
        userService.save(user);
    }

    // 更新
    @Test
    public void update() {
        User user = new User();
        user.setId(1);
        user.setUserName("小海绵");
        user.setUserPassword("654321");
        userService.update(user);
    }

    // 删除
    @Test
    public void delete() {
        userService.delete(5);
    }

    // 根据id查
    @Test
    public void get() {
        User user = userService.findById(1);
        System.out.println(user);
    }

    // 查询所有
    @Test
    public void list() {
        List<User> list = userService.findAll();
        list.forEach(user -> System.out.println(user));
    }

    // 分页查询
    @Test
    public void listByPage() {
        int pageCode = 0; // 当前页，注意这里默认第一页是0，坑爹
        int pageSize = 3; // 每页显示3条记录
        Page<User> page = userService.listByPage(pageCode, pageSize);
        System.out.println("总记录数=" + page.getTotalElements());
        System.out.println("总页数=" + page.getTotalPages());
        page.getContent().forEach(user -> System.out.println(user));
    }

    // 自定义查询 -- 方法
    @Test
    public void findByUserName() {
        User user = userService.findByUserName("小海绵");
        System.out.println(user);
    }

    // 自定义查询 -- 注解
    @Test
    public void findByName() {
        User user = userService.findByName("小海绵");
        System.out.println(user);
    }
}

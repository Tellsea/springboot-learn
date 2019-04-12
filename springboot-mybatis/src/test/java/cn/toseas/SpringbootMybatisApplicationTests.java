package cn.toseas;

import cn.toseas.pojo.User;
import cn.toseas.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserService userService;

    /**
     * 新增
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("toseas");
        user.setPasswd("123456");
        userService.insert(user);
    }

    /**
     * 更新
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(1L);
        user.setName("aesot");
        user.setPasswd("654321");
        userService.update(user);
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        userService.delete(2L);
    }

    /**
     * 查询单个
     */
    @Test
    public void get() {
        userService.get(1L);
    }

    /**
     * 查询所有
     */
    @Test
    public void list() {
        userService.list();
    }

    /**
     * 分页查询
     */
    @Test
    public void page() {
        userService.page();
    }

    /**
     * 分页查询 + 条件过滤
     */
    @Test
    public void pageByExample() {
        userService.pageByExample();
    }

}

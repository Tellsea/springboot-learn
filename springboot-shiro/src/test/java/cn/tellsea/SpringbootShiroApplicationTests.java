package cn.tellsea;

import cn.tellsea.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootShiroApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    public void md5() {
        String password = "123456";
        String salt = "sdfnegaf7gafj3nfdsfdsj9";
        System.out.println(new Md5Hash(password, salt, 2));
    }

}

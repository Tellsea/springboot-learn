package cn.tellsea.web;

import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //这里随便给一个借口，请求之后druid就会监控到当前操作的详细信息
    //访问localhost:8080/druid
    @GetMapping({"", "/", "index"})
    public List<User> list() {
        return userService.list();
    }
}

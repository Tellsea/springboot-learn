package cn.tellsea.web;

import cn.tellsea.bean.User;
import cn.tellsea.dto.ResponseCode;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 测试响应结果
    @GetMapping("")
    public ResponseCode test() {
        return ResponseCode.success();
    }

    // service.impl报错，测试事物是否回滚
    @GetMapping("list")
    public void insertList() {
        List<User> list = new ArrayList<>();
        list.add(new User("Tom", "111111"));
        list.add(new User("Susan", "222222"));
        list.add(new User("Sky", "333333"));
        list.add(new User("", "444444"));
        list.add(new User("Bang", "555555"));
        userService.insertList(list);
    }
}

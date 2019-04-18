package cn.tellsea.web;

import cn.tellsea.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RouterController {

    //测试常用数据
    @GetMapping("/data")
    public String data(Model model) {
        // 字符串
        model.addAttribute("msg", "Hello, Thymeleaf!");
        // 对象
        User user = new User();
        user.setAge(21);
        user.setName("Jack Chen");
        user.setFriend(new User("李小龙", 30));
        model.addAttribute("user", user);
        // 方法
        model.addAttribute("today", new Date());
        // 遍历
        List<User> users = new ArrayList<>();
        users.add(new User("李小龙", 30));
        users.add(new User("哈哈哈", 20));
        users.add(new User("呵呵呵", 10));
        model.addAttribute("users", users);
        return "data";
    }
}

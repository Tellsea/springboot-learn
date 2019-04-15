package cn.tellsea.web;

import cn.tellsea.annotation.SystemControllerLog;
import cn.tellsea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    @SystemControllerLog(description = "测试Get日志打印")
    public String get() {
        return "测试Get日志打印";
    }

    @PostMapping("post")
    @SystemControllerLog(description = "测试POST日志打印")
    public String post(@RequestParam("username") String username) {
        return "测试POST日志打印, 参数为：" + username;
    }

    @GetMapping("service")
    public String service() {
        return userService.test();
    }
}

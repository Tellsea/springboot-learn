package cn.tellsea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    @GetMapping({"/", "", "/index"})
    public String index() {
        return "index";
    }

    // 定制的登录表单
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("level1")
    @ResponseBody
    public String level1() {
        return "level1 拥有角色VIP1";
    }

    @GetMapping("level2")
    @ResponseBody
    public String level2() {
        return "level2 拥有角色VIP2";
    }

    @GetMapping("level3")
    @ResponseBody
    public String level3() {
        return "level3 拥有角色VIP3";
    }
}

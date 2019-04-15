package cn.tellsea.web;

import cn.tellsea.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model) {
        log.info("用户名；" + user.getUserName() + ", 密码：" + user.getUserPwd());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPwd());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "index";

        } catch (DisabledAccountException e) {
            model.addAttribute("message", "该用户未授权");
            return "login";
        } catch (UnknownAccountException e) {
            model.addAttribute("message", "该用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message", "密码错误");
            return "login";
        }
    }
}

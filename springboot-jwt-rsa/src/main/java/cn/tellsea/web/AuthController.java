package cn.tellsea.web;

import cn.tellsea.bean.User;
import cn.tellsea.bean.UserInfo;
import cn.tellsea.config.JwtProperties;
import cn.tellsea.service.AuthService;
import cn.tellsea.utils.CookieUtils;
import cn.tellsea.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${tellsea.jwt.cookieName}")
    private String cookieName;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("register")
    public String register(@Valid User user) {
        authService.register(user);
        return "注册成功";
    }

    /**
     * 登录授权
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("login")
    public String login(@RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        // 登录
        String token = authService.login(username, password);
        if (StringUtils.isBlank(token)) {
            log.info("未授权");
            return "未授权";
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.newBuilder(response).httpOnly().request(request).build(cookieName, token);
        return "登录成功";
    }

    /**
     * 校验用户登录状态
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    @GetMapping("verify")
    public UserInfo verify(@CookieValue("TELLSEA_TOKEN") String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            // 解析token
            UserInfo info = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 刷新token
            String newToken = JwtUtils.generateToken(info, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.newBuilder(response).httpOnly().request(request).build(cookieName, newToken);
            // 已登录
            return info;
        } catch (Exception e) {
            // token过期或无效
            log.info("token过期或无效");
            return null;
        }
    }
}
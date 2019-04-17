package cn.tellsea.service.impl;

import cn.tellsea.bean.User;
import cn.tellsea.bean.UserInfo;
import cn.tellsea.config.JwtProperties;
import cn.tellsea.mapper.UserMapper;
import cn.tellsea.service.AuthService;
import cn.tellsea.utils.CodecUtils;
import cn.tellsea.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void register(User user) {
        // 校验验证码
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 保存数据
        user.setCreated(new Date());
        userMapper.insert(user);
    }

    @Override
    public String login(String username, String password) {
        try {
            // 检验用户名，密码
            User user = selectByNamePwd(username, password);
            if (user == null) {
                log.info("login 用户名或密码错误");
                return null;
            }
            // 生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), username), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            log.info("login Exception 用户名或密码错误");
            return null;
        }
    }

    @Override
    public User selectByNamePwd(String username, String password) {
        User u = new User();
        u.setUsername(username);
        User user = userMapper.selectOne(u);
        if (user == null) {
            log.info("selectByNamePwd 用户不存在");
            return null;
        }
        // 校验密码
        if (!StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()))) {
            log.info("selectByNamePwd 密码错误");
            return null;
        }
        return user;
    }
}

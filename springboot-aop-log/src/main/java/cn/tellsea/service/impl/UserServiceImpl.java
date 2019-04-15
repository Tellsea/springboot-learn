package cn.tellsea.service.impl;

import cn.tellsea.annotation.SystemServiceLog;
import cn.tellsea.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @SystemServiceLog(description = "测试service层日志打印")
    public String test() {
        return "测试service层日志打印";
    }
}

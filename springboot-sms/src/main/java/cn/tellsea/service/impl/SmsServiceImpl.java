package cn.tellsea.service.impl;

import cn.tellsea.config.SmsProperties;
import cn.tellsea.service.SmsService;
import cn.tellsea.utils.JsonUtils;
import cn.tellsea.utils.NumberUtils;
import cn.tellsea.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@EnableConfigurationProperties(SmsProperties.class)
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    public void sendCode(String phone) {
        // 生成redis的key
        String key = KEY_PREFIX + phone;
        String code = NumberUtils.generateCode(6);
        Map<Object, Object> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        // 异步发送验证码
        smsUtils.sendSms(phone, smsProperties.getSignName(), smsProperties.getVerifyCodeTemplete(), JsonUtils.toString(msg));
        // 保存验证码
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }
}

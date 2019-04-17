package cn.tellsea.web;

import cn.tellsea.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信
     *
     * @param phone
     * @return
     */
    @PostMapping("code")
    public String sendCode(@RequestParam("phone") String phone) {
        smsService.sendCode(phone);
        return "发送短信到：" + phone;
    }
}

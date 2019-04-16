package cn.tellsea.web;

import cn.tellsea.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "index";
    }

    // 发送简单邮件
    @GetMapping("sendSimpleMail")
    public String sendSimpleMail() {
        emailService.sendSimpleMail();
        return "index";
    }

    // 发送Html邮件
    @GetMapping("sendHtmlMail")
    public String sendHtmlMail() {
        emailService.sendHtmlMail();
        return "index";
    }

    // 发送带附件的邮件
    @GetMapping("sendAttachmentsMail")
    public String sendAttachmentsMail() {
        emailService.sendAttachmentsMail();
        return "index";
    }

    // 发送带静态资源的邮件
    @GetMapping("sendInlineMail")
    public String sendInlineMail() {
        emailService.sendInlineMail();
        return "index";
    }

    // 发送模板邮件
    @GetMapping("sendTemplateMail")
    public String sendTemplateMail() {
        emailService.sendTemplateMail();
        return "index";
    }

}

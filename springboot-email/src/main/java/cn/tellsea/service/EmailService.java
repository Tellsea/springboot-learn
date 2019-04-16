package cn.tellsea.service;

public interface EmailService {

    void sendSimpleMail();

    void sendHtmlMail();

    void sendAttachmentsMail();

    void sendInlineMail();

    void sendTemplateMail();
}

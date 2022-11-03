package com.noloafing.service.impl;

import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.MailService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@ConfigurationProperties(prefix = "spring.mail")
public class MailServiceImpl implements MailService {
    //发送方账号
    private String username;
    //发件人昵称
    private String nickname;

    //发送对象
    @Resource
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMall(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(nickname+'<'+username+'>');
            message.setTo(to);
            message.setCc(username);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //发送带附件的邮件
    @Override
    public void sendAttachFileMail(String to, String subject, String content, File file) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(nickname+'<'+username+'>');
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content);
            //添加附件
            messageHelper.addAttachment(file.getName(), file);
            //发送
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(nickname+'<'+username+'>');
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new SystemException(AppHttpCodeEnum.SEND_EMAIL_CODE_FAILED);
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

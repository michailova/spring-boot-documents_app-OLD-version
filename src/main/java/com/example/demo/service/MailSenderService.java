package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
  //  @Autowired
    private static JavaMailSender mailSender;
    public MailSenderService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }


    public static void sendNewMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("helen.mikhailava.by@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
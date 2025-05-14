package com.budgetblaze.UserService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    JavaMailSender mailSender;

    public void EmailSender(String recipientEmail, String mailSubject, String mailBody){
        /*  We will be using the Simple MailSender Api to send email */
        // Create the SimpleMailMessage and configure the Mail Properties
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom("singhsaksham1053@gmail.com");
        message.setTo(recipientEmail);
        message.setText(mailBody);
        message.setSubject(mailSubject);
        System.out.println(recipientEmail);
        System.out.println(mailBody);
        System.out.println(mailSubject);
        //Send it
        mailSender.send(message);
        System.out.println("Email Sent Successfully for User Verification");
    }
}

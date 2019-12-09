package com.logistics.email.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
public class Emailnotifications {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	

	public void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nkolli@artissol.com");;
        System.out.println("Mail sending");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Test Mail for Artis"
        		+ "/n test another line");
       
        
        javaMailSender.send(msg);
        System.out.println("DOne");
    }

}

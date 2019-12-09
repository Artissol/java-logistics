package com.logistics;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.logistics.email.notification.Emailnotifications;

@SpringBootApplication
@EnableScheduling
public class LogisticApiApplication extends SpringBootServletInitializer {

	@Autowired
    private JavaMailSender javaMailSender;
	
	public static void main(String[] args) {
		SpringApplication.run(LogisticApiApplication.class, args);
		
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LogisticApiApplication.class);
    }
	
	 void run(String args) throws MessagingException {

        System.out.println("Sending Email...");

        sendEmail();
            //sendEmailWithAttachment();

        System.out.println("Done");

    }

	 void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nkolli@artissol.com");;
        System.out.println("Mail sending");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
        
        javaMailSender.send(msg);
        System.out.println("DOne");
    }
}

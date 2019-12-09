package com.logistics.email.notification;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.multipart.MultipartFile;

import com.logistics.Response;
import com.logistics.request.constants.Email;

public class SendEmail {

	private String username = "test@candorinfratech.in";
	private String password = "Test123#";
	Properties props = new Properties();
	private String host = "smtpout.secureserver.net";

	public SendEmail() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "465");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	}

	public Response sendEmail(String to, String cc, String bcc, String subject, String body,
			MultipartFile[] attachFiles) {
		// String body = "You're invited to join Logistics. Here is your system
		// generated password <b>welcome123</b>. <br/> <br/>If you're having problems,
		// please feel free to contact us at logistics@artissol.com. We'll be glad to
		// help.<br/> <br/>Regards,<br/>Team Logistics.";
		/*
		 * username = "tkalluri@artissol.com"; password = "Artis@123";
		 */
		String domain = "secureserver.net";
		switch (domain) {
		case "1and1.com":
			host = "smtp.1and1.com";
			break;
		case "gmail.com":
			host = "smtp.gmail.com";
			break;
		case "yahoo.com":
			host = "smtp.mail.yahoo.com";
			break;
		case "rediffmail.com":
			host = "smtp.rediffmail.com";
			break;
		case "secureserver.net":
			host = "smtpout.secureserver.net";
			break;	
		default:
			host = "smtp.1and1.com";
			username = "support@artissol.com";
			password = "Chitti@17";
			break;
		}
		props.put("mail.smtp.host", host);
		Response response = new Response();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			InternetAddress[] myToList = InternetAddress.parse(to);
			InternetAddress[] myBccList = InternetAddress.parse(bcc);
			InternetAddress[] myCcList = InternetAddress.parse(cc);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, myToList);
			message.setRecipients(Message.RecipientType.BCC, myBccList);
			message.setRecipients(Message.RecipientType.CC, myCcList);

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setContent(body, "text/html");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			/*
			 * if(attachFiles != null && attachFiles.length > 0){ for (MultipartFile
			 * filePath : attachFiles) { MimeBodyPart attachPart = new MimeBodyPart(); try {
			 * filePath.transferTo(new
			 * File(filePath.getOriginalFilename()).getAbsoluteFile());
			 * attachPart.attachFile(filePath.getOriginalFilename()); } catch (IOException
			 * ex) { ex.printStackTrace(); } multipart.addBodyPart(attachPart); } }
			 */

			// Send the complete message parts
			message.setContent(multipart);
			// Transport.send(message, message.getAllRecipients());
			Transport.send(message);

			response.setStatus(200);
			response.setMessage("Sent Email Successfully");

		} catch (MessagingException e) {
			response.setStatus(-1);
			response.setMessage("" + e);
			response.setObject(e);
			e.printStackTrace();
		}

		return response;

	}
}
package at.jku.cis.iVolunteer.core.security;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CoreEmailService {
 
    @Autowired
    private JavaMailSender emailSender;
 
    public void sendPlainMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("iVolunteerMail@gmx.at");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
    public void sendMimeMessage(String to, String subject, String text) {
    	MimeMessage mimeMessage = this.emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	try {
	    	helper.setTo(to);
	    	helper.setSubject(subject);
	        helper.setFrom("iVolunteerMail@gmx.at");
	        helper.setText(text, true);
	    	this.emailSender.send(mimeMessage);
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    }
    
    
    
}

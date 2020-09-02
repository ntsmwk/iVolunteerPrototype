package at.jku.cis.iVolunteer.core.security.activation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.ActivationLinkClickedResponse;
import at.jku.cis.iVolunteer.model.user.ActivationResponse;
import at.jku.cis.iVolunteer.model.user.PendingActivation;

@Service
public class CoreActivationService {
 
    @Autowired private JavaMailSender emailSender;
    @Autowired private CorePendingActivationRepository pendingActivationRepository;
	@Value("${spring.data.websiteuri}") String uri;

 
    public void sendPlainMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("iVolunteerMail@gmx.at");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
    public PendingActivation createActivation(CoreUser user) {
		String md5 = RandomStringUtils.randomAlphanumeric(128);
		PendingActivation pendingActivation = new PendingActivation(md5, user.getId());
		return pendingActivationRepository.save(pendingActivation);
    }
    
    public PendingActivation findActivationById(String activationId) {
    	return pendingActivationRepository.findOne(activationId);
    }
    
    public PendingActivation findActivationByUserId(String userId) {
    	return pendingActivationRepository.findByUserId(userId);
    }
    
    private void deletePendingActivation(String activationId) {
    	pendingActivationRepository.delete(activationId);
    }
    
    public void sendMimeActivationMessage(CoreUser user) {
    	PendingActivation activation = createActivation(user);
		String url = uri + "/register/activate/" + activation.getActivationId();
		String msg = patchURL(getText(), url);
    	
    	
    	MimeMessage mimeMessage = this.emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	try {
	    	helper.setTo(user.getEmails().get(0));
	    	helper.setSubject("Willkommen bei iVolunteer");
	        helper.setFrom("iVolunteerMail@gmx.at");
	        helper.setText(msg, true);
	    	this.emailSender.send(mimeMessage);
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    }
    
    public ActivationLinkClickedResponse handleActivationLinkClicked(String activationId) {
    	PendingActivation pendingActivation = findActivationById(activationId);
    	
    	ActivationResponse activationResponse;
    	
    	if (pendingActivation == null) {
    		activationResponse = ActivationResponse.FAILED;
    	} else {
    		Date now = new Date();
    		long delta = now.toInstant().toEpochMilli() - pendingActivation.getTimestamp().toInstant().toEpochMilli();
    		if (delta >= 900000) { //15 minutes
    			activationResponse = ActivationResponse.EXPIRED;
    		} else {
    			activationResponse = ActivationResponse.SUCCESS;
        		//TODO update user flag;
    		}	
    		deletePendingActivation(activationId);
    		
    	}
    	return new ActivationLinkClickedResponse(pendingActivation, activationResponse);
    	
    }
    
    private String getText() {
		Resource resource = new ClassPathResource("/eMailTemplate/template.html");
		try {
			InputStream input = resource.getInputStream();
	        byte[] bdata = FileCopyUtils.copyToByteArray(input);
	        String data = new String(bdata, StandardCharsets.UTF_8);
	        return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String patchURL(String msg, String url) {
		if (msg == null) {
			return null;
		}
		msg = msg.replace(new String("link-placeholder"), url);
		return msg;
	}
	
    
    
    
}

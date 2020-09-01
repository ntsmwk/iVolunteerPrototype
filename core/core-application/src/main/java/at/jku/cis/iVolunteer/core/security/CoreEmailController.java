package at.jku.cis.iVolunteer.core.security;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreEmailController {

	@Autowired private CoreEmailService emailService;
	@Value("${spring.data.websiteuri}") String uri;

	
	@PutMapping("register/test/email")
	private void testEmail() {
		//TODO AK - generate activation key
		String md5 = RandomStringUtils.randomAlphanumeric(128);
		String url = uri + "/register/activate/" + md5;
		String msg = patchURL(getText(), url);
		emailService.sendMimeMessage("alexander.kopp@gmx.at", "iVolunteer", msg);
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

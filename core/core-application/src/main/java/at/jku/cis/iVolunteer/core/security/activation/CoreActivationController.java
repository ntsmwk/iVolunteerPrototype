package at.jku.cis.iVolunteer.core.security.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@RestController
public class CoreActivationController {

	@Autowired private CoreActivationService activationService;
	@Autowired private CoreUserRepository userRepository;

	
	@PutMapping("register/test/email")
	private void testEmail() {
		final CoreUser user = userRepository.findByUsername("AKop");
		activationService.sendMimeActivationMessage(user);
	}
	
	
	
	
	
}

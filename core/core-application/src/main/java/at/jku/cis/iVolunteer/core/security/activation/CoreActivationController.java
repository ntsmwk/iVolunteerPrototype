package at.jku.cis.iVolunteer.core.security.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.ActivationLinkClickedResponse;

@RestController
public class CoreActivationController {
	@Autowired private CoreActivationService activationService;
	@Autowired private CoreUserRepository userRepository;

	@PutMapping("register/test/email")
	private void testEmail() {
		final CoreUser user = userRepository.findByUsername("AKop");
		activationService.createActivationAndSendLink(user);
	}
	
	@PostMapping("register/activate/generate-link/{username}")
	private boolean generateActivationLink(@PathVariable("username") String username) {
		CoreUser user = userRepository.findByUsername(username);
		if (user == null) {
			return false;
		} 
		return activationService.createActivationAndSendLink(user);
	}
	
	@PostMapping("register/activate/{activationId}")
	private ActivationLinkClickedResponse activationLinkClicked(@PathVariable("activationId") String activationId) {
		return activationService.handleActivationLinkClicked(activationId);
	}

}

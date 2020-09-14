package at.jku.cis.iVolunteer.core.security.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.ActivationLinkClickedResponse;
import at.jku.cis.iVolunteer.model.registration.AccountType;

@RestController
public class CoreActivationController {
	@Autowired private CoreActivationService activationService;
	@Autowired private CoreUserRepository userRepository;
	
	@PostMapping("register/activate/generate-link/{username}/user")
	private boolean generateActivationLink(@PathVariable("username") String username, @RequestParam("type") AccountType type) {
		CoreUser user = userRepository.findByUsername(username);
		if (user == null || user.isActivated()) {
			return false;
		} 
		return activationService.createActivationAndSendLink(user, type);
	}
	
	@PostMapping("register/activate/generate-link/email")
	private boolean generateActivationLinkViaEmail(@RequestBody String email, @RequestParam("type") AccountType type) {
		CoreUser user = userRepository.findByLoginEmail(email);
		if (user == null || user.isActivated()) { 
			return false; 
		}
		
		return activationService.createActivationAndSendLink(user, type);
	}
	
	
	@PostMapping("register/activate/{activationId}")
	private ActivationLinkClickedResponse activationLinkClicked(@PathVariable("activationId") String activationId) {
		return activationService.handleActivationLinkClicked(activationId);
	}
	


}

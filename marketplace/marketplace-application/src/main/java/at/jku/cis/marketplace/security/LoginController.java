package at.jku.cis.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.marketplace.participant.Participant;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public Participant getLoggedInParticipant() {
		return loginService.getLoggedInParticipant();
	}
	
	@GetMapping("/login/role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

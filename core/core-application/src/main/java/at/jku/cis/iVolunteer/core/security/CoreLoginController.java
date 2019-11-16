package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@RestController
@RequestMapping("/login")
public class CoreLoginController {

	@Autowired private CoreLoginService loginService;

	@GetMapping
	public CoreUser getLoggedInParticipant() {
		CoreUser participant = loginService.getLoggedInParticipant();
		if (participant instanceof CoreHelpSeeker) {
			return (CoreHelpSeeker) participant;
		}
		if (participant instanceof CoreFlexProd) {
			return (CoreFlexProd) participant;
		}
		if (participant instanceof CoreVolunteer) {
			return (CoreVolunteer) participant;
		}
		if (participant instanceof CoreRecruiter) {
			return (CoreRecruiter) participant;
		}
		throw new RuntimeException("User not found");

	}

	@GetMapping("role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

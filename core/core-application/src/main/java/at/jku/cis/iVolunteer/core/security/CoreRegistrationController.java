package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@RestController
@RequestMapping("/register")

public class CoreRegistrationController {

	@Autowired private CoreRegistrationService coreRegistrationService;

	@PostMapping("/volunteer")
	public void registerVolunteer(@RequestBody CoreVolunteer user) {
		coreRegistrationService.registerVolunteer(user);
	}

}

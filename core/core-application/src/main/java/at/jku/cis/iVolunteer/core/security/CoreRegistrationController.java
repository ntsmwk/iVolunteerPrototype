package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.AccountType;

@RestController
@RequestMapping("/register")

public class CoreRegistrationController {

	@Autowired
	private CoreRegistrationService coreRegistrationService;

	@PostMapping()
	public CoreUser registerUser(@RequestBody CoreUser user, @RequestParam("type") AccountType type) {
		
		if (type != AccountType.ORGANIZATION && type != AccountType.PERSON) {
			System.out.println("invalid registrationtype - either 'organization' or 'person'");
			return null;
		}
		
		return coreRegistrationService.registerUser(user, type);
	}

}

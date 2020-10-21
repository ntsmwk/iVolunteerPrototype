package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponse;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponseMessage;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.AccountType;

@RestController
@RequestMapping("/register")

public class CoreRegistrationController {

	@Autowired
	private CoreRegistrationService coreRegistrationService;

	@PostMapping
	public ResponseEntity<Object> registerUser(@RequestBody CoreUser user, @RequestParam("type") AccountType type) {
		if (type != AccountType.ORGANIZATION && type != AccountType.PERSON) {
			return new ResponseEntity<Object>(new ErrorResponse("invalid registrationtype - either 'organization' or 'person'"), HttpStatus.BAD_REQUEST);
		}
		RegisterResponseMessage responseMessage = coreRegistrationService.registerUser(user, type);
		
		if (responseMessage.getResponse() == RegisterResponse.OK) {
			return new ResponseEntity<Object>("", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(responseMessage, HttpStatus.BAD_REQUEST);
		}
	}

}

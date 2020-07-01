package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@RestController
@RequestMapping("/login")
public class CoreLoginController {

	@Autowired
	private CoreLoginService loginService;

	@GetMapping
	public CoreUser getLoggedInUser() {
		CoreUser user = loginService.getLoggedInUser();

		return user;
	}

	@GetMapping("role")
	public UserRole getLoggedInRole() {
		return loginService.getLoggedInUserRole();
	}
}

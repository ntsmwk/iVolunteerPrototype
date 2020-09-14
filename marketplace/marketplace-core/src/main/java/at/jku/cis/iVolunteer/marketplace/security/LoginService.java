package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.User;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	public User getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		User user = findByUsername((String) authentication.getPrincipal());

		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

	public UserRole getLoggedInUserRole(Tenant tenant) {
		User user = getLoggedInUser();

		if (user == null) {
			return UserRole.NONE;
		}

		return user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst().orElse(UserRole.VOLUNTEER);
	}

	private User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
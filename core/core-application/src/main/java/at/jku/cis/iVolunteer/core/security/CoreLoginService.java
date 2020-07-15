package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreLoginService {

	@Autowired
	private CoreUserRepository coreUserRepository;

	public CoreUser getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public UserRole getLoggedInUserRole() {
		// TODO Philipp: return array of users...
		CoreUser user = getLoggedInUser();

		if (user == null) {
			return UserRole.NONE;
		}
		return user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst().orElse(UserRole.VOLUNTEER);

	}

	private CoreUser findByUsername(String username) {
		return coreUserRepository.findByUsername(username);

	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

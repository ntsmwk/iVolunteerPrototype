package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Service
public class CoreLoginService {

	@Autowired
	private CoreUserRepository coreUserRepository;

	public CoreUser getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		CoreUser user = findByUsername((String) authentication.getPrincipal());

		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

	private CoreUser findByUsername(String username) {
		return coreUserRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

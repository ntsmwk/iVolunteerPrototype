package at.jku.cis.iVolunteer.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class LoginService {

	@Autowired private CoreUserRepository userRepository;

	public CoreUser getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		CoreUser user = findByUsername((String) authentication.getPrincipal());

		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

	public UserRole getLoggedInUserRole(Tenant tenant) {
		CoreUser user = getLoggedInUser();

		if (user == null) {
			return UserRole.NONE;
		}

		return user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst().orElse(UserRole.VOLUNTEER);
	}

	private CoreUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
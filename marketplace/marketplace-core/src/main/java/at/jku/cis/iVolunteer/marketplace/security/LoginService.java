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
	// @Autowired
	// private HelpSeekerRepository helpSeekerRepository;
	// @Autowired
	// private RecruiterRepository recruiterRepository;

	public User getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	// TODO Philipp
	// Abfrage nur mehr per Tenant möglich
	public UserRole getLoggedInUserRole(Tenant tenant) {
		User user = getLoggedInUser();

		return user.getSubscribedTenants().stream().filter(c -> {
			return c.getTenantId() == tenant.getId();
		}).findFirst().orElse(null).getRole();

		// User participant = getLoggedInUser();
		// if (participant instanceof HelpSeeker) {
		// return UserRole.HELP_SEEKER;
		// }
		// if (participant instanceof Volunteer) {
		// return UserRole.VOLUNTEER;
		// }
		// if (participant instanceof Recruiter) {
		// return UserRole.RECRUITER;
		// }
		// return null;
	}

	private User findByUsername(String username) {
		// User user = helpSeekerRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		// user = recruiterRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		return null;
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
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

	// @Autowired
	// private CoreHelpSeekerRepository coreHelpSeekerRepository;
	// @Autowired
	// private CoreVolunteerRepository coreVolunteerRepository;
	// @Autowired
	// private CoreFlexProdRepository coreFlexProdRepository;
	// @Autowired
	// private CoreRecruiterRepository coreRecruiterRepository;
	// @Autowired
	// private CoreAdminRepository coreAdminRepository;

	public CoreUser getLoggedInUser() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public UserRole getLoggedInParticipantRole() {
		CoreUser user = getLoggedInUser();

		return user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst().orElse(UserRole.NONE);

		// if (user instanceof CoreHelpSeeker) {
		// return UserRole.HELP_SEEKER;
		// }
		// if (user instanceof CoreVolunteer) {
		// return UserRole.VOLUNTEER;
		// }
		// if (user instanceof CoreFlexProd) {
		// return UserRole.FLEXPROD;
		// }
		// if (user instanceof CoreRecruiter) {
		// return UserRole.RECRUITER;
		// }
		// if (user instanceof CoreAdmin) {
		// return UserRole.ADMIN;
		// }
	}

	private CoreUser findByUsername(String username) {
		CoreUser user = coreUserRepository.findByUsername(username);
		if (user != null) {
			return user;
		}

		// CoreUser user = coreHelpSeekerRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		// user = coreVolunteerRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		// user = coreFlexProdRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		// user = coreRecruiterRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		// user = coreAdminRepository.findByUsername(username);
		// if (user != null) {
		// return user;
		// }
		return null;
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

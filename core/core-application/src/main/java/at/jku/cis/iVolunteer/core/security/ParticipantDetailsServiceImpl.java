package at.jku.cis.iVolunteer.core.security;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired
	private CoreUserRepository coreUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Philipp: asList(UserRole.NONE)!?
		CoreUser user = coreUserRepository.findByUsername(username);
		if (user != null) {
			UserRole role = user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst()
					.orElse(UserRole.VOLUNTEER);

			return new User(user.getUsername(), user.getPassword(), asList(role));
		}

		// CoreUser user = coreHelpSeekerRepository.findByUsername(username);
		// if (user != null) {
		// return new User(user.getUsername(), user.getPassword(), asList(HELP_SEEKER));
		// }

		// user = coreVolunteerRepository.findByUsername(username);
		// if (user != null) {
		// return new User(user.getUsername(), user.getPassword(), asList(VOLUNTEER));
		// }

		// user = coreRecruiterRepository.findByUsername(username);
		// if (user != null) {
		// return new User(user.getUsername(), user.getPassword(), asList(RECRUITER));
		// }

		// user = coreFlexProdRepository.findByUsername(username);
		// if (user != null) {
		// return new User(user.getUsername(), user.getPassword(), asList(FLEXPROD));
		// }

		// user = coreAdminRepository.findByUsername(username);
		// if (user != null) {
		// return new User(user.getUsername(), user.getPassword(), asList(ADMIN));
		// }

		throw new UsernameNotFoundException(username);
	}
}

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
		CoreUser user = coreUserRepository.findByUsername(username);
		if (user != null) {
			UserRole role = user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst()
					.orElse(UserRole.VOLUNTEER);

			return new User(user.getUsername(), user.getPassword(), asList(role));
		}

		throw new UsernameNotFoundException(username);
	}
}

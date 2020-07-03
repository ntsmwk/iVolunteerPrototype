package at.jku.cis.iVolunteer.marketplace.security;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.marketplace.security.service.ParticipantDetailsService;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		at.jku.cis.iVolunteer.model.user.User user = userRepository.findByUsername(username);
		if (user != null) {
			UserRole role = user.getSubscribedTenants().stream().map(c -> c.getRole()).findFirst()
					.orElse(UserRole.VOLUNTEER);
			return new User(user.getUsername(), user.getPassword(), asList(role));
		}

		throw new UsernameNotFoundException(username);
	}

}

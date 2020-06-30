package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.marketplace.security.service.ParticipantDetailsService;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired
	private UserRepository userRepository;
	// @Autowired
	// private HelpSeekerRepository helpSeekerRepository;
	// @Autowired
	// private RecruiterRepository recruiterRepository;

	// TODO Philipp
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		at.jku.cis.iVolunteer.model.user.User user = userRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), null);
		}

		// HelpSeeker helpSeeker = helpSeekerRepository.findByUsername(username);
		// if (helpSeeker != null) {
		// return new User(helpSeeker.getUsername(), helpSeeker.getPassword(),
		// asList(HELP_SEEKER));
		// }

		// Volunteer volunteer = volunteerRepository.findByUsername(username);
		// if (volunteer != null) {
		// return new User(volunteer.getUsername(), volunteer.getPassword(),
		// asList(VOLUNTEER));
		// }

		// Recruiter recruiter = recruiterRepository.findByUsername(username);
		// if (recruiter != null) {
		// return new User(recruiter.getUsername(), recruiter.getPassword(),
		// asList(RECRUITER));
		// }

		throw new UsernameNotFoundException(username);
	}
}

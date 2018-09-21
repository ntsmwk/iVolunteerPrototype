package at.jku.cis.iVolunteer.core.security;

import static at.jku.cis.iVolunteer.core.security.ParticipantRole.HELP_SEEKER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.VOLUNTEER;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker != null) {
			return new User(helpSeeker.getUsername(), helpSeeker.getPassword(), asList(HELP_SEEKER));
		}

		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer != null) {
			return new User(volunteer.getUsername(), volunteer.getPassword(), asList(VOLUNTEER));
		}

		throw new UsernameNotFoundException(username);
	}
}

package at.jku.cis.iVolunteer.marketplace.security;

import static at.jku.cis.iVolunteer.marketplace.security.ParticipantRole.HELP_SEEKER;
import static at.jku.cis.iVolunteer.marketplace.security.ParticipantRole.VOLUNTEER;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.marketplace.security.service.ParticipantDetailsService;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired private HelpSeekerRepository helpSeekerRepository;
	@Autowired private VolunteerRepository volunteerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HelpSeeker helpSeeker = helpSeekerRepository.findByUsername(username);
		if (helpSeeker != null) {
			return new User(helpSeeker.getUsername(), helpSeeker.getPassword(), asList(HELP_SEEKER));
		}

		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer != null) {
			return new User(volunteer.getUsername(), volunteer.getPassword(), asList(VOLUNTEER));
		}

		throw new UsernameNotFoundException(username);
	}
}

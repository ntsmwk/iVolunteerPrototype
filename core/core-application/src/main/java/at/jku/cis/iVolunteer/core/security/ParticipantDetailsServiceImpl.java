package at.jku.cis.iVolunteer.core.security;

import static at.jku.cis.iVolunteer.core.security.ParticipantRole.HELP_SEEKER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.VOLUNTEER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.FLEXPROD;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;

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
		
		CoreFlexProd flexProdUser = coreFlexProdRepository.findByUsername(username);
		if (flexProdUser != null) {
			return new User(flexProdUser.getUsername(), flexProdUser.getPassword(), asList(FLEXPROD));
		}

		throw new UsernameNotFoundException(username);
	}
}

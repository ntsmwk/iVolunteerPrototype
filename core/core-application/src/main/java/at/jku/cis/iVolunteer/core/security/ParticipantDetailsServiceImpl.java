package at.jku.cis.iVolunteer.core.security;

import static at.jku.cis.iVolunteer.core.security.ParticipantRole.FLEXPROD;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.HELP_SEEKER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.RECRUITER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.VOLUNTEER;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.ADMIN;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.admin.CoreAdminRepository;
import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private CoreAdminRepository coreAdminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CoreUser user = coreHelpSeekerRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), asList(HELP_SEEKER));
		}

		user = coreVolunteerRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), asList(VOLUNTEER));
		}

		user = coreRecruiterRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), asList(RECRUITER));
		}
		
		user = coreFlexProdRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), asList(FLEXPROD));
		}
		
		user = coreAdminRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(), asList(ADMIN));
		}

		throw new UsernameNotFoundException(username);
	}
}

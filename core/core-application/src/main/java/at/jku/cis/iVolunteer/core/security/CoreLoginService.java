package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class CoreLoginService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;

	public CoreUser getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public ParticipantRole getLoggedInParticipantRole() {
		CoreUser participant = getLoggedInParticipant();

		if (participant instanceof CoreHelpSeeker) {
			return ParticipantRole.HELP_SEEKER;
		}
		if (participant instanceof CoreVolunteer) {
			return ParticipantRole.VOLUNTEER;
		}
		if (participant instanceof CoreFlexProd) {
			return ParticipantRole.FLEXPROD;
		}
		if (participant instanceof CoreRecruiter) {
			return ParticipantRole.RECRUITER;
		}
		return ParticipantRole.NONE;
	}

	private CoreUser findByUsername(String username) {
		CoreUser user = coreHelpSeekerRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		user = coreVolunteerRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		user = coreFlexProdRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		user = coreRecruiterRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		return null;
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

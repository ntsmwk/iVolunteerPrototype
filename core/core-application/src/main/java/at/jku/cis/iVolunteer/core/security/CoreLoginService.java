package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class CoreLoginService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;

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
		} if (participant instanceof CoreFlexProd) {
			return ParticipantRole.FLEXPROD;
		}

		throw new RuntimeException("User not found");
	}

	private CoreUser findByUsername(String username) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker != null) {
			return helpSeeker;
		}
		CoreFlexProd flexProd = coreFlexProdRepository.findByUsername(username);
		if (flexProd != null) {
			return flexProd;
		}
		
		
		return coreVolunteerRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

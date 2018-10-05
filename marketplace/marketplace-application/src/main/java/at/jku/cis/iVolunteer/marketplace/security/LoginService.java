package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class LoginService {

	@Autowired private HelpSeekerRepository helpSeekerRepository;
	@Autowired private VolunteerRepository volunteerRepository;

	public User getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public ParticipantRole getLoggedInParticipantRole() {
		User participant = getLoggedInParticipant();
		if (participant instanceof HelpSeeker) {
			return ParticipantRole.HELP_SEEKER;
		}
		if (participant instanceof Volunteer) {
			return ParticipantRole.VOLUNTEER;
		}
		return null;
	}

	private User findByUsername(String username) {
		HelpSeeker helpSeeker = helpSeekerRepository.findByUsername(username);
		if (helpSeeker != null) {
			return helpSeeker;
		}
		return volunteerRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.RecruiterRepository;
import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.ParticipantRole;
import at.jku.cis.iVolunteer.model.user.Recruiter;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class LoginService {

	@Autowired private HelpSeekerRepository helpSeekerRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private RecruiterRepository recruiterRepository;

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
		if(participant instanceof Recruiter) {
			return ParticipantRole.RECRUITER;
		}
		return null;
	}

	private User findByUsername(String username) {
		User user = helpSeekerRepository.findByUsername(username);
		if (user != null) {
			return user;
		} 
		user = volunteerRepository.findByUsername(username);
		if(user != null) {
			return user;
		}
		user = recruiterRepository.findByUsername(username);
		if(user != null) {
			return user;
		}
		return null;
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
package at.jku.csi.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.csi.marketplace.participant.EmployeeRepository;
import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.participant.VolunteerRepository;

@Service
public class LoginService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	public Participant getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		String username = (String) authentication.getPrincipal();
		if (authentication.getAuthorities().contains(ParticipantRole.EMPLOYEE)) {
			return employeeRepository.findByUsername(username);
		}
		return volunteerRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

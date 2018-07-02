package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.employee.CoreEmployeeRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.participant.CoreParticipant;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;

@Service
public class CoreLoginService {

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;
	
	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	public CoreParticipant getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public ParticipantRole getLoggedInParticipantRole() {
		CoreParticipant participant = getLoggedInParticipant();
		if (participant instanceof CoreEmployee) {
			return ParticipantRole.EMPLOYEE;
		}
		if (participant instanceof CoreVolunteer) {
			return ParticipantRole.VOLUNTEER;
		}
		return null;
	}

	private CoreParticipant findByUsername(String username) {
		CoreEmployee employee = coreEmployeeRepository.findByUsername(username);
		if (employee != null) {
			return employee;
		}
		return coreVolunteerRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

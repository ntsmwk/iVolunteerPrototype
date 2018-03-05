package at.jku.csi.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.csi.marketplace.participant.Employee;
import at.jku.csi.marketplace.participant.EmployeeRepository;
import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;

@Service
public class LoginService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	public Participant getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public ParticipantRole getLoggedInParticipantRole() {
		Participant participant = getLoggedInParticipant();
		if (participant instanceof Employee) {
			return ParticipantRole.EMPLOYEE;
		}
		if (participant instanceof Volunteer) {
			return ParticipantRole.VOLUNTEER;
		}
		return null;
	}

	private Participant findByUsername(String username) {
		Employee employee = employeeRepository.findByUsername(username);
		if (employee != null) {
			return employee;
		}
		return volunteerRepository.findByUsername(username);
	}

	private Authentication determineAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}

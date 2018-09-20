package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.user.EmployeeRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.user.Employee;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class LoginService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	public User getLoggedInParticipant() {
		Authentication authentication = determineAuthentication();
		return findByUsername((String) authentication.getPrincipal());
	}

	public ParticipantRole getLoggedInParticipantRole() {
		User participant = getLoggedInParticipant();
		if (participant instanceof Employee) {
			return ParticipantRole.EMPLOYEE;
		}
		if (participant instanceof Volunteer) {
			return ParticipantRole.VOLUNTEER;
		}
		return null;
	}

	private User findByUsername(String username) {
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
package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.lib.mapper.participant.EmployeeMapper;
import at.jku.cis.iVolunteer.lib.mapper.participant.VolunteerMapper;
import at.jku.cis.iVolunteer.model.participant.Employee;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.dto.ParticipantDTO;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private VolunteerMapper volunteerMapper;

	@GetMapping("/login")
	public ParticipantDTO getLoggedInParticipant() {
		Participant participant = loginService.getLoggedInParticipant();
		if (participant instanceof Employee) {
			return employeeMapper.toDTO((Employee) participant);
		}
		return volunteerMapper.toDTO((Volunteer) participant);
	}

	@GetMapping("/login/role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

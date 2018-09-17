package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.user.CoreEmployeeMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreParticipantDTO;

@RestController
@RequestMapping("/login")
public class CoreLoginController {

	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private CoreEmployeeMapper employeeMapper;
	@Autowired
	private CoreVolunteerMapper volunteerMapper;

	@GetMapping
	public CoreParticipantDTO getLoggedInParticipant() {
		CoreUser participant = loginService.getLoggedInParticipant();
		if (participant instanceof CoreEmployee) {
			return employeeMapper.toDTO((CoreEmployee) participant);
		}
		return volunteerMapper.toDTO((CoreVolunteer) participant);
	}

	@GetMapping("role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

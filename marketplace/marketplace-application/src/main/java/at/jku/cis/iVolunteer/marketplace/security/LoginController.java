package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.lib.mapper.participant.ParticipantMapper;
import at.jku.cis.iVolunteer.model.participant.dto.ParticipantDTO;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private ParticipantMapper participantMapper;

	@GetMapping("/login")
	public ParticipantDTO getLoggedInParticipant() {
		return participantMapper.toDTO(loginService.getLoggedInParticipant());
	}
	
	@GetMapping("/login/role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

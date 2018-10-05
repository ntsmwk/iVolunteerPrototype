package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreParticipantDTO;

@RestController
@RequestMapping("/login")
public class CoreLoginController {

	@Autowired private CoreLoginService loginService;
	@Autowired private CoreHelpSeekerMapper helpSeekerMapper;
	@Autowired private CoreVolunteerMapper volunteerMapper;

	@GetMapping
	public CoreParticipantDTO getLoggedInParticipant() {
		CoreUser participant = loginService.getLoggedInParticipant();
		if (participant instanceof CoreHelpSeeker) {
			return helpSeekerMapper.toDTO((CoreHelpSeeker) participant);
		}
		return volunteerMapper.toDTO((CoreVolunteer) participant);
	}

	@GetMapping("role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

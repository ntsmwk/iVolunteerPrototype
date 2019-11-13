package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.user.CoreFlexProdMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreRecruiterMapper;
import at.jku.cis.iVolunteer.mapper.core.user.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreUserDTO;

@RestController
@RequestMapping("/login")
public class CoreLoginController {

	@Autowired private CoreLoginService loginService;
	@Autowired private CoreHelpSeekerMapper helpSeekerMapper;
	@Autowired private CoreVolunteerMapper volunteerMapper;
	@Autowired private CoreFlexProdMapper flexProdMapper;
	@Autowired private CoreRecruiterMapper recruiterMapper;

	@GetMapping
	public CoreUserDTO getLoggedInParticipant() {
		CoreUser participant = loginService.getLoggedInParticipant();
		if (participant instanceof CoreHelpSeeker) {
			return helpSeekerMapper.toDTO((CoreHelpSeeker) participant);
		}
		if (participant instanceof CoreFlexProd) {
			return flexProdMapper.toDTO((CoreFlexProd) participant);
		}
		if (participant instanceof CoreVolunteer) {
			return volunteerMapper.toDTO((CoreVolunteer) participant);
		}
		if (participant instanceof CoreRecruiter) {
			return recruiterMapper.toDTO((CoreRecruiter) participant);
		}
		throw new RuntimeException("User not found");

	}

	@GetMapping("role")
	public ParticipantRole getLoggedInRole() {
		return loginService.getLoggedInParticipantRole();
	}
}

package at.jku.cis.iVolunteer.marketplace.user;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.user.HelpSeeker;

@RestController
public class HelpSeekerController {

	@Autowired private HelpSeekerRepository helpSeekerRepository;


	@PostMapping("/helpseeker")
	public HelpSeeker registerHelpSeeker(@RequestBody HelpSeeker helpSeeker) {
		if (helpSeekerRepository.findOne(helpSeeker.getId()) != null) {
			throw new BadRequestException("HelpSeeker already registed");
		}
		return helpSeekerRepository.insert(helpSeeker);
	}
}
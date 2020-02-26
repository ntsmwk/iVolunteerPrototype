package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreInitializationController {

	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;

	@PutMapping("/init/register-volunteers")
	public void registerVolunteers() {
		coreVolunteerInitializationService.registerVolunteers();
	}
	
	@PutMapping("init/register-helpseekers")
	public void registerHelpSeekers() {
		coreHelpSeekerInitializationService.registerDefaultHelpSeekers();
	}
}
package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreInitializationController {

	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;

	@PutMapping("/init/register")
	public void registerVolunteers() {
		coreVolunteerInitializationService.registerVolunteers();
	}
}
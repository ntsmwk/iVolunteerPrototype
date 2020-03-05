package at.jku.cis.iVolunteer.api.reset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {

	@Autowired private ResetService resetService;

	@DeleteMapping("/reset")
	public void reset() {
		resetService.reset();
	}

	@PostMapping("/push-task-from-api")
	public void pushTaskFromAPI() {
		resetService.pushTaskFromAPI();
	}

	@PostMapping("/create-reset-state")
	public void createResetState() {
		resetService.createResetState();
	} 

}

package at.jku.cis.iVolunteer.marketplace.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeController {

	@Autowired private FakeService fakeService;

	@DeleteMapping("/reset")
	public void reset() {
		fakeService.reset();
	}

	@PostMapping("/push-task-from-api")
	public void pushTaskFromAPI() {
		fakeService.pushTaskFromAPI();
	}

	@PostMapping("/fahrtenspange-fake")
	public void addFahrtenspangeFake() {
		fakeService.addFahrtenspangeFake();
	}
	
	@PostMapping("/create-reset-state")
	public void createResetState() {
		fakeService.createResetState();
	}

}

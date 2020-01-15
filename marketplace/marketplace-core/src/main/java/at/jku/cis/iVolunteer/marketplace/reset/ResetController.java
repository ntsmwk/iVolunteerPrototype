package at.jku.cis.iVolunteer.marketplace.reset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {

	@Autowired private ResetService resetService;

	@DeleteMapping("/reset")
	public void reset() {
		resetService.reset();
	}
}

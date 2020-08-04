package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {

	private static final String VOLUNTEER_ID = "5f22ab8f2485e1326c9740d7";
	private static final String TENANT_ID = "5f22ab8f2485e1326c9740d1";
	@Autowired private MatchingService matchingService;
	
	
	@GetMapping("matching/test")
	public void testMatching() {
		
		matchingService.match(VOLUNTEER_ID, TENANT_ID);
	}
}

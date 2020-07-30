package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {

	private static final String TENANT_ID = "5f2128a5df0a59224ffca128";
	@Autowired private MatchingService matchingService;
	
	
	@GetMapping("matching/test")
	public void testMatching() {
		
		matchingService.match("asdfasf", TENANT_ID);
	}
}

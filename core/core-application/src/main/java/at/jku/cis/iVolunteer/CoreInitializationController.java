package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
public class CoreInitializationController {

	@Autowired CoreVolunteerService coreVolunteerService;
	@Autowired CoreLoginService coreLoginService;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;

	@PutMapping("/init/register")
	public void registerVolunteers() {
		coreVolunteerRepository.findAll().forEach(volunteer -> registerVolunteer(volunteer));
	}

	private void registerVolunteer(CoreVolunteer volunteer) {
		Marketplace mp = marketplaceRepository.findAll().stream().filter(m -> m.getName().equals("Marketplace 1"))
				.findFirst().orElse(null);
		if (mp != null) {
			try {
				coreVolunteerService.registerMarketplace(volunteer.getId(), mp.getId(), "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

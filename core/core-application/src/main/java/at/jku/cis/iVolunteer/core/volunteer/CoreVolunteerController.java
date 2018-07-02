package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController("/volunteer")
public class CoreVolunteerController {

	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	@GetMapping("/{coreVolunteerId}")
	public CoreVolunteer getCoreVolunteer(@PathVariable("coreVolunteerId") String coreVolunteerId) {
		return coreVolunteerRepository.findOne(coreVolunteerId);
	}

	@GetMapping("/{coreVolunteerId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("coreVolunteerId") String coreVolunteerId) {
		return coreVolunteerRepository.findOne(coreVolunteerId).getRegisteredMarketplaces();
	}
}

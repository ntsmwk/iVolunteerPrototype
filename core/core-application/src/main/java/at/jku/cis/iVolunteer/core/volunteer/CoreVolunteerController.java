package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/volunteer")
public class CoreVolunteerController {

	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreVolunteerService coreVolunteerService;

	@GetMapping("/all")
	public List<CoreVolunteer> getAllCoreVolunteers() {
		return this.coreVolunteerRepository.findAll();
	}

	@GetMapping("/{volunteerId}")
	public CoreVolunteer getCoreVolunteer(@PathVariable("volunteerId") String volunteerId) {
		return coreVolunteerRepository.findOne(volunteerId);
	}

	@GetMapping("/{volunteerId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("volunteerId") String volunteerId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(volunteerId);
		return volunteer.getRegisteredMarketplaces();
	}

	@PostMapping("/{coreVolunteerId}/register/{marketplaceId}/tenant/{tenantId}")
	public void registerMarketpace(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId, @RequestHeader("Authorization") String authorization) {
		coreVolunteerService.registerMarketplace(coreVolunteerId, marketplaceId,tenantId, authorization);
	}

}

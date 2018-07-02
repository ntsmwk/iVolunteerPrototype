package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/volunteer")
public class CoreVolunteerController {

	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@GetMapping("/{coreVolunteerId}")
	public CoreVolunteer getCoreVolunteer(@PathVariable("coreVolunteerId") String coreVolunteerId) {
		return coreVolunteerRepository.findOne(coreVolunteerId);
	}

	@GetMapping("/{coreVolunteerId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("coreVolunteerId") String coreVolunteerId) {
		return coreVolunteerRepository.findOne(coreVolunteerId).getRegisteredMarketplaces();
	}

	@PostMapping("/{coreVolunteerId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if(volunteer == null || marketplace == null) {
			throw new NotFoundException();
		}
		
		//TODO
//		volunteer.getRegisteredMarketplaces().add()

	}

}

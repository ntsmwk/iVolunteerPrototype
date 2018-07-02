package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.mapper.core.participant.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.participant.dto.CoreVolunteerDTO;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;

@RestController
@RequestMapping("/volunteer")
public class CoreVolunteerController {

	@Autowired
	private MarketplaceMapper marketplaceMapper;

	@Autowired
	private CoreVolunteerMapper coreVolunteerMapper;

	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@GetMapping("/{volunteerId}")
	public CoreVolunteerDTO getCoreVolunteer(@PathVariable("volunteerId") String volunteerId) {
		return coreVolunteerMapper.toDTO(coreVolunteerRepository.findOne(volunteerId));
	}

	@GetMapping("/{volunteerId}/marketplace")
	public List<MarketplaceDTO> getRegisteredMarketplaces(@PathVariable("volunteerId") String volunteerId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(volunteerId);
		return marketplaceMapper.toDTOs(volunteer.getRegisteredMarketplaces());
	}

	@PostMapping("/{coreVolunteerId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (volunteer == null || marketplace == null) {
			throw new NotFoundException();
		}

		// TODO
		// volunteer.getRegisteredMarketplaces().add()

	}

}

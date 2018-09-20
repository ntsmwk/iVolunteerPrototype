package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.mapper.core.user.CoreVolunteerMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreVolunteerDTO;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;
import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

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

	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{volunteerId}")
	public CoreVolunteerDTO getCoreVolunteer(@PathVariable("volunteerId") String volunteerId) {
		return coreVolunteerMapper.toDTO(coreVolunteerRepository.findOne(volunteerId));
	}

	@GetMapping("/{volunteerId}/marketplaces")
	public List<MarketplaceDTO> getRegisteredMarketplaces(@PathVariable("volunteerId") String volunteerId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(volunteerId);
		return marketplaceMapper.toDTOs(volunteer.getRegisteredMarketplaces());
	}
	
	@PostMapping("/{coreVolunteerId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (volunteer == null || marketplace == null) {
			throw new NotFoundException();
		}

		volunteer.getRegisteredMarketplaces().add(marketplace);
		CoreVolunteer coreVolunteer = coreVolunteerRepository.save(volunteer);

		VolunteerDTO volunteerDTO = new VolunteerDTO();
		volunteerDTO.setId(coreVolunteer.getId());
		volunteerDTO.setPassword(volunteer.getPassword());
		volunteerDTO.setUsername(volunteer.getUsername());
		coreMarketplaceRestClient.registerVolunteer(marketplace.getUrl(), authorization, volunteerDTO);
	}
}

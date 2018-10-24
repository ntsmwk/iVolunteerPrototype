package at.jku.cis.iVolunteer.core.helpseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreHelpSeekerDTO;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;
import at.jku.cis.iVolunteer.model.user.dto.HelpSeekerDTO;

@RestController
@RequestMapping("/helpseeker")
public class CoreHelpSeekerController {

	@Autowired private CoreHelpSeekerMapper coreHelpSeekerMapper;
	@Autowired private MarketplaceMapper marketplaceMapper;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreHelpSeekerId}")
	public CoreHelpSeekerDTO getCorehelpSeeker(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		return coreHelpSeekerMapper.toDTO(coreHelpSeekerRepository.findOne(coreHelpSeekerId));
	}

	@GetMapping("/{coreHelpSeekerId}/marketplace")
	public MarketplaceDTO getRegisteredMarketplaces(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		if(helpSeeker.getRegisteredMarketplaces().isEmpty()) {
			return null;
		}
		return marketplaceMapper.toDTO(helpSeeker.getRegisteredMarketplaces().get(0));
	}

	@PostMapping("/{coreHelpSeekerId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (helpSeeker == null || marketplace == null) {
			throw new NotFoundException();
		}

		helpSeeker.getRegisteredMarketplaces().add(marketplace);
		CoreHelpSeeker coreHelpSeeker = coreHelpSeekerRepository.save(helpSeeker);

		HelpSeekerDTO helpSeekerDTO = new HelpSeekerDTO();
		helpSeekerDTO.setId(coreHelpSeeker.getId());
		helpSeekerDTO.setPassword(helpSeeker.getPassword());
		helpSeekerDTO.setUsername(helpSeeker.getUsername());
		coreMarketplaceRestClient.registerHelpSeeker(marketplace.getUrl(), authorization, helpSeekerDTO);
	}

}
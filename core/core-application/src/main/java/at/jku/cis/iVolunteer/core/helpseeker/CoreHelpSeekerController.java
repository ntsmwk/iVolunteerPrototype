package at.jku.cis.iVolunteer.core.helpseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreHelpSeekerDTO;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;

@RestController
@RequestMapping("/helpseeker")
public class CoreHelpSeekerController {

	@Autowired private CoreHelpSeekerMapper coreHelpSeekerMapper;
	@Autowired private MarketplaceMapper marketplaceMapper;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;

	@GetMapping("/{coreHelpSeekerId}")
	public CoreHelpSeekerDTO getCorehelpSeeker(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		return coreHelpSeekerMapper.toDTO(coreHelpSeekerRepository.findOne(coreHelpSeekerId));
	}

	@GetMapping("/{coreHelpSeekerId}/marketplace")
	public MarketplaceDTO getRegisteredMarketplaces(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		CoreHelpSeeker volunteer = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		return marketplaceMapper.toDTO(volunteer.getRegisteredMarketplaces().get(0));
	}
}
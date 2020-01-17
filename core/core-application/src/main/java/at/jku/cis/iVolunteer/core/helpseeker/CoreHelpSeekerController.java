package at.jku.cis.iVolunteer.core.helpseeker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;

@RestController
@RequestMapping("/helpseeker")
public class CoreHelpSeekerController {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;
	
	@GetMapping("/all")
	public List<CoreHelpSeeker> getAllCoreVolunteers() {
		return this.coreHelpSeekerRepository.findAll();
	}
	
	@PutMapping("/find-by-ids")
	public List<CoreHelpSeeker> getAllCoreVolunteers(@RequestBody List<String> coreHelpseekerIds) {
		List<CoreHelpSeeker> coreHelpseekers = new ArrayList<>();

		coreHelpSeekerRepository.findAll(coreHelpseekerIds).forEach(coreHelpseekers::add);
		
		return coreHelpseekers;
	}

	@GetMapping("/{coreHelpSeekerId}")
	public CoreHelpSeeker getCorehelpSeeker(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		return coreHelpSeekerRepository.findOne(coreHelpSeekerId);
	}

	@GetMapping("/{coreHelpSeekerId}/marketplace")
	public Marketplace getRegisteredMarketplaces(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		if (helpSeeker.getRegisteredMarketplaces().isEmpty()) {
			return null;
		}
		return helpSeeker.getRegisteredMarketplaces().get(0);
	}

	@PostMapping("/{coreHelpSeekerId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreHelpSeeker coreHelpSeeker = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreHelpSeeker == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreHelpSeeker.getRegisteredMarketplaces().add(marketplace);
		coreHelpSeeker = coreHelpSeekerRepository.save(coreHelpSeeker);

		HelpSeeker helpSeeker = new HelpSeeker();
		helpSeeker.setId(coreHelpSeeker.getId());
		helpSeeker.setUsername(coreHelpSeeker.getUsername());
		helpSeeker.setFirstname(coreHelpSeeker.getFirstname());
		helpSeeker.setMiddlename(coreHelpSeeker.getMiddlename());
		helpSeeker.setPosition(coreHelpSeeker.getPosition());
		helpSeeker.setLastname(coreHelpSeeker.getLastname());
		helpSeeker.setNickname(coreHelpSeeker.getNickname());
		coreMarketplaceRestClient.registerHelpSeeker(marketplace.getUrl(), authorization, helpSeeker);
	}

}
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/helpseeker")
public class CoreHelpSeekerController {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;

	@Autowired private CoreHelpSeekerService coreHelpSeekerService;

	@GetMapping("/all")
	public List<CoreHelpSeeker> getAllCoreHelpSeekers(@RequestParam(value = "tId", required = false) String tenantId) {
		if (tenantId == null) {
			return this.coreHelpSeekerRepository.findAll();
		}
		return this.coreHelpSeekerService.getAllCoreHelpSeekers(tenantId);
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

	@PostMapping("/{coreHelpSeekerId}/register/{marketplaceId}/tenant/{tenantId}")
	public void registerMarketpace(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
			@RequestHeader("Authorization") String authorization) {

		coreHelpSeekerService.registerMarketplace(coreHelpSeekerId, marketplaceId, tenantId, authorization);

	}

}
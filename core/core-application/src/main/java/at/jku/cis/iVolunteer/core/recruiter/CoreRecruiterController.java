package at.jku.cis.iVolunteer.core.recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.Recruiter;

@RestController
@RequestMapping("/recruiter")
public class CoreRecruiterController {

	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreRecruiterId}")
	public CoreRecruiter getCorehelpSeeker(@PathVariable("coreRecruiterId") String coreRecruiterId) {
		return coreRecruiterRepository.findOne(coreRecruiterId);
	}

	@GetMapping("/{coreRecruiterId}/marketplace")
	public Marketplace getRegisteredMarketplaces(@PathVariable("coreRecruiterId") String coreRecruiterId) {
		CoreRecruiter coreRecruiter = coreRecruiterRepository.findOne(coreRecruiterId);
		if (coreRecruiter.getRegisteredMarketplaces().isEmpty()) {
			return null;
		}
		return coreRecruiter.getRegisteredMarketplaces().get(0);
	}

	@PostMapping("/{coreRecruiterId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreRecruiterId") String coreRecruiterId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreRecruiter coreRecruiter = coreRecruiterRepository.findOne(coreRecruiterId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreRecruiter == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreRecruiter.getRegisteredMarketplaces().add(marketplace);
		coreRecruiter = coreRecruiterRepository.save(coreRecruiter);

		Recruiter recruiter = new Recruiter();
		recruiter.setId(coreRecruiter.getId());
		recruiter.setPosition(coreRecruiter.getPosition());
		recruiter.setUsername(coreRecruiter.getUsername());
		recruiter.setFirstname(coreRecruiter.getFirstname());
		recruiter.setLastname(coreRecruiter.getLastname());
		recruiter.setMiddlename(coreRecruiter.getMiddlename());

		coreMarketplaceRestClient.registerRecruiter(marketplace.getUrl(), authorization, recruiter);
	}

}
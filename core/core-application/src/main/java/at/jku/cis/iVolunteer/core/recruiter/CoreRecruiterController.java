package at.jku.cis.iVolunteer.core.recruiter;

import java.util.ArrayList;
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
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;

@RestController
@RequestMapping("/recruiter")
public class CoreRecruiterController {

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreRecruiterId}")
	public CoreUser getCorehelpSeeker(@PathVariable("coreRecruiterId") String coreRecruiterId) {
		return coreUserRepository.findOne(coreRecruiterId);
	}

	@GetMapping("/{coreRecruiterId}/marketplace")
	public Marketplace getRegisteredMarketplaces(@PathVariable("coreRecruiterId") String coreRecruiterId) {
		CoreUser coreRecruiter = coreUserRepository.findOne(coreRecruiterId);
		if (coreRecruiter.getRegisteredMarketplaceIds().isEmpty()) {
			return null;
		}

		return this.marketplaceRepository.findOne(coreRecruiter.getRegisteredMarketplaceIds().get(0));
	}

	@PostMapping("/{coreRecruiterId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreRecruiterId") String coreRecruiterId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreUser coreRecruiter = coreUserRepository.findOne(coreRecruiterId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreRecruiter == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreRecruiter.getRegisteredMarketplaceIds().add(marketplace.getId());
		coreRecruiter = coreUserRepository.save(coreRecruiter);

		User recruiter = new User(coreRecruiter);
		// recruiter.setId(coreRecruiter.getId());
		// recruiter.setPosition(coreRecruiter.getPosition());
		// recruiter.setUsername(coreRecruiter.getUsername());
		// recruiter.setFirstname(coreRecruiter.getFirstname());
		// recruiter.setLastname(coreRecruiter.getLastname());
		// recruiter.setMiddlename(coreRecruiter.getMiddlename());

		coreMarketplaceRestClient.registerUser(marketplace.getUrl(), authorization, recruiter);
	}

}
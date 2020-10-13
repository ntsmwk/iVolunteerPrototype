package at.jku.cis.iVolunteer.core.flexprod;

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
@RequestMapping("/flexprod")
public class CoreFlexProdController {

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreFlexProdId}")
	public CoreUser getCorehelpSeeker(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		return coreUserRepository.findOne(coreFlexProdId);
	}

	@GetMapping("/{coreFlexProdId}/marketplace")
	public Marketplace getRegisteredMarketplaces(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		CoreUser flexProdUser = coreUserRepository.findOne(coreFlexProdId);
		if (flexProdUser.getRegisteredMarketplaceIds().isEmpty()) {
			return null;
		}

		return this.marketplaceRepository.findOne(flexProdUser.getRegisteredMarketplaceIds().get(0));
	}

	@PostMapping("/{coreFlexProdId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreFlexProdId") String coreFlexProdId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreUser coreFlexProdUser = coreUserRepository.findOne(coreFlexProdId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreFlexProdUser == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreFlexProdUser.getRegisteredMarketplaceIds().add(marketplaceId);
		coreFlexProdUser = coreUserRepository.save(coreFlexProdUser);

		User flexProdUser = new User();
		flexProdUser.setId(coreFlexProdUser.getId());
		flexProdUser.setUsername(flexProdUser.getUsername());
		coreMarketplaceRestClient.registerOrUpdateMarketplaceUser(marketplace.getUrl(), authorization, flexProdUser);
	}

}
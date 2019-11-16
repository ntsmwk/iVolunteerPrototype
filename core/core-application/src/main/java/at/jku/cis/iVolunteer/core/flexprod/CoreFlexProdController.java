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
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.FlexProd;

@RestController
@RequestMapping("/flexprod")
public class CoreFlexProdController {

	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreFlexProdId}")
	public CoreFlexProd getCorehelpSeeker(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		return coreFlexProdRepository.findOne(coreFlexProdId);
	}

	@GetMapping("/{coreFlexProdId}/marketplace")
	public Marketplace getRegisteredMarketplaces(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		CoreFlexProd flexProdUser = coreFlexProdRepository.findOne(coreFlexProdId);
		if (flexProdUser.getRegisteredMarketplaces().isEmpty()) {
			return null;
		}
		return flexProdUser.getRegisteredMarketplaces().get(0);
	}

	@PostMapping("/{coreFlexProdId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreFlexProdId") String coreFlexProdId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreFlexProd coreFlexProdUser = coreFlexProdRepository.findOne(coreFlexProdId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreFlexProdUser == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreFlexProdUser.getRegisteredMarketplaces().add(marketplace);
		coreFlexProdUser = coreFlexProdRepository.save(coreFlexProdUser);

		FlexProd flexProdUser = new FlexProd();
		flexProdUser.setId(coreFlexProdUser.getId());
		flexProdUser.setUsername(flexProdUser.getUsername());
		coreMarketplaceRestClient.registerFlexProdUser(marketplace.getUrl(), authorization, flexProdUser);
	}

}
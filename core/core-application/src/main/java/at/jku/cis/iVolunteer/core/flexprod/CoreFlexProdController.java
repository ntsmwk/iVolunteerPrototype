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
import at.jku.cis.iVolunteer.mapper.core.user.CoreFlexProdMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreFlexProdDTO;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;
import at.jku.cis.iVolunteer.model.user.dto.FlexProdDTO;


@RestController
@RequestMapping("/flexprod")
public class CoreFlexProdController {

	@Autowired private CoreFlexProdMapper coreHelpSeekerMapper;
	@Autowired private MarketplaceMapper marketplaceMapper;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@GetMapping("/{coreFlexProdId}")
	public CoreFlexProdDTO getCorehelpSeeker(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		return coreHelpSeekerMapper.toDTO(coreFlexProdRepository.findOne(coreFlexProdId));
	}

	@GetMapping("/{coreFlexProdId}/marketplace")
	public MarketplaceDTO getRegisteredMarketplaces(@PathVariable("coreFlexProdId") String coreFlexProdId) {
		CoreFlexProd flexProdUser = coreFlexProdRepository.findOne(coreFlexProdId);
		if(flexProdUser.getRegisteredMarketplaces().isEmpty()) {
			return null;
		}
		return marketplaceMapper.toDTO(flexProdUser.getRegisteredMarketplaces().get(0));
	}

	@PostMapping("/{coreFlexProdId}/register/{marketplaceId}")
	public void registerMarketpace(@PathVariable("coreFlexProdId") String coreFlexProdId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		CoreFlexProd flexProdUser = coreFlexProdRepository.findOne(coreFlexProdId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (flexProdUser == null || marketplace == null) {
			throw new NotFoundException();
		}

		flexProdUser.getRegisteredMarketplaces().add(marketplace);
		CoreFlexProd coreFlexProdUser = coreFlexProdRepository.save(flexProdUser);

		FlexProdDTO flexProdUserDTO = new FlexProdDTO();
		flexProdUserDTO.setId(coreFlexProdUser.getId());
		flexProdUserDTO.setPassword(flexProdUser.getPassword());
		flexProdUserDTO.setUsername(flexProdUser.getUsername());
		coreMarketplaceRestClient.registerFlexProdUser(marketplace.getUrl(), authorization, flexProdUserDTO);
	}

}
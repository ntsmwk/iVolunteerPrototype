package at.jku.cis.iVolunteer.core.helpseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;

@Service
public class CoreHelpSeekerService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	public void registerMarketplace(String coreHelpSeekerId, String marketplaceId, String tenantId,
			String authorization) {
		CoreHelpSeeker coreHelpSeeker = coreHelpSeekerRepository.findOne(coreHelpSeekerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreHelpSeeker == null || marketplace == null) {
			throw new NotFoundException();
		}
		coreHelpSeeker = updateCoreHelpSeeker(tenantId, coreHelpSeeker, marketplace);
		sendRegistrationToMarketplace(authorization, coreHelpSeeker, marketplace);
	}

	private void sendRegistrationToMarketplace(String authorization, CoreHelpSeeker coreHelpSeeker,
			Marketplace marketplace) {
		HelpSeeker helpSeeker = new HelpSeeker();
		helpSeeker.setId(coreHelpSeeker.getId());
		helpSeeker.setTenantId(coreHelpSeeker.getTenantId());
		helpSeeker.setUsername(coreHelpSeeker.getUsername());
		helpSeeker.setFirstname(coreHelpSeeker.getFirstname());
		helpSeeker.setMiddlename(coreHelpSeeker.getMiddlename());
		helpSeeker.setPosition(coreHelpSeeker.getPosition());
		helpSeeker.setLastname(coreHelpSeeker.getLastname());
		helpSeeker.setNickname(coreHelpSeeker.getNickname());
		helpSeeker.setImage(coreHelpSeeker.getImage());
		
		coreMarketplaceRestClient.registerHelpSeeker(marketplace.getUrl(), authorization, helpSeeker);
	}

	private CoreHelpSeeker updateCoreHelpSeeker(String tenantId, CoreHelpSeeker coreHelpSeeker,
			Marketplace marketplace) {
		coreHelpSeeker.getRegisteredMarketplaces().add(marketplace);
		coreHelpSeeker.setTenantId(tenantId);
		coreHelpSeeker = coreHelpSeekerRepository.save(coreHelpSeeker);
		return coreHelpSeeker;
	}
}

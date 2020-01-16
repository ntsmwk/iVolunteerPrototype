package at.jku.cis.iVolunteer.core.volunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class CoreVolunteerService {
	
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	public void registerMarketplace(String coreVolunteerId, String marketplaceId, String authorization) {
		CoreVolunteer coreVolunteer = coreVolunteerRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreVolunteer == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreVolunteer = updateCoreVolunteer(coreVolunteer, marketplace);
		registerVolunteer(authorization, coreVolunteer, marketplace);
	}
	

	private CoreVolunteer updateCoreVolunteer(CoreVolunteer coreVolunteer, Marketplace marketplace) {
		coreVolunteer.getRegisteredMarketplaces().add(marketplace);
		coreVolunteer = coreVolunteerRepository.save(coreVolunteer);
		return coreVolunteer;
	}

	
	private void registerVolunteer(String authorization, CoreVolunteer coreVolunteer, Marketplace marketplace) {
		Volunteer volunteer = new Volunteer();
		volunteer.setId(coreVolunteer.getId());
		volunteer.setUsername(coreVolunteer.getUsername());
		volunteer.setFirstname(coreVolunteer.getFirstname());
		volunteer.setLastname(coreVolunteer.getLastname());
		volunteer.setMiddlename(coreVolunteer.getMiddlename());
		volunteer.setPosition(coreVolunteer.getPosition());
		volunteer.setNickname(coreVolunteer.getNickname());
		volunteer.setProfileImagePath(coreVolunteer.getProfileImagePath());
		coreMarketplaceRestClient.registerVolunteer(marketplace.getUrl(), authorization, volunteer);
	}

}

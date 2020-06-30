package at.jku.cis.iVolunteer.core.helpseeker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreHelpSeekerService {

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	public void registerMarketplace(String coreHelpSeekerId, String marketplaceId, String tenantId,
			String authorization) {
		CoreUser coreHelpSeeker = coreUserRepository.findOne(coreHelpSeekerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreHelpSeeker == null || marketplace == null) {
			throw new NotFoundException();
		}
		coreHelpSeeker = updateCoreHelpSeeker(tenantId, coreHelpSeeker, marketplace);
		sendRegistrationToMarketplace(authorization, coreHelpSeeker, marketplace);
	}

	private void sendRegistrationToMarketplace(String authorization, CoreUser coreHelpSeeker, Marketplace marketplace) {
		User helpSeeker = new User();
		helpSeeker.setId(coreHelpSeeker.getId());
		helpSeeker.setSubscribedTenants(coreHelpSeeker.getSubscribedTenants());
		helpSeeker.setUsername(coreHelpSeeker.getUsername());
		helpSeeker.setFirstname(coreHelpSeeker.getFirstname());
		helpSeeker.setMiddlename(coreHelpSeeker.getMiddlename());
		helpSeeker.setPosition(coreHelpSeeker.getPosition());
		helpSeeker.setLastname(coreHelpSeeker.getLastname());
		helpSeeker.setNickname(coreHelpSeeker.getNickname());
		helpSeeker.setImage(coreHelpSeeker.getImage());

		coreMarketplaceRestClient.registerUser(marketplace.getUrl(), authorization, helpSeeker);
	}

	private CoreUser updateCoreHelpSeeker(String tenantId, CoreUser coreHelpSeeker, Marketplace marketplace) {
		coreHelpSeeker.getRegisteredMarketplaces().add(marketplace);
		coreHelpSeeker.setSubscribedTenants(
				Collections.singletonList(new TenantUserSubscription(tenantId, UserRole.HELP_SEEKER)));

		coreHelpSeeker = coreUserRepository.save(coreHelpSeeker);
		return coreHelpSeeker;
	}

	public List<CoreUser> getAllCoreHelpSeekers(String tenantId) {
		List<CoreUser> users = this.coreUserRepository.findAll();

		List<CoreUser> helpSeekers = new ArrayList<>();
		users.forEach(u -> {
			if (u.getSubscribedTenants().stream().map(t -> t.getRole()).equals(UserRole.HELP_SEEKER)) {
				helpSeekers.add(u);
			}
			;
		});
		return helpSeekers;
	}
}

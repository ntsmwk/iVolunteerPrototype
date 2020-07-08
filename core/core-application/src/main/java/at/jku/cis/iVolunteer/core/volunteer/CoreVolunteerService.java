package at.jku.cis.iVolunteer.core.volunteer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreVolunteerService {

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	public void subscribeTenant(String coreVolunteerId, String marketplaceId, String tenantId, String authorization) {
		this.subscribeTenant(coreVolunteerId, marketplaceId, Collections.singletonList(tenantId), authorization);
	}

	public void subscribeTenant(String coreVolunteerId, String marketplaceId, List<String> tenantIds,
			String authorization) {
		CoreUser coreVolunteer = coreUserRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);

		List<Tenant> tenants = new ArrayList<>();
		tenantIds.forEach(id -> {
			tenants.add(this.tenantRepository.findOne(id));
		});
		if (coreVolunteer == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreVolunteer = updateCoreVolunteer(coreVolunteer, marketplace, tenants);
		registerOrUpdateVolunteer(authorization, coreVolunteer, marketplace);
	}

	private CoreUser updateCoreVolunteer(CoreUser coreVolunteer, Marketplace marketplace, List<Tenant> tenants) {
		if (!coreVolunteer.getRegisteredMarketplaces().contains(marketplace)) {
			coreVolunteer.getRegisteredMarketplaces().add(marketplace);
		}

		List<TenantUserSubscription> newSubscriptions = new ArrayList<>();
		tenants.forEach(t -> {
			newSubscriptions.add(new TenantUserSubscription(marketplace.getId(), t.getId(), UserRole.VOLUNTEER));
		});
		coreVolunteer.getSubscribedTenants().addAll(newSubscriptions);

		return coreUserRepository.save(coreVolunteer);
	}

	private void registerOrUpdateVolunteer(String authorization, CoreUser coreVolunteer, Marketplace marketplace) {
		User volunteer = new User(coreVolunteer);
		// volunteer.setId(coreVolunteer.getId());
		// volunteer.setSubscribedTenants(coreVolunteer.getSubscribedTenants());
		// volunteer.setUsername(coreVolunteer.getUsername());
		// volunteer.setFirstname(coreVolunteer.getFirstname());
		// volunteer.setLastname(coreVolunteer.getLastname());
		// volunteer.setMiddlename(coreVolunteer.getMiddlename());
		// volunteer.setBirthday(coreVolunteer.getBirthday());
		// volunteer.setPosition(coreVolunteer.getPosition());
		// volunteer.setNickname(coreVolunteer.getNickname());
		// if (coreVolunteer.getImage() != null) {
		// volunteer.setImage(coreVolunteer.getImage());
		// }
		coreMarketplaceRestClient.registerUser(marketplace.getUrl(), authorization, volunteer);
	}

	public void unsubscribeTenant(String coreVolunteerId, String marketplaceId, String tenantId, String authorization) {
		CoreUser coreVolunteer = coreUserRepository.findOne(coreVolunteerId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (coreVolunteer == null || marketplace == null) {
			throw new NotFoundException();
		}
		coreVolunteer.removeSubscribedTenant(marketplaceId, tenantId);
		coreUserRepository.save(coreVolunteer);

		// TODO MWE remove tenant from MP db..

	}
}

package at.jku.cis.iVolunteer.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer._mappers.xnet.XTenantMapper;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	CoreUserService coreUserService;
	@Autowired
	XTenantMapper xTenantMapper;

	public List<Tenant> getTenantsByUser(String userId) {
		CoreUser user = coreUserService.findById(userId);
		if (user != null) {
			return user.getSubscribedTenants().stream().map(t -> t.getTenantId())
					.map(tId -> tenantRepository.findOne(tId)).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public List<Tenant> getAllTenants() {
		return tenantRepository.findAll();
	}

	public String getTenantIdByName(String tenantName) {
		return tenantRepository.findByName(tenantName).getId();
	}

	public Tenant getTenantByName(String tenantName) {
		return tenantRepository.findByName(tenantName);
	}

	public Tenant getTenantById(String tenantId) {
		return tenantRepository.findOne(tenantId);
	}

	public List<Tenant> getTenantsByMarketplaceIds(String marketplaceId) {
		return tenantRepository.findByMarketplaceId(marketplaceId);
	}

	public Tenant createTenant(Tenant tenant) {
		return tenantRepository.save(tenant);
	}

	public Tenant updateTenant(Tenant tenant) {
		return tenantRepository.save(tenant);
	}

	public List<Tenant> getSubscribedTenants(CoreUser user) {
		List<String> tenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER)).map(t -> t.getTenantId())
				.collect(Collectors.toList());

		List<Tenant> ret = new ArrayList<>();
		tenantRepository.findAll(tenantIds).forEach(ret::add);
		return ret;
	}

	public List<Tenant> getUnsubscribedTenants(CoreUser user) {
		List<String> subscribedTenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER)).map(t -> t.getTenantId())
				.collect(Collectors.toList());

		List<Tenant> ret = getAllTenants().stream()
				.filter(t -> subscribedTenantIds.stream().noneMatch(id -> t.getId().equals(id)))
				.collect(Collectors.toList());
		return ret;
	}

	public List<CoreUser> getSubscribedUsers(String tenantId) {
		List<CoreUser> users = coreUserService.findAll();
		List<CoreUser> ret = users.stream().filter(u -> {
			TenantSubscription tenantSubscription = u.getSubscribedTenants().stream()
					.filter(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == UserRole.VOLUNTEER).findAny()
					.orElse(null);
			return tenantSubscription != null;
		}).collect(Collectors.toList());

		return ret;
	}

	public List<XTenant> toXTenantTargets(List<Tenant> tenants) {
		List<XTenant> ret = new ArrayList<>();
		for (Tenant t : tenants) {

			List<CoreUser> users = getSubscribedUsers(t.getId());
			ret.add(xTenantMapper.toTarget(t, users));
		}
		return ret;
	}

}

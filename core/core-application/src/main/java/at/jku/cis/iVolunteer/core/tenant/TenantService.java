package at.jku.cis.iVolunteer.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private CoreUserRepository userRepository;

	public List<Tenant> getTenantsByUser(String userId) {
		CoreUser user = userRepository.findOne(userId);
		if (user != null) {
			return user.getSubscribedTenants().stream().map(t -> t.getTenant()).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public List<Tenant> getAllTenants() {
		return tenantRepository.findAll();
	}

	public String getTenantByName(String tenantName) {
		return tenantRepository.findByName(tenantName).getId();
	}

	public Tenant getTenantById(String tenantId) {
		return tenantRepository.findOne(tenantId);
	}

	public List<Tenant> getTenantsByMarketplaceIds(String marketplaceId) {
		return tenantRepository.findByMarketplaceId(marketplaceId);
	}

	public Tenant createTenant(Tenant tenant) {
		return tenantRepository.insert(tenant);
	}

	public Tenant updateTenant(String tenantId, @RequestBody Tenant tenant) {
		Tenant orginalTenant = tenantRepository.findOne(tenantId);
		if (orginalTenant == null) {
			throw new NotAcceptableException();
		}
		orginalTenant.setName(tenant.getName());
		orginalTenant.setPrimaryColor(tenant.getPrimaryColor());
		orginalTenant.setSecondaryColor(tenant.getSecondaryColor());
		orginalTenant.setMarketplaceId(tenant.getMarketplaceId());
		return tenantRepository.save(orginalTenant);
	}

}

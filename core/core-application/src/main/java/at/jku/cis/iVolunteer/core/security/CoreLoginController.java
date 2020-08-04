package at.jku.cis.iVolunteer.core.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.global.GlobalInfo;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@RestController
@RequestMapping("/login")
public class CoreLoginController {
	@Autowired private TenantService tenantService;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreLoginService loginService;

	@GetMapping
	public CoreUser getLoggedInUser() {
		final CoreUser user = loginService.getLoggedInUser();

		return user;
	}

	@PutMapping("/globalInfo/role/{role}")
	public GlobalInfo getGlobalInfo(@PathVariable("role") UserRole role, @RequestBody List<String> tenantIds) {
		GlobalInfo globalInfo = new GlobalInfo();

		CoreUser user = loginService.getLoggedInUser();

		globalInfo.setUser(user);
		globalInfo.setUserRole(role);

		final List<String> registeredMarketplaceIds = user.getRegisteredMarketplaceIds();
		if (registeredMarketplaceIds.size() > 0) {
			globalInfo.setMarketplace(this.marketplaceRepository.findOne(registeredMarketplaceIds.get(0)));
		}

		globalInfo.setTenants(
				tenantIds.stream().map(id -> this.tenantService.getTenantById(id)).collect(Collectors.toList()));

		return globalInfo;
	}
}

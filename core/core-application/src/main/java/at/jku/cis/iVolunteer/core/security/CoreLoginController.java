package at.jku.cis.iVolunteer.core.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreLoginService loginService;

	@GetMapping
	public CoreUser getLoggedInUser() {
		final CoreUser user = loginService.getLoggedInUser();

		return user;
	}

	@GetMapping("role")
	public UserRole getLoggedInRole() {
		return loginService.getLoggedInUserRole();
	}

	@GetMapping("/globalInfo")
	public GlobalInfo getGlobalInfo() {
		final GlobalInfo globalInfo = new GlobalInfo();

		globalInfo.setUser(loginService.getLoggedInUser());
		globalInfo.setUserRole(loginService.getLoggedInUserRole());

		final CoreUser coreUser = (CoreUser) globalInfo.getUser();

		final List<String> registeredMarketplaceIds = coreUser.getRegisteredMarketplaceIds();
		if (registeredMarketplaceIds.size() > 0) {
			globalInfo.setMarketplace(this.marketplaceRepository.findOne(registeredMarketplaceIds.get(0)));
		}

		globalInfo.setTenants(this.tenantService.getTenantsByUser(coreUser.getId()));

		return globalInfo;
	}
}

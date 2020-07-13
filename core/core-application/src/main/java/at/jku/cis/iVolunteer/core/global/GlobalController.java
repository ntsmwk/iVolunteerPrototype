package at.jku.cis.iVolunteer.core.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
public class GlobalController {

	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@GetMapping("/global")
	public GlobalInfo getGlobalInfo() {
		GlobalInfo globalInfo = new GlobalInfo();

		globalInfo.setUser(loginService.getLoggedInUser());
		globalInfo.setUserRole(loginService.getLoggedInUserRole());

		CoreUser coreUser = (CoreUser) globalInfo.getUser();

		List<String> registeredMarketplaceIds = coreUser.getRegisteredMarketplaceIds();
		if (registeredMarketplaceIds.size() > 0) {
			globalInfo.setMarketplace(this.marketplaceRepository.findOne(registeredMarketplaceIds.get(0)));
		}

		globalInfo.setTenants(this.tenantService.getTenantsByUser(coreUser.getId()));

		return globalInfo;
	}

}

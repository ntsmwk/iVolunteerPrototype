package at.jku.cis.iVolunteer.core.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/global")
	public GlobalInfo getGlobalInfo() {
		GlobalInfo globalInfo = new GlobalInfo();

		globalInfo.setParticipant(loginService.getLoggedInUser());
		globalInfo.setParticipantRole(loginService.getLoggedInParticipantRole());

		CoreUser coreUser = (CoreUser) globalInfo.getParticipant();
		List<Marketplace> registeredMarketplaces = coreUser.getRegisteredMarketplaces();
		if (registeredMarketplaces.size() > 0) {
			globalInfo.setMarketplace(registeredMarketplaces.get(0));
		}
		globalInfo.setTenants(this.tenantService.getTenantsByUser(coreUser.getId()));

		return globalInfo;
	}

}

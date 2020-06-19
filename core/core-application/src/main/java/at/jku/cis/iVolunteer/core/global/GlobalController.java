package at.jku.cis.iVolunteer.core.global;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.security.ParticipantRole;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
public class GlobalController {

	@Autowired private CoreLoginService loginService;
	@Autowired private TenantService tenantService;

	@GetMapping("/global")
	public GlobalInfo getGlobalInfo() {
		GlobalInfo globalInfo = new GlobalInfo();

		globalInfo.setParticipant(loginService.getLoggedInParticipant());
		globalInfo.setParticipantRole(loginService.getLoggedInParticipantRole());

		Marketplace marketplace = null;
		List<Tenant> tenants = new ArrayList<>();

		
		switch (globalInfo.getParticipantRole()) {
		case VOLUNTEER:
			setVolunteerGlobalInfo(globalInfo, marketplace, tenants);
			break;
		case HELP_SEEKER:
			setHelpSeekerGlobalInfo(globalInfo, marketplace, tenants);
			break;
		}

		globalInfo.setTenants(tenants);
		globalInfo.setMarketplace(marketplace);

		return globalInfo;
	}

	private void setVolunteerGlobalInfo(GlobalInfo globalInfo, Marketplace marketplace, List<Tenant> tenants) {
		CoreVolunteer coreVolunteer = (CoreVolunteer) globalInfo.getParticipant();
		List<Marketplace> registeredMarketplaces = coreVolunteer.getRegisteredMarketplaces();
		if (registeredMarketplaces.size() > 0) {
			marketplace = registeredMarketplaces.get(0);
		}
		tenants = this.tenantService.getTenantsByVolunteer(globalInfo.getParticipant().getId());
	}

	private void setHelpSeekerGlobalInfo(GlobalInfo globalInfo, Marketplace marketplace, List<Tenant> tenants) {
		CoreHelpSeeker coreHelpSeeker = (CoreHelpSeeker) globalInfo.getParticipant();
		List<Marketplace> registeredMarketplaces = coreHelpSeeker.getRegisteredMarketplaces();
		if (registeredMarketplaces.size() > 0) {
			marketplace = registeredMarketplaces.get(0);
		}
		Tenant tenant = this.tenantService.getTenantById(coreHelpSeeker.getTenantId());
		tenants.add(tenant);
	}

}

package at.jku.cis.iVolunteer.core.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
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

		switch (globalInfo.getParticipantRole()) {
		case VOLUNTEER:
			setVolunteerGlobalInfo(globalInfo);
			break;
		case HELP_SEEKER:
			setHelpSeekerGlobalInfo(globalInfo);
			break;
		}

		return globalInfo;
	}

	private void setVolunteerGlobalInfo(GlobalInfo globalInfo) {
		CoreVolunteer coreVolunteer = (CoreVolunteer) globalInfo.getParticipant();
		List<Marketplace> registeredMarketplaces = coreVolunteer.getRegisteredMarketplaces();
		if (registeredMarketplaces.size() > 0) {
			globalInfo.setMarketplace(registeredMarketplaces.get(0));
		}
		globalInfo.setTenants(this.tenantService.getTenantsByVolunteer(globalInfo.getParticipant().getId()));
	}

	private void setHelpSeekerGlobalInfo(GlobalInfo globalInfo) {
		CoreHelpSeeker coreHelpSeeker = (CoreHelpSeeker) globalInfo.getParticipant();
		List<Marketplace> registeredMarketplaces = coreHelpSeeker.getRegisteredMarketplaces();
		if (registeredMarketplaces.size() > 0) {
			globalInfo.setMarketplace(registeredMarketplaces.get(0));
		}

		globalInfo.getTenants().add(this.tenantService.getTenantById(coreHelpSeeker.getTenantId()));
	}

}

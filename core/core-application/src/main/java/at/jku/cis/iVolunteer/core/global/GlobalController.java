package at.jku.cis.iVolunteer.core.global;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerService;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.security.ParticipantRole;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
public class GlobalController {

	@Autowired private CoreLoginService loginService;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private CoreHelpSeekerService coreHelpSeekerService;
	@Autowired private CoreVolunteerService coreVolunteerService;

	@GetMapping("/global")
	public GlobalInfo getGlobalInfo() {
		GlobalInfo globalInfo = new GlobalInfo();

		globalInfo.setParticipant(loginService.getLoggedInParticipant());
		globalInfo.setParticipantRole(loginService.getLoggedInParticipantRole());

//		 if (this.globalInfo.participantRole === "HELP_SEEKER") {
//		      this.globalInfo.marketplace = <Marketplace>(
//		        await this.coreHelpseekerService
//		          .findRegisteredMarketplaces(this.globalInfo.participant.id)
//		          .toPromise()
//		      );
//		    } else if (this.globalInfo.participantRole === "VOLUNTEER") {
//		      let marketplaces = [];
//		      marketplaces = <Marketplace[]>(
//		        await this.coreVolunteerService
//		          .findRegisteredMarketplaces(this.globalInfo.participant.id)
//		          .toPromise()
//		      );
//		      this.globalInfo.marketplace = marketplaces[0];
//		    }
//		
		Marketplace marketplace = null;
		List<Tenant> tenants = new ArrayList<>();

		if (this.getGlobalInfo().getParticipantRole() == ParticipantRole.VOLUNTEER) {
			List<Marketplace> registeredMarketplaces = ((CoreVolunteer) globalInfo.getParticipant())
					.getRegisteredMarketplaces();
			if (registeredMarketplaces.size() > 0) {
				globalInfo.setMarketplace(registeredMarketplaces.get(0));
			}
			
			
			
		} else if (this.getGlobalInfo().getParticipantRole() == ParticipantRole.HELP_SEEKER) {
			List<Marketplace> registeredMarketplaces = ((CoreHelpSeeker) globalInfo.getParticipant())
					.getRegisteredMarketplaces();
			if (registeredMarketplaces.size() > 0) {
				globalInfo.setMarketplace(registeredMarketplaces.get(0));
			}
		}

		globalInfo.setTenants(tenants);
		globalInfo.setMarketplace(marketplace);

		return globalInfo;
	}

}

package at.jku.cis.iVolunteer.initialize;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerService;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreHelpSeekerInitializationService {

	private static final String USER_MV = "MVS";
	private static final String TENANT_MV = "MV Schwertberg";

	private static final String USER_FF = "FFA";
	private static final String TENANT_FF = "FF Eidenberg";

	private static final String USER_RK = "OERK";
	private static final String TENANT_RK = "RK Wilhering";

	private static final String MMUSTERMANN = "mmustermann";
	private static final String RAW_PASSWORD = "passme";

	private static final String FFEIDENBERG = TENANT_FF;
	private static final String MUSIKVEREINSCHWERTBERG = TENANT_MV;
	private static final String RKWILHERING = "RK Wilhering";

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private TenantRepository coreTenantRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CoreHelpSeekerService coreHelpSeekerService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreUserService coreUserService;

	public void initHelpSeekers() {
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD, "M", "Mustermann", "", "HelpSeeker");
		createHelpSeeker(USER_FF, "passme", "Wolfgang", "Kronsteiner", "", "Feuerwehr Kommandant");
		createHelpSeeker("OERK", "passme", "Sandra", "Horvatis", "Rotes Kreuz", "Freiwilligenmanagement");
		createHelpSeeker(USER_MV, "passme", "Johannes", "Schönböck", "", "Musikverein Obmann");
		// TODO temp
		subscribeDefaultHelpseekersToTenants();
	}

	private CoreUser createHelpSeeker(String username, String password, String firstName, String lastName,
			String nickName, String position) {
		CoreUser helpSeeker = coreUserRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreUser();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker.setId(username);
			helpSeeker.setFirstname(firstName);
			helpSeeker.setLastname(lastName);
			helpSeeker.setNickname(nickName);
			helpSeeker.setPosition(position);

			helpSeeker = coreUserRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}

	public void subscribeDefaultHelpseekersToTenants() {
		String tenantIdFF = coreTenantRepository.findByName(FFEIDENBERG).getId();
		CoreUser ffUser = coreUserRepository.findOne(USER_FF);
		String tenantIdRK = coreTenantRepository.findByName(RKWILHERING).getId();
		CoreUser rkUser = coreUserRepository.findOne(USER_RK);
		String tenantIdMV = coreTenantRepository.findByName(MUSIKVEREINSCHWERTBERG).getId();
		CoreUser mvUser = coreUserRepository.findOne(USER_MV);

		// TODO: needs change later on, works for now, since there is only one
		// marketplace
		Marketplace mp = this.marketplaceRepository
				.findOne(ffUser.getRegisteredMarketplaceIds().stream().findFirst().orElse(null));

		ffUser.setSubscribedTenants(
				Collections.singletonList(new TenantUserSubscription(mp.getId(), tenantIdFF, UserRole.HELP_SEEKER)));
		rkUser.setSubscribedTenants(
				Collections.singletonList(new TenantUserSubscription(mp.getId(), tenantIdRK, UserRole.HELP_SEEKER)));
		mvUser.setSubscribedTenants(
				Collections.singletonList(new TenantUserSubscription(mp.getId(), tenantIdMV, UserRole.HELP_SEEKER)));

		coreUserRepository.save(ffUser);
		coreUserRepository.save(rkUser);
		coreUserRepository.save(mvUser);

	}

	public void registerDefaultHelpSeekers() {
		registerDefaultHelpSeeker(USER_FF, TENANT_FF);
		registerDefaultHelpSeeker(USER_MV, TENANT_MV);
		registerDefaultHelpSeeker(USER_RK, TENANT_RK);
	}

	private void registerDefaultHelpSeeker(String helpSeekerUser, String tenantName) {
		List<CoreUser> helpSeekers = this.coreUserService.getCoreUsersByRole(UserRole.HELP_SEEKER);
		List<Tenant> tenants = coreTenantRepository.findAll();

		CoreUser MV_helpSeeker = helpSeekers.stream().filter(helpSeeker -> helpSeeker.getId().equals(helpSeekerUser))
				.findFirst().orElse(null);
		Tenant MV_tenant = tenants.stream().filter(tenant -> tenant.getName().equals(tenantName)).findFirst()
				.orElse(null);
		if (MV_helpSeeker != null && MV_tenant != null) {
			this.registerHelpSeeker(MV_helpSeeker, MV_tenant.getId());
		}
	}

	private void registerHelpSeeker(CoreUser helpSeeker, String tenantId) {
		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");

		if (mp != null) {
			try {
				coreHelpSeekerService.registerMarketplace(helpSeeker.getId(), mp.getId(), tenantId, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

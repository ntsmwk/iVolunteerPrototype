package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.registration.AccountType;
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

	private static final String TENANT_ADMIN_FF = "FFA_ADMIN";
	private static final String TENANT_ADMIN_MV = "MVS_ADMIN";
	private static final String TENANT_ADMIN_RK = "OERK_ADMIN";

	private static final String TENANT_FLEXPROD = "FlexProd";
	private static final String TENANT_ADMIN_FLEXPROD = "FLEXPROD_ADMIN";

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private TenantRepository coreTenantRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreUserService coreUserService;
	@Autowired
	TenantService tenantService;

	public void initHelpSeekers() {
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD, "M", "Mustermann", "", "HelpSeeker");
		createHelpSeeker(USER_FF, "passme", "Wolfgang", "Kronsteiner", "", "Feuerwehr Kommandant");
		createHelpSeeker("OERK", "passme", "Sandra", "Horvatis", "Rotes Kreuz", "Freiwilligenmanagement");
		createHelpSeeker(USER_MV, "passme", "Johannes", "Schönböck", "", "Musikverein Obmann");
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
			helpSeeker.setOrganizationPosition(position);
			helpSeeker.setActivated(true);
			helpSeeker.setAccountType(AccountType.PERSON);
			// helpSeeker = coreUserRepository.insert(helpSeeker);
			coreUserService.addNewUser(helpSeeker, "", false);
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

		// TODO: needs change later on, works for now,
		// since there is only one marketplace
		Marketplace mp = this.marketplaceRepository.findByName("Marketplace 1");

		coreUserService.subscribeUserToTenant(USER_FF, mp.getId(), tenantIdFF, UserRole.HELP_SEEKER, "", false);
		coreUserService.subscribeUserToTenant(USER_RK, mp.getId(), tenantIdRK, UserRole.HELP_SEEKER, "", false);
		coreUserService.subscribeUserToTenant(USER_MV, mp.getId(), tenantIdMV, UserRole.HELP_SEEKER, "", false);

	}

	public void registerDefaultHelpSeekers() {
		registerDefaultHelpSeeker(USER_FF, TENANT_FF);
		registerDefaultHelpSeeker(USER_MV, TENANT_MV);
		registerDefaultHelpSeeker(USER_RK, TENANT_RK);
	}

	private void registerDefaultHelpSeeker(String helpseekerId, String tenantName) {
		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");
		Tenant tenant = tenantService.getTenantByName(tenantName);

		coreUserService.registerToMarketplace(helpseekerId, mp.getId(), "");
	}

	public void initTenantAdmins() {
		createTenantAdmin(TENANT_ADMIN_FF, RAW_PASSWORD, "Peter", "Wagner");
		createTenantAdmin(TENANT_ADMIN_MV, RAW_PASSWORD, "Paul", "Gruber");
		createTenantAdmin(TENANT_ADMIN_RK, RAW_PASSWORD, "Pepe", "Weber");
	}

	public void initFlexProdTenantAdmin() {
		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");
		CoreUser tenantAdminFlexprod = createTenantAdmin(TENANT_ADMIN_FLEXPROD, RAW_PASSWORD, "Jane", "Doe");
		Tenant tenant = tenantService.getTenantByName(TENANT_FLEXPROD);

		coreUserService.subscribeUserToTenant(tenantAdminFlexprod.getId(), mp.getId(), tenant.getId(),
				UserRole.TENANT_ADMIN, "", false);

		registerDefaultTenantAdmin(TENANT_ADMIN_FLEXPROD, TENANT_FLEXPROD);
	}

	private CoreUser createTenantAdmin(String username, String password, String firstName, String lastName) {
		CoreUser tenantAdmin = coreUserRepository.findByUsername(username);
		if (tenantAdmin == null) {
			tenantAdmin = new CoreUser();
			tenantAdmin.setUsername(username);
			tenantAdmin.setPassword(bCryptPasswordEncoder.encode(password));
			tenantAdmin.setId(username);
			tenantAdmin.setFirstname(firstName);
			tenantAdmin.setLastname(lastName);
			tenantAdmin.setActivated(true);
			tenantAdmin.setAccountType(AccountType.ORGANIZATION);
			coreUserService.addNewUser(tenantAdmin, "", false);
		}
		return tenantAdmin;
	}

	public void subscribeDefaultTenantAdminsToTenants() {
		String tenantIdFF = coreTenantRepository.findByName(FFEIDENBERG).getId();
		String tenantIdRK = coreTenantRepository.findByName(RKWILHERING).getId();
		String tenantIdMV = coreTenantRepository.findByName(MUSIKVEREINSCHWERTBERG).getId();

		// TODO: needs change later on, works for now,
		// since there is only one marketplace
		Marketplace mp = this.marketplaceRepository.findByName("Marketplace 1");

		coreUserService.subscribeUserToTenant(TENANT_ADMIN_FF, mp.getId(), tenantIdFF, UserRole.TENANT_ADMIN, "",
				false);
		coreUserService.subscribeUserToTenant(TENANT_ADMIN_RK, mp.getId(), tenantIdRK, UserRole.TENANT_ADMIN, "",
				false);
		coreUserService.subscribeUserToTenant(TENANT_ADMIN_MV, mp.getId(), tenantIdMV, UserRole.TENANT_ADMIN, "",
				false);
	}

	public void registerDefaultTenantAdmins() {
		registerDefaultTenantAdmin(TENANT_ADMIN_FF, TENANT_FF);
		registerDefaultTenantAdmin(TENANT_ADMIN_MV, TENANT_MV);
		registerDefaultTenantAdmin(TENANT_ADMIN_RK, TENANT_RK);
	}

	private void registerDefaultTenantAdmin(String username, String tenantName) {
		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");

		coreUserService.registerToMarketplace(username, mp.getId(), "");
	}

}

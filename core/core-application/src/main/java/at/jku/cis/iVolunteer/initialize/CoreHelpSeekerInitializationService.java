package at.jku.cis.iVolunteer.initialize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerService;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.user.UserImagePathRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserImagePath;

@Service
public class CoreHelpSeekerInitializationService {
	
	private static final String USER_MV = "MVS";
	private static final String TENANT_MV = "Musikverein_Schwertberg";
	
	private static final String USER_FF = "FFA";
	private static final String TENANT_FF = "FF_Eidenberg";
	
	private static final String USER_RK = "OERK";
	private static final String TENANT_RK = "RK_Wilhering";

	private static final String MMUSTERMANN = "mmustermann";
	private static final String RAW_PASSWORD = "passme";

	private static final String FFEIDENBERG = TENANT_FF;
	private static final String MUSIKVEREINSCHWERTBERG = TENANT_MV;
	private static final String RKWILHERING = "RK_Wilhering";

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private TenantRepository coreTenantRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private UserImagePathRepository userImagePathRepository;
	@Autowired private CoreHelpSeekerService coreHelpSeekerService;
	@Autowired private MarketplaceRepository marketplaceRepository;

	public void initHelpSeekers() {
		String tenantIdFF = coreTenantRepository.findByName(FFEIDENBERG).getId();
		String tenantIdRK = coreTenantRepository.findByName(RKWILHERING).getId();
		String tenantIdMV = coreTenantRepository.findByName(MUSIKVEREINSCHWERTBERG).getId();

		// @formatter:off
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD, "M", "Mustermann", "", "HelpSeeker", USER_FF, tenantIdFF);

		CoreHelpSeeker helpseeker = createHelpSeeker(USER_FF, "passme", "Wolfgang", "Kronsteiner", "", "Feuerwehr Kommandant", USER_FF, tenantIdFF);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/FF_Altenberg.jpg"));

		helpseeker = createHelpSeeker("OERK", "passme", "Sandra", "Horvatis", "Rotes Kreuz", "Freiwilligenmanagement", "OERK", tenantIdRK);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/OERK_Sonderlogo_rgb_cropped.jpg"));

		helpseeker = createHelpSeeker(USER_MV, "passme", "Johannes", "Schönböck", "", "Musikverein Obmann", USER_MV, tenantIdMV);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/musikvereinschwertberg.jpeg"));
		// @formatter:on
	}

	private CoreHelpSeeker createHelpSeeker(String username, String password, String firstName, String lastName,
			String nickName, String position, String id, String tenantId) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker.setId(username);
			helpSeeker.setFirstname(firstName);
			helpSeeker.setLastname(lastName);
			helpSeeker.setNickname(nickName);
			helpSeeker.setPosition(position);
			helpSeeker.setTenantId(tenantId);
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}

	public void registerDefaultHelpSeekers() {

		registerDefaultHelpSeeker(USER_FF, TENANT_FF);
		registerDefaultHelpSeeker(USER_MV, TENANT_MV);
		registerDefaultHelpSeeker(USER_RK, TENANT_RK);

	}

	private void registerDefaultHelpSeeker(String helpSeekerUser, String tenantName) {
		List<CoreHelpSeeker> helpSeekers = coreHelpSeekerRepository.findAll();
		List<Tenant> tenants = coreTenantRepository.findAll();

		CoreHelpSeeker MV_helpSeeker = helpSeekers.stream()
				.filter(helpSeeker -> helpSeeker.getId().equals(helpSeekerUser)).findFirst().orElse(null);
		Tenant MV_tenant = tenants.stream().filter(tenant -> tenant.getName().equals(tenantName)).findFirst()
				.orElse(null);
		if (MV_helpSeeker != null && MV_tenant != null) {
			this.registerHelpSeeker(MV_helpSeeker, MV_tenant.getId());
		}
	}

	private void registerHelpSeeker(CoreHelpSeeker helpSeeker, String tenantId) {
		Marketplace mp = marketplaceRepository.findAll().stream().filter(m -> m.getName().equals("Marketplace 1"))
				.findFirst().orElse(null);
		if (mp != null) {
			try {
				coreHelpSeekerService.registerMarketplace(helpSeeker.getId(), mp.getId(), tenantId, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

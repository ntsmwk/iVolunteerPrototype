package at.jku.cis.iVolunteer.initialize;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreInitializationService {
	private static final String RECRUITER = "recruiter";
	private static final String FLEXPROD = "flexprod";
	private static final String ADMIN = "admin";
	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CoreUserRepository coreUserRepository;

	@Autowired
	private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired
	private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;
	@Autowired
	private CoreTenantInitializationService coreTenantInitializationService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private Environment environment;

	public void init() {
		createMarketplace();

		coreTenantInitializationService.initTenants();
		coreVolunteerInitializationService.initVolunteers();
		coreHelpSeekerInitializationService.initHelpSeekers();

		createFlexProdUser(FLEXPROD, RAW_PASSWORD);
		createAdminUser(ADMIN, RAW_PASSWORD);
		createRecruiter(RECRUITER, RAW_PASSWORD, "Daniel", "Huber", "Recruiter");

	}

	private void createMarketplace() {
		Marketplace marketplace = this.marketplaceRepository.findByName("Marketplace 1");
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setName("Marketplace 1");
			marketplace.setShortName("MP 1");
			if (environment.acceptsProfiles("dev")) {
				marketplace.setId("0eaf3a6281df11e8adc0fa7ae01bbebc");
				marketplace.setUrl("http://localhost:8080");
			}
			this.marketplaceRepository.save(marketplace);
		}
	}

	private void createRecruiter(String username, String password, String firstName, String lastName, String position) {
		CoreUser recruiter = coreUserRepository.findByUsername(username);
		if (recruiter == null) {
			recruiter = new CoreUser();
			recruiter.setUsername(username);
			recruiter.setPassword(bCryptPasswordEncoder.encode(password));
			recruiter.setFirstname(firstName);
			recruiter.setLastname(lastName);
			recruiter.setPosition(position);
			recruiter.setSubscribedTenants(
					Collections.singletonList(new TenantUserSubscription("noTenantId!?", UserRole.RECRUITER)));
			recruiter = coreUserRepository.insert(recruiter);
		}
	}

	private CoreUser createAdminUser(String username, String password) {
		CoreUser fpUser = coreUserRepository.findByUsername(username);
		if (fpUser == null) {
			fpUser = new CoreUser();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			fpUser.setSubscribedTenants(
					Collections.singletonList(new TenantUserSubscription("noTenantId!?", UserRole.ADMIN)));
			fpUser = coreUserRepository.insert(fpUser);
		}
		return fpUser;
	}

	private CoreUser createFlexProdUser(String username, String password) {

		CoreUser fpUser = coreUserRepository.findByUsername(username);

		if (fpUser == null) {
			fpUser = new CoreUser();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			fpUser.setSubscribedTenants(
					Collections.singletonList(new TenantUserSubscription("noTenantId!?", UserRole.FLEXPROD)));
			fpUser = coreUserRepository.insert(fpUser);
		}

		return fpUser;
	}

}

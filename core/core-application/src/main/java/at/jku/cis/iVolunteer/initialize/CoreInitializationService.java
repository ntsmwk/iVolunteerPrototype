package at.jku.cis.iVolunteer.initialize;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.diagram.XDiagramRawDataSetRepository;
import at.jku.cis.iVolunteer.core.image.ImageRepository;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.activation.CorePendingActivationRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.tenant.tags.TagRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tag;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.registration.AccountType;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreInitializationService {
	private static final String RECRUITER = "recruiter";
	private static final String FLEXPROD = "FlexProd";
	private static final String ADMIN = "admin";
	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CorePendingActivationRepository pendingActivationRepository;
	@Autowired
	private Environment environment;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	XDiagramRawDataSetRepository xDiagramRawDataSetRepository;

	protected void createMarketplace() {
		Marketplace marketplace = this.marketplaceRepository.findByName("Marketplace 1");
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setName("Marketplace 1");
			marketplace.setShortName("MP 1");
			if (environment.acceptsProfiles("dev")) {
				marketplace.setId("0eaf3a6281df11e8adc0fa7ae01bbebc");
				marketplace.setUrl("http://localhost:8080");
			}
			if (environment.acceptsProfiles("prodx")) {
				marketplace.setId("0eaf3a6281df11e8adc0fa7ae01bbebc");
				marketplace.setUrl("https://marketplace.ivolunteer.at");
			} else if (environment.acceptsProfiles("prod")) {
				marketplace.setId("5bc08399ae03711c3810e3cc");
				marketplace.setUrl("http://140.78.92.58:8080");
			}
			this.marketplaceRepository.save(marketplace);
		}
	}

	protected void createStandardRecruiter() {
		createRecruiter(RECRUITER, RAW_PASSWORD, "Daniel", "Huber", "Recruiter");
	}

	private void createRecruiter(String username, String password, String firstName, String lastName, String position) {
		CoreUser recruiter = coreUserRepository.findByUsername(username);
		if (recruiter == null) {
			recruiter = new CoreUser();
			recruiter.setUsername(username);
			recruiter.setPassword(bCryptPasswordEncoder.encode(password));
			recruiter.setFirstname(firstName);
			recruiter.setLastname(lastName);
			recruiter.setOrganizationPosition(position);
			recruiter.setActivated(true);
			recruiter.setAccountType(AccountType.PERSON);
			recruiter = coreUserRepository.save(recruiter);
		}
	}

	protected void subscribedRecruitersToTenant() {
		CoreUser recruiter = coreUserRepository.findByUsername(RECRUITER);
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		recruiter.setSubscribedTenants(Collections
				.singletonList(new TenantSubscription(marketplace.getId(), "!notenantId?", UserRole.RECRUITER)));
		coreUserRepository.save(recruiter);
	}

	protected void registerRecruitersToMarketplace() {
		CoreUser recruiter = coreUserRepository.findByUsername(RECRUITER);
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		if (marketplace != null) {
			// TODO register init
			// coreRecruiterController.registerMarketpace(recruiter.getId(),
			// marketplace.getId(), "");
		}
	}

	protected void createStandardAdminUser() {
		createAdminUser(ADMIN, RAW_PASSWORD);
	}

	private CoreUser createAdminUser(String username, String password) {
		CoreUser admin = coreUserRepository.findByUsername(username);
		if (admin == null) {
			admin = new CoreUser();
			admin.setUsername(username);
			admin.setPassword(bCryptPasswordEncoder.encode(password));
			admin.setActivated(true);
			admin.setAccountType(AccountType.ADMIN);
			admin = coreUserRepository.save(admin);
		}
		return admin;
	}

	protected void subscribeAdminsToTenant() {
		CoreUser admin = coreUserRepository.findByUsername(ADMIN);
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		admin.setSubscribedTenants(
				Collections.singletonList(new TenantSubscription(marketplace.getId(), "!notenantId?", UserRole.ADMIN)));
		coreUserRepository.save(admin);
	}

	protected void registerAdminsToMarketplace() {
		CoreUser admin = coreUserRepository.findByUsername(ADMIN);
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		if (marketplace != null) {
			// TODO - no function
		}
	}

	protected void createStandardFlexProdUser() {
		createFlexProdUser(FLEXPROD, RAW_PASSWORD);
	}

	private CoreUser createFlexProdUser(String username, String password) {
		CoreUser fpUser = coreUserRepository.findByUsername(username);
		if (fpUser == null) {
			fpUser = new CoreUser();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			// TODO: needs proper registration
			fpUser.setSubscribedTenants(
					Collections.singletonList(new TenantSubscription(null, null, UserRole.FLEXPROD)));
			fpUser = coreUserRepository.insert(fpUser);
		}

		return fpUser;
	}

	protected void addTenantTags() {
		for (int i = 0; i < 10; i++) {
			Tag t = new Tag("Tenant Tag" + i);
			tagRepository.save(t);
		}
	}

	public void wipeAll() {
		coreUserRepository.deleteAll();
		pendingActivationRepository.deleteAll();
		tenantRepository.deleteAll();
		tagRepository.deleteAll();
		imageRepository.deleteAll();
		xDiagramRawDataSetRepository.deleteAll();
	}

	public void deleteUsers() {
		this.coreUserRepository.deleteAll();
	}

}

package at.jku.cis.iVolunteer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.tenant.CoreTenantRepository;
import at.jku.cis.iVolunteer.core.user.UserImagePathRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreTenant;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.user.UserImagePath;

@SpringBootApplication
public class CoreApplication {

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String MWEIXLBAUMER = "mweixlbaumer";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";
	private static final String RECRUITER = "recruiter";
	private static final String FLEXPROD = "flexprod";
	private static final String RAW_PASSWORD = "passme";
	
	private static final String FFEIDENBERG = "FF_Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "Musikverein_Schwertberg";
	private static final String RKWILHERING = "RK_Wilhering";
	

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private UserImagePathRepository userImagePathRepository;
	@Autowired private CoreTenantRepository coreTenantRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@PostConstruct
	private void init() {
		createTenant(FFEIDENBERG);
		createTenant(MUSIKVEREINSCHWERTBERG);
		createTenant(RKWILHERING);
		
		CoreHelpSeeker helpSeeker = createHelpSeeker(MMUSTERMANN, RAW_PASSWORD);
		helpSeeker.setTenantId(coreTenantRepository.findByName(FFEIDENBERG).getId());
		saveHelpseeker(helpSeeker);
		
		createRecruiter(RECRUITER, RAW_PASSWORD);
		createFlexProdUser(FLEXPROD, RAW_PASSWORD);

		createVolunteer(BROISER, RAW_PASSWORD);
		CoreVolunteer volunteer = createVolunteer(PSTARZER, RAW_PASSWORD);
		volunteer.setFirstname("Philipp");
		volunteer.setLastname("Starzer");
		saveVolunteer(volunteer);

		volunteer = createVolunteer(MWEISSENBEK, RAW_PASSWORD);
		volunteer.setFirstname("Markus");
		volunteer.setLastname("Weißenbek");
		saveVolunteer(volunteer);

		volunteer = createVolunteer(MWEIXLBAUMER, RAW_PASSWORD);
		volunteer.setFirstname("Markus");
		volunteer.setLastname("Weixlbaumer");
		volunteer.addSubscribedTenant(coreTenantRepository.findByName(FFEIDENBERG).getId());
		saveVolunteer(volunteer);

		// Test Users for Instantiation
		volunteer = createVolunteer("AKop", "passme");
		volunteer.setFirstname("Alexander");
		volunteer.setLastname("Kopp");
		volunteer.setNickname("Alex");
		saveVolunteer(volunteer);

		volunteer = createVolunteer("WRet", "passme");
		volunteer.setFirstname("Werner");
		volunteer.setLastname("Retschitzegger");
		saveVolunteer(volunteer);

		volunteer = createVolunteer("WSch", "passme");
		volunteer.setFirstname("Wieland");
		volunteer.setLastname("Schwinger");
		saveVolunteer(volunteer);

		volunteer = createVolunteer("BProe", "passme");
		volunteer.setFirstname("Birgit");
		volunteer.setLastname("Pröll");
		saveVolunteer(volunteer);

		volunteer = createVolunteer("KKof", "passme");
		volunteer.setFirstname("Katharina");
		volunteer.setLastname("Kofler");
		volunteer.setNickname("Kati");
		saveVolunteer(volunteer);

		// Helpseekers
		CoreHelpSeeker helpseeker = createHelpSeekerFixedId("FFA", "passme");
		helpseeker.setFirstname("Wolfgang");
		helpseeker.setLastname("Kronsteiner");
		helpseeker.setPosition("Feuerwehr Kommandant");
		helpseeker.setId("FFA");
		helpseeker.setTenantId(coreTenantRepository.findByName(FFEIDENBERG).getId());
		saveHelpseeker(helpseeker);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/FF_Altenberg.jpg"));

		helpseeker = createHelpSeekerFixedId("OERK", "passme");
		helpseeker.setNickname("Rotes Kreuz");
		helpseeker.setId("OERK");
		helpseeker.setTenantId(coreTenantRepository.findByName(RKWILHERING).getId());
		helpseeker = saveHelpseeker(helpseeker);
		userImagePathRepository
				.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/OERK_Sonderlogo_rgb_cropped.jpg"));

		helpseeker = createHelpSeekerFixedId("MVS", "passme");
		helpseeker.setFirstname("Johannes");
		helpseeker.setLastname("Schönböck");
		helpseeker.setPosition("Musikverein Obmann");
		helpseeker.setId("MVS");
		helpseeker.setTenantId(coreTenantRepository.findByName(MUSIKVEREINSCHWERTBERG).getId());
		saveHelpseeker(helpseeker);
		userImagePathRepository
				.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/musikvereinschwertberg.jpeg"));

		CoreRecruiter recruiter = createRecruiter("recruiter", "passme");
		recruiter.setFirstname("Daniel");
		recruiter.setLastname("Huber");
		recruiter.setPosition("Recruiter");
		saveRecruiter(recruiter);

	}

	private CoreHelpSeeker saveHelpseeker(CoreHelpSeeker coreHelpseeker) {
		return coreHelpSeekerRepository.save(coreHelpseeker);
	}

	private CoreHelpSeeker createHelpSeeker(String username, String password) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}

	private CoreHelpSeeker createHelpSeekerFixedId(String username, String password) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setId(username);
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}

	private CoreVolunteer saveVolunteer(CoreVolunteer coreVolunteer) {
		CoreVolunteer volunteer = coreVolunteerRepository.save(coreVolunteer);
		return volunteer;
	}

	private CoreVolunteer createVolunteer(String username, String password) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
		return volunteer;
	}

	private CoreRecruiter createRecruiter(String username, String password) {
		CoreRecruiter recruiter = coreRecruiterRepository.findByUsername(username);
		if (recruiter == null) {
			recruiter = new CoreRecruiter();
			recruiter.setUsername(username);
			recruiter.setPassword(bCryptPasswordEncoder.encode(password));
			recruiter = coreRecruiterRepository.insert(recruiter);
		}
		return recruiter;
	}

	private CoreRecruiter saveRecruiter(CoreRecruiter coreRecruiter) {
		CoreRecruiter recruiter = coreRecruiterRepository.save(coreRecruiter);
		return recruiter;
	}

	private CoreFlexProd createFlexProdUser(String username, String password) {

		CoreFlexProd fpUser = coreFlexProdRepository.findByUsername(username);

		if (fpUser == null) {
			fpUser = new CoreFlexProd();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			fpUser = coreFlexProdRepository.insert(fpUser);
		}

		return fpUser;
	}
	
	private CoreTenant createTenant(String name) {
		CoreTenant tenant = coreTenantRepository.findByName(name);
		
		if(tenant == null) {
			tenant = new CoreTenant();
			tenant.setName(name);

			tenant = coreTenantRepository.insert(tenant);
		}
		return tenant;
		
	}
}

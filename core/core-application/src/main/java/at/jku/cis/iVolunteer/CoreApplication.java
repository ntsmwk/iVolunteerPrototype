package at.jku.cis.iVolunteer;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.user.UserImagePathRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerController;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
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

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private UserImagePathRepository userImagePathRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreVolunteerService coreVolunteerService;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@PostConstruct
	private void init() {
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD);
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

		volunteer = createVolunteer("JSch", "passme");
		volunteer.setFirstname("Johannes");
		volunteer.setLastname("Schönböck");
		volunteer.setNickname("Hannes");
		saveVolunteer(volunteer);

		volunteer = createVolunteer("KKof", "passme");
		volunteer.setFirstname("Katharina");
		volunteer.setLastname("Kofler");
		volunteer.setNickname("Kati");
		saveVolunteer(volunteer);

		// Helpseekers
		CoreHelpSeeker helpseeker = createHelpSeekerFixedId("FFA", "passme");
		helpseeker.setNickname("FF Altenberg");
		helpseeker.setId("FFA");
		saveHelpseeker(helpseeker);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/FF_Altenberg.jpg"));

		helpseeker = createHelpSeekerFixedId("OERK", "passme");
		helpseeker.setNickname("Rotes Kreuz");
		helpseeker.setId("OERK");
		helpseeker = saveHelpseeker(helpseeker);
		userImagePathRepository
				.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/OERK_Sonderlogo_rgb_cropped.jpg"));

		helpseeker = createHelpSeekerFixedId("MVS", "passme");
		helpseeker.setNickname("Musikverein Schwertberg");
		helpseeker.setId("MVS");
		saveHelpseeker(helpseeker);
		userImagePathRepository
				.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/musikvereinschwertberg.jpeg"));

		helpseeker = createHelpSeekerFixedId("EFA", "passme");
		helpseeker.setNickname("Forum Alpbach");
		helpseeker.setId("EFA");
		saveHelpseeker(helpseeker);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/neighborhelp.jpg"));

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
//		Marketplace mp = marketplaceRepository.findAll().stream().filter(m -> m.getName().equals("Marketplace 1"))
//				.findFirst().orElse(null);
//		if (mp != null) {
//			try {
//				coreVolunteerService.registerMarketplace(volunteer.getId(), mp.getId(), "");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
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
}

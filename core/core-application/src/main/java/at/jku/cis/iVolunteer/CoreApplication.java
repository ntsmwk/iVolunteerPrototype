package at.jku.cis.iVolunteer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.admin.CoreAdminRepository;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreAdmin;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@SpringBootApplication
public class CoreApplication {
	private static final String ADMIN = "admin";

	private static final String BROISER = "broiser";
	private static final String PSTARZER = "pstarzer";
	private static final String MWEISSENBEK = "mweissenbek";

	private static final String MMUSTERMANN = "mmustermann";

	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CoreAdminRepository coreAdminRepository;
	@Autowired
	private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@PostConstruct
	private void init() {
		createAdmin(ADMIN, RAW_PASSWORD);
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD);
		createVolunteer(BROISER, RAW_PASSWORD);
		createVolunteer(PSTARZER, RAW_PASSWORD);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD);

	}

	private CoreAdmin createAdmin(String username, String password) {
		CoreAdmin admin = coreAdminRepository.findByUsername(username);
		if (admin != null) {
			return admin;
		}
		admin = new CoreAdmin();
		admin.setUsername(username);
		admin.setPassword(bCryptPasswordEncoder.encode(password));
		return coreAdminRepository.insert(admin);
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
}

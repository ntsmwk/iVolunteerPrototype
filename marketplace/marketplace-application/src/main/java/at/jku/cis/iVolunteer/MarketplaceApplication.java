package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.Competence;
import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.participant.Employee;
import at.jku.cis.iVolunteer.marketplace.participant.EmployeeRepository;
import at.jku.cis.iVolunteer.marketplace.participant.Volunteer;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.participant.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.marketplace.participant.profile.VolunteerProfileRepository;

@ComponentScan("at.jku.cis.iVolunteer")
@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private VolunteerProfileRepository volunteerProfileRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CompetenceRepository competenceRepository;

	@Bean
	public RestTemplate prodduceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Employee employee = employeeRepository.findByUsername(MMUSTERMANN);
		if (employee == null) {
			employee = new Employee();
			employee.setUsername(MMUSTERMANN);
			employee.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			employee = employeeRepository.insert(employee);
		}

		createVolunteer(BROISER, RAW_PASSWORD);
		createVolunteer(PSTARZER, RAW_PASSWORD);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD);

		createCompetence("Planning");
		createCompetence("Leadership");
		createCompetence("Creativity");
		createCompetence("Flexability");
		createCompetence("Motivation");

	}

	private Volunteer createVolunteer(String username, String password) {
		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new Volunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer = volunteerRepository.insert(volunteer);
		}

		VolunteerProfile volunteerProfile = volunteerProfileRepository.findByVolunteer(volunteer);
		if (volunteerProfile == null) {
			volunteerProfile = new VolunteerProfile();
			volunteerProfile.setVolunteer(volunteer);
			volunteerProfile = volunteerProfileRepository.insert(volunteerProfile);
		}
		return volunteer;
	}

	private Competence createCompetence(String competenceName) {
		Competence competence = competenceRepository.findByName(competenceName);
		if (competence == null) {
			competence = new Competence();
			competence.setName(competenceName);
			competence = competenceRepository.insert(competence);
		}
		return competence;
	}
}

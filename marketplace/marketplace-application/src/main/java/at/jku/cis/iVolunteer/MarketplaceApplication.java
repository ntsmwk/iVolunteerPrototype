package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.group.GroupRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.group.Group;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;
	@Autowired private GroupRepository groupRepository;
	@Autowired private VolunteerRepository volunteerRepository;

	private static final String BROISER = "broiser";
	private static final String RAW_PASSWORD = "passme";
	
	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		createCompetence("Planning");
		createCompetence("Leadership");
		createCompetence("Creativity");
		createCompetence("Flexability");
		createCompetence("Motivation");
		createVolunteer(BROISER, RAW_PASSWORD);
		createGroup("Testgroup1", "This is my testgroup", false);
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

	private Group createGroup(String name, String description, boolean autoJoin) {
		Group group = new Group();
		group.setName(name);
		group.setDescription(description);
		group.setAutoJoin(autoJoin);
		group = groupRepository.insert(group);
		return group;
	}
	
	private Volunteer createVolunteer(String username, String password) {
		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new Volunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(password);
			volunteer = volunteerRepository.insert(volunteer);
		}
		return volunteer;
	}
}

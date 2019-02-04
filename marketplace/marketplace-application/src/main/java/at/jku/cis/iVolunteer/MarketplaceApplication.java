package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;

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

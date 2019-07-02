package at.jku.cis.iVolunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.marketplace.task.template.UserDefinedTaskTemplateRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;
	
	@Autowired private PropertyRepository propertyRepository;
	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;

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
		
		addStandardProperties();
		addTestTemplates();
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
	
	private void addStandardProperties() {
		StandardProperties sp = new StandardProperties(competenceRepository, propertyRepository);
		List<Property> props = sp.getAll();
		
		for (Property p : props) {
			if (!propertyRepository.exists(p.getId())) {
				propertyRepository.save(p);
			}
		}
	}
	
	private void addTestTemplates() {
		
		StandardTemplates st = new StandardTemplates(competenceRepository, propertyRepository);
		
		
		
		for (UserDefinedTaskTemplate t : st.createAll()) {
			if (!userDefinedTaskTemplateRepository.exists(t.getId())) {
				userDefinedTaskTemplateRepository.save(t);
			}
		}
		
		
//		UserDefinedTaskTemplate single = st.createTemplateWithAllProperties();
//		
//		if (!userDefinedTaskTemplateRepository.exists(single.getId())) {
//			userDefinedTaskTemplateRepository.save(single);
//			System.out.println("TestTemplate nested added");
//		} else {
//			System.out.println("TestTemplate nested already in db");
//		}
//		
//	
//		UserDefinedTaskTemplate nested = st.createNestedTemplateWithExamples();
//		
//		if (!userDefinedTaskTemplateRepository.exists(nested.getId())) {
//			userDefinedTaskTemplateRepository.save(nested);
//			System.out.println("TestTemplate nested added");
//		} else {
//			System.out.println("TestTemplate nested already in db");
//		}
	}
}

package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.InheritanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.configurable.class_.ConfigurableClassRepository;
import at.jku.cis.iVolunteer.marketplace.configurable.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.marketplace.task.template.UserDefinedTaskTemplateRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.Relationship;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.*;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;
	
	@Autowired private PropertyRepository propertyRepository;
	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	@Autowired private ConfigurableClassRepository configurableClassRepository;
	@Autowired private RelationshipRepository relationshipRepository;

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
		
		addTestConfigClasses();
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
	}
	
	private void addTestConfigClasses() {
		ConfigurableClass c1 = new ConfigurableClass();
		c1.setId("test1");
		c1.setName("Class 1");
		c1.setProperties(new LinkedList<Property>());
		
		
		c1.getProperties().add(new StandardProperties.NameProperty());
		c1.getProperties().add( new StandardProperties.CommentsProperty());
		c1.getProperties().add(new StandardProperties.StartDateProperty());
		c1.getProperties().add(new StandardProperties.DescriptionProperty());
		
		
		
		ConfigurableClass c2 = new ConfigurableClass();
		c2.setId("test2");
		c2.setName("Class 2");
		c2.setProperties(new ArrayList<Property>());
		
		ConfigurableClass c3 = new ConfigurableClass();
		c3.setId("test3");
		c3.setName("Class 3");
		c3.setProperties(new ArrayList<Property>());
		
		
		ConfigurableClass c4 = new ConfigurableClass();
		c4.setId("test4");
		c4.setName("Class 4");
		c4.setProperties(new ArrayList<Property>());
		
		
		ConfigurableClass c5 = new ConfigurableClass();
		c5.setId("test5");
		c5.setName("Class 5");
		c5.setProperties(new ArrayList<Property>());
		
		
		ConfigurableClass c6 = new ConfigurableClass();
		c6.setId("test6");
		c6.setName("Class 6");
		c6.setProperties(new ArrayList<Property>());
		
		
		
//		{from: 1, to: 3},
//		  {from: 1, to: 2},
//		  {from: 2, to: 4},
//		  {from: 2, to: 5},
//		  {from: 3, to: 3},
//		  {from: 6, to: 6},

		
		Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
		i1.setId("i1");
		Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
		i2.setId("i2");
		Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
		i3.setId("i3");
		Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
		i4.setId("i4");
		Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
		i5.setId("i5");
		Association i6 = new Association(c6.getId(), c6.getId(), AssociationParameter.ONE, AssociationParameter.ZEROSTAR);
		i6.setId("i6");
		
		if (!relationshipRepository.exists(i1.getId())) {
			relationshipRepository.save(i1);
		}
		if (!relationshipRepository.exists(i2.getId())) {
			relationshipRepository.save(i2);
		}
		if (!relationshipRepository.exists(i3.getId())) {
			relationshipRepository.save(i3);
		}
		if (!relationshipRepository.exists(i4.getId())) {
			relationshipRepository.save(i4);
		}
		if (!relationshipRepository.exists(i5.getId())) {
			relationshipRepository.save(i5);
		}
		if (!relationshipRepository.exists(i6.getId())) {
			relationshipRepository.save(i6);
		}
		  
		  
		if (!configurableClassRepository.exists(c1.getId())) {
			configurableClassRepository.save(c1);
		}
		
		if (!configurableClassRepository.exists(c2.getId())) {
			configurableClassRepository.save(c2);
		}
		
		if (!configurableClassRepository.exists(c3.getId())) {
			configurableClassRepository.save(c3);
		}
		
		if (!configurableClassRepository.exists(c4.getId())) {
			configurableClassRepository.save(c4);
		}
		
		if (!configurableClassRepository.exists(c5.getId())) {
			configurableClassRepository.save(c5);
		}
		
		if (!configurableClassRepository.exists(c6.getId())) {
			configurableClassRepository.save(c6);
		}
	}
}

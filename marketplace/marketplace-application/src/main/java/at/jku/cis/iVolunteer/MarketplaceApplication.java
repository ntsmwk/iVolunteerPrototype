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
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepostiory;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.marketplace.task.template.UserDefinedTaskTemplateRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationParameter;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired private CompetenceRepository competenceRepository;
	
	@Autowired private PropertyRepository propertyRepository;
	@Autowired private UserDefinedTaskTemplateRepository userDefinedTaskTemplateRepository;
	@Autowired private ClassDefinitionRepostiory configurableClassRepository;
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
		ClassDefinition c1 = new ClassDefinition();
		c1.setId("test1");
		c1.setName("Class 1");
		c1.setProperties(new LinkedList<Property>());
		
		
		c1.getProperties().add(new StandardProperties.NameProperty());
		c1.getProperties().add( new StandardProperties.CommentsProperty());
		c1.getProperties().add(new StandardProperties.StartDateProperty());
		c1.getProperties().add(new StandardProperties.DescriptionProperty());
		
		
		
		ClassDefinition c2 = new ClassDefinition();
		c2.setId("test2");
		c2.setName("Class 2");
		c2.setProperties(new ArrayList<Property>());
		
		ClassDefinition c3 = new ClassDefinition();
		c3.setId("test3");
		c3.setName("Class 3");
		c3.setProperties(new ArrayList<Property>());
		
		
		ClassDefinition c4 = new ClassDefinition();
		c4.setId("test4");
		c4.setName("Class 4");
		c4.setProperties(new ArrayList<Property>());
		
		
		ClassDefinition c5 = new ClassDefinition();
		c5.setId("test5");
		c5.setName("Class 5");
		c5.setProperties(new ArrayList<Property>());
		
		
		ClassDefinition c6 = new ClassDefinition();
		c6.setId("test6");
		c6.setName("Class 6");
		c6.setProperties(new ArrayList<Property>());
		
		ClassDefinition c7 = new ClassDefinition();
		c7.setId("test7");
		c7.setName("Class 7");
		c7.setProperties(new ArrayList<Property>());
		
		ClassDefinition c8 = new ClassDefinition();
		c8.setId("test8");
		c8.setName("Class 8");
		c8.setProperties(new ArrayList<Property>());
		
		ClassDefinition c9 = new ClassDefinition();
		c9.setId("test9");
		c9.setName("Class 9");
		c9.setProperties(new ArrayList<Property>());
		
		
		
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
		
		Association i7 = new Association(c5.getId(), c7.getId(), AssociationParameter.ONE, AssociationParameter.ONESTAR);
		i7.setId("i7");
		Association i8 = new Association(c5.getId(), c8.getId(), AssociationParameter.ONE, AssociationParameter.ZEROONE);
		i8.setId("i8");
		Association i9 = new Association(c4.getId(), c9.getId(), AssociationParameter.ZEROSTAR, AssociationParameter.ZEROONE);
		i9.setId("i9");
		
		
		
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
		if (!relationshipRepository.exists(i7.getId())) {
			relationshipRepository.save(i7);
		}
		if (!relationshipRepository.exists(i8.getId())) {
			relationshipRepository.save(i8);
		}
		if (!relationshipRepository.exists(i9.getId())) {
			relationshipRepository.save(i9);
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
		
		if (!configurableClassRepository.exists(c7.getId())) {
			configurableClassRepository.save(c7);
		}
		
		if (!configurableClassRepository.exists(c8.getId())) {
			configurableClassRepository.save(c8);
		}
		
		if (!configurableClassRepository.exists(c9.getId())) {
			configurableClassRepository.save(c9);
		}
	}
	
}

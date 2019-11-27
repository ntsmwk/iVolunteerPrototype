package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;


@SpringBootApplication
public class MarketplaceApplication {

	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionsRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}
	
	@PreDestroy
	public void onExit() {
		classDefinitionRepository.deleteAll();
		relationshipRepository.deleteAll();
		classInstanceRepository.deleteAll();
		configuratorRepository.deleteAll();
		propertyDefinitionsRepository.deleteAll();
		
	}

	@PostConstruct
	public void init() {
		CompetenceClassDefinition def = new CompetenceClassDefinition();
		def.setClassArchetype(ClassArchetype.COMPETENCE);
		def.setMarketplaceId("asdfasdf");
		def.setName("mycompdf");
		def.setId("mycompdf");
		def.setTimestamp(new Date());
		if (!classDefinitionRepository.exists(def.getId())) {
			classDefinitionRepository.save(def);

		}
		
		addTestConfigClasses();
		addConfigurators();		

	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void addTestConfigClasses() {
				
		CompetenceClassDefinition c1 = new CompetenceClassDefinition();
		c1.setId("test1");
		c1.setName("Class 1");
		c1.setProperties(new ArrayList<ClassProperty<Object>>());
		c1.setRoot(true);
		
		PropertyDefinition npd = new StandardPropertyDefinitions.NameProperty();
		ClassProperty<Object> ncp = propertyDefinitionToClassPropertyMapper.toTarget(npd);
		c1.getProperties().add(ncp);
		
		PropertyDefinition sdpd = new StandardPropertyDefinitions.StartDateProperty();	
		ClassProperty<Object> sdcp = propertyDefinitionToClassPropertyMapper.toTarget(sdpd);
		c1.getProperties().add(sdcp);
		
		PropertyDefinition dpd = new StandardPropertyDefinitions.DescriptionProperty(); 
		ClassProperty<Object> dcp = propertyDefinitionToClassPropertyMapper.toTarget(dpd);
		c1.getProperties().add(dcp);
		
		c1.setClassArchetype(ClassArchetype.COMPETENCE);
		
		CompetenceClassDefinition c2 = new CompetenceClassDefinition();
		c2.setId("test2");
		c2.setName("Class 2");
		c2.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c3 = new CompetenceClassDefinition();
		c3.setId("test3");
		c3.setName("Class 3");
		c3.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c4 = new CompetenceClassDefinition();
		c4.setId("test4");
		c4.setName("Class 4");
		c4.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c5 = new CompetenceClassDefinition();
		c5.setId("test5");
		c5.setName("Class 5");
		c5.setClassArchetype(ClassArchetype.COMPETENCE);

		
		
		CompetenceClassDefinition c6 = new CompetenceClassDefinition();
		c6.setId("test6");
		c6.setName("Class 6");
		c6.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c7 = new CompetenceClassDefinition();
		c7.setId("test7");
		c7.setName("Class 7");
		c7.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c8 = new CompetenceClassDefinition();
		c8.setId("test8");
		c8.setName("Class 8");
		c8.setClassArchetype(ClassArchetype.COMPETENCE);

		
		CompetenceClassDefinition c9 = new CompetenceClassDefinition();
		c9.setId("test9");
		c9.setName("Class 9");
		c9.setClassArchetype(ClassArchetype.COMPETENCE);

		
		
		
//		{from: 1, to: 3},
//		  {from: 1, to: 2},
//		  {from: 2, to: 4},
//		  {from: 2, to: 5},
//		  {from: 3, to: 3},
//		  {from: 6, to: 6},

		
		Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
		i1.setId("test_i1");
		Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
		i2.setId("test_i2");
		Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
		i3.setId("test_i3");
		Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
		i4.setId("test_i4");
		Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
		i5.setId("test_i5");
//		Inheritance i6 = new Inheritance(c6.getId(), c6.getId(), c6.getId());
//		i6.setId("test_i6");
		
		Inheritance i7 = new Inheritance(c5.getId(), c7.getId(),c5.getId());
		i7.setId("test_i7");
		Inheritance i8 = new Inheritance(c5.getId(), c8.getId(), c5.getId());
		i8.setId("test_i8");
		Inheritance i9 = new Inheritance(c4.getId(), c9.getId(), c4.getId());
		i9.setId("test_i9");
		
		
		
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

		if (!relationshipRepository.exists(i7.getId())) {
			relationshipRepository.save(i7);
		}
		if (!relationshipRepository.exists(i8.getId())) {
			relationshipRepository.save(i8);
		}
		if (!relationshipRepository.exists(i9.getId())) {
			relationshipRepository.save(i9);
		}
		  
		  
		if (!classDefinitionRepository.exists(c1.getId())) {
			classDefinitionRepository.save(c1);
		}
		
		if (!classDefinitionRepository.exists(c2.getId())) {
			classDefinitionRepository.save(c2);
		}
		
		if (!classDefinitionRepository.exists(c3.getId())) {
			classDefinitionRepository.save(c3);
		}
		
		if (!classDefinitionRepository.exists(c4.getId())) {
			classDefinitionRepository.save(c4);
		}
		
		if (!classDefinitionRepository.exists(c5.getId())) {
			classDefinitionRepository.save(c5);
		}
		
		if (!classDefinitionRepository.exists(c6.getId())) {
			classDefinitionRepository.save(c6);
		}
		
		if (!classDefinitionRepository.exists(c7.getId())) {
			classDefinitionRepository.save(c7);
		}
		
		if (!classDefinitionRepository.exists(c8.getId())) {
			classDefinitionRepository.save(c8);
		}
		
		if (!classDefinitionRepository.exists(c9.getId())) {
			classDefinitionRepository.save(c9);
		}
	}

	private void addConfigurators() {
		Configurator c1 = new Configurator();
		c1.setName("test1");
		c1.setId("test1");
		c1.setDate(new Date());
		
		c1.setRelationshipIds(new ArrayList<>());
		c1.getRelationshipIds().add("test_i1");
		c1.getRelationshipIds().add("test_i2");
		c1.getRelationshipIds().add("test_i3");
		c1.getRelationshipIds().add("test_i4");
		c1.getRelationshipIds().add("test_i5");
		c1.getRelationshipIds().add("test_i6");
		c1.getRelationshipIds().add("test_i7");
		c1.getRelationshipIds().add("test_i8");
		c1.getRelationshipIds().add("test_i9");

		c1.setClassDefinitionIds(new ArrayList<>());
		c1.getClassDefinitionIds().add("test1");
		c1.getClassDefinitionIds().add("test2");
		c1.getClassDefinitionIds().add("test3");
		c1.getClassDefinitionIds().add("test4");
		c1.getClassDefinitionIds().add("test5");
		c1.getClassDefinitionIds().add("test6");
		c1.getClassDefinitionIds().add("test7");
		c1.getClassDefinitionIds().add("test8");
		c1.getClassDefinitionIds().add("test9");

		Configurator c2 = new Configurator();
		c2.setName("test2");
		c2.setId("test2");
		c2.setDate(new Date(1289516400000L));

		Configurator c3 = new Configurator();
		c3.setName("test3");
		c3.setId("test3");

		c3.setDate(new Date());

		Configurator c4 = new Configurator();
		c4.setName("test4");
		c4.setId("test4");

		c4.setDate(new Date());

		if (!configuratorRepository.exists(c1.getId())) {
			configuratorRepository.save(c1);
		}

		if (!configuratorRepository.exists(c2.getId())) {
			configuratorRepository.save(c2);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c4.getId())) {
			configuratorRepository.save(c4);
		}

	}

}

package at.jku.cis.iVolunteer.marketplace.configurations.clazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingCollectorConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingCollectorConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@RestController
public class ClassConfigurationController {

	@Autowired private ClassConfigurationRepository classConfigurationRepository;
	@Autowired private CollectionService collectionService;
	
	@Autowired private MatchingCollectorConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	
	
	
	@GetMapping("class-configuration/all")
	List<ClassConfiguration> getAllClassConfigurations() {
		return classConfigurationRepository.findAll();
	}
	
	@GetMapping("class-configuration/all/sort")
	List<ClassConfiguration> getAllClassConfigurationsWithSort(@RequestParam(value = "sorted", required = false) String sortType) {
		
//		if (sortType.equalsIgnoreCase("asc")) {
//			return configuratorRepository.findAllWithSort(new Sort(Sort.Direction.ASC, "date"));
//
//		} else if (sortType.equalsIgnoreCase("desc")) {
//			return configuratorRepository.findAllWithSort(new Sort(Sort.Direction.DESC, "date"));
//		} 
		
		
		
		List<ClassConfiguration> classConfigurations =  classConfigurationRepository.findAllWithSort(new Sort(Sort.Direction.ASC, "name"));
		return classConfigurations;
	}
	
	@GetMapping("class-configuration/{id}")
	ClassConfiguration getClassConfigurationById(@PathVariable("id") String id) {
		return classConfigurationRepository.findOne(id);
	}
	
	@GetMapping("class-configuration/by-name/{name}")
	List<ClassConfiguration> getClassConfigurationByName(@PathVariable("name") String name) {
		return classConfigurationRepository.findByName(name);
	}
	
	@PostMapping("class-configuration/new-empty")
	ClassConfiguration createNewEmptyClassConfiguration(@RequestBody String[] params) {
		if (params.length != 2) {
			return null;
		}
		
		ClassConfiguration classConfiguration = new ClassConfiguration();
		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);
		classConfiguration.setTimestamp(new Date());
		
		return saveClassConfiguration(classConfiguration);
	}
	
	@PostMapping("class-configuration/new")
	ClassConfiguration createNewClassConfiguration(@RequestBody String[] params) {
		if (params.length != 3) {
			return null;
		}
		
		ClassConfiguration newClassConfiguration = createAndSaveNewClassConfiguration(params[0], params[1], params[2], null);
		return newClassConfiguration;
	}
	
	@PutMapping("class-configuration/save")
	public ClassConfiguration saveClassConfiguration(@RequestBody ClassConfiguration updatedClassConfiguration) {
		updatedClassConfiguration.setTimestamp(new Date());
				
		ClassConfiguration classConfiguration = classConfigurationRepository.save(updatedClassConfiguration);
		
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(updatedClassConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
		if (classDefinitions != null) {
			for (ClassDefinition cd : classDefinitions) {
				cd.setConfigurationId(classConfiguration.getId());
			}
		}
		classDefinitionRepository.save(classDefinitions);
		
		//Build MatchingCollector
		List<MatchingCollector> collectors = collectionService.collectAllClassDefinitionsWithPropertiesAsMatchingCollectors(classConfiguration.getId());
				
		MatchingCollectorConfiguration matchingCollectorConfiguration = new MatchingCollectorConfiguration();
		matchingCollectorConfiguration.setId(classConfiguration.getId());
		matchingCollectorConfiguration.setClassConfigurationId(classConfiguration.getId());
		matchingCollectorConfiguration.setCollectors(collectors);
	
		
		matchingCollectorConfigurationRepository.save(matchingCollectorConfiguration);
		return classConfiguration;
	}
	
	@DeleteMapping("class-configuration/{id}/delete")
	void deleteClassConfiguration(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration= classConfigurationRepository.findOne(id);
		
		classConfiguration.getClassDefinitionIds().forEach(classDefinitionRepository::delete);
		classConfiguration.getRelationshipIds().forEach(relationshipRepository::delete);
		
		classConfigurationRepository.delete(id);
	}
	
	@PutMapping("class-configuration/delete-multiple")
	List<ClassConfiguration> deleteMultipleClassConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this::deleteClassConfiguration);
		return this.classConfigurationRepository.findAll();
	}
	
	private ClassConfiguration createAndSaveNewClassConfiguration(String tenantId, String configuratorName, String description, String configuratorId) {
		
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		
		List<PropertyDefinition<Object>> properties = this.propertyDefinitionRepository.findByTenantId(tenantId);
		
		ClassDefinition fwPassEintrag = new ClassDefinition();
		fwPassEintrag.setId(new ObjectId().toHexString());
		fwPassEintrag.setTenantId(tenantId);
		fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
		fwPassEintrag.setRoot(true);
		fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
		fwPassEintrag.setWriteProtected(true);
		fwPassEintrag.setCollector(true);
		fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
		
		PropertyDefinition idProperty = properties.stream().filter(p -> p.getName().equals("id")).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(idProperty));
		
		PropertyDefinition nameProperty = properties.stream().filter(p -> p.getName().equals("name")).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(nameProperty));
		
		PropertyDefinition evidenzProperty = properties.stream().filter(p -> p.getName().equals("evidenz")).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(evidenzProperty));
		
		classDefinitions.add(fwPassEintrag);

		
		ClassDefinition task = new ClassDefinition();
		task.setId(new ObjectId().toHexString());
		task.setTenantId(tenantId);
		task.setName("Tätigkeit");
		task.setClassArchetype(ClassArchetype.TASK);
		task.setWriteProtected(true);
		task.setProperties(new ArrayList<>());
		
		PropertyDefinition dateFromProperty = properties.stream().filter(p -> p.getName().equals("Starting Date")).findFirst().get();
		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateFromProperty));
		
		PropertyDefinition dateToProperty = properties.stream().filter(p -> p.getName().equals("End Date")).findFirst().get();
		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateToProperty));
		
		classDefinitions.add(task);
		
		Inheritance r1 = new Inheritance();
		r1.setRelationshipType(RelationshipType.INHERITANCE);
		r1.setTarget(task.getId());
		r1.setSource(fwPassEintrag.getId());
		
		relationships.add(r1);
		
		ClassDefinition competence = new ClassDefinition();		
		competence.setId(new ObjectId().toHexString());
		competence.setTenantId(tenantId);
		competence.setName("Kompetenz");
		competence.setClassArchetype(ClassArchetype.COMPETENCE);
		competence.setWriteProtected(true);
		competence.setProperties(new ArrayList<>());

		classDefinitions.add(competence);

		
		Inheritance r2 = new Inheritance();
		r2.setRelationshipType(RelationshipType.INHERITANCE);
		r2.setTarget(competence.getId());
		r2.setSource(fwPassEintrag.getId());
		
		relationships.add(r2);
		
		ClassDefinition achievement = new ClassDefinition();
		achievement.setId(new ObjectId().toHexString());
		achievement.setTenantId(tenantId);
		achievement.setName("Verdienst");
		achievement.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievement.setWriteProtected(true);
		achievement.setProperties(new ArrayList<>());
		
		classDefinitions.add(achievement);


		Inheritance r3 = new Inheritance();
		r3.setRelationshipType(RelationshipType.INHERITANCE);
		r3.setTarget(achievement.getId());
		r3.setSource(fwPassEintrag.getId());
		
		relationships.add(r3);

		
		ClassDefinition function = new ClassDefinition();
		function.setId(new ObjectId().toHexString());
		function.setTenantId(tenantId);
		function.setName("Funktion");
		function.setClassArchetype(ClassArchetype.FUNCTION);
		function.setWriteProtected(true);
		function.setProperties(new ArrayList<>());

		classDefinitions.add(function);

		
		Inheritance r4 = new Inheritance();
		r4.setRelationshipType(RelationshipType.INHERITANCE);
		r4.setTarget(function.getId());
		r4.setSource(fwPassEintrag.getId());
		
		relationships.add(r4);
		
		/////////////////Philipp Zeug//////////////////////////
		ClassDefinition myTask = new ClassDefinition();
		myTask.setId(new ObjectId().toHexString());
		myTask.setTenantId(tenantId);
		myTask.setName("myTask");
		myTask.setClassArchetype(ClassArchetype.TASK);
		myTask.setProperties(new ArrayList<>());
		
		PropertyDefinition tt1 = properties.stream().filter(p -> p.getName().equals("taskType1")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt1));
		
		PropertyDefinition tt2 = properties.stream().filter(p -> p.getName().equals("taskType2")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt2));
		
		PropertyDefinition tt3 = properties.stream().filter(p -> p.getName().equals("taskType3")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt3));
		
		PropertyDefinition location = properties.stream().filter(p -> p.getName().equals("Location")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(location));
		
		PropertyDefinition rank = properties.stream().filter(p -> p.getName().equals("rank")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(rank));
		
		PropertyDefinition duration = properties.stream().filter(p -> p.getName().equals("duration")).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(duration));
		
		classDefinitions.add(myTask);
		
		Inheritance r5 = new Inheritance();
		r5.setRelationshipType(RelationshipType.INHERITANCE);
		r5.setTarget(myTask.getId());
		r5.setSource(task.getId());
		
		relationships.add(r5);
		
		ClassConfiguration configurator = new ClassConfiguration();
		configurator.setTimestamp(new Date());
		configurator.setId(configuratorId);
		configurator.setName(configuratorName);
		configurator.setDescription(description);
		configurator.setClassDefinitionIds(new ArrayList<>());
		configurator.setRelationshipIds(new ArrayList<>());
		
		
		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(configurator.getId());
			this.classDefinitionRepository.save(cd);
			configurator.getClassDefinitionIds().add(cd.getId());
		}
		
		for (Relationship r : relationships) {
			this.relationshipRepository.save(r);
			configurator.getRelationshipIds().add(r.getId());
		}
		
//		this.classConfigurationRepository.save(configurator);
		return saveClassConfiguration(configurator);
		
	}
	
	
}

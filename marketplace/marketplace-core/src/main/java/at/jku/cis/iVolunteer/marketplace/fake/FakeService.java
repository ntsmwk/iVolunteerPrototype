package at.jku.cis.iVolunteer.marketplace.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.chart.StoredChartRepository;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToReset;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToResetRepository;
import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceController;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class FakeService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToPropertyInstanceMapper propertyDefinitionToPropertyInstanceMapper;
	@Autowired private ClassInstanceController classInstanceController;
	@Autowired private StoredChartRepository storedChartRepository;
	@Autowired private IsSunburstFakeRepository isSunburstFakeRepository;
	@Autowired private ClassesAndRelationshipsToResetRepository classesAndRelationshipsToResetRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;

	public void reset() {

		resetFahrtenspangeFake();

		resetSunburstFake();

//		Reset Sunburst Fake
		// Reset ClassDefinitions
		resetClassDefinitionsRelationshipsAndConfigurators();
//			//Reset Flag for retrieval

//		Reset Sybos Fake

		deleteSharedChart();
	}

	private void deleteSharedChart() {
		this.storedChartRepository.deleteAll();
	}

	private void resetFahrtenspangeFake() {
		// Reset Fahrtenspange Derivation-Rule
		derivationRuleRepository.deleteAll();

		// Reset Fahrtenspange Task

		// Reset Fahrtenspange Badge
		List<ClassInstance> list = classInstanceRepository.findAll().stream()
				.filter(ci -> ci.getName().equals("Fahrtenspange Bronze")).collect(Collectors.toList());
		classInstanceRepository.delete(list);
	}

	private void resetSunburstFake() {
		this.isSunburstFakeRepository.deleteAll();
	}

	private void resetClassDefinitionsRelationshipsAndConfigurators() {
		List<ClassesAndRelationshipsToReset> list = classesAndRelationshipsToResetRepository.findAll();
		ClassesAndRelationshipsToReset reset = list.get(0);

		classDefinitionRepository.deleteAll();
		classDefinitionRepository.save(reset.getClassDefinitions());

		relationshipRepository.deleteAll();
		relationshipRepository.save(reset.getRelationships());

		configuratorRepository.deleteAll();
		configuratorRepository.save(reset.getConfigurators());

	}

	public void pushTaskFromAPI() {
//		[{
//		    "_id": "test_2222",
//		    "_class": "at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance",
//		    "name": "Medical Care Transport_test",
//		    "userId": "5df4c9d571186b28b3474941",
//		    "issuerId": "FFA",
//		    "published": false,
//		    "inUserRepository": false,
//		    "inIssuerInbox": false,
//		    "classArchetype": "TASK",
//		    "timestamp": "2020-01-15T10:02:39.834Z"
//		}]

		TaskClassInstance instance = new TaskClassInstance();
		instance.setId("task_from_api");
		instance.setClassArchetype(ClassArchetype.TASK);
		instance.setName("Sybos Tätigkeit");
		instance.setIssuerId("FFA");

		Volunteer user = volunteerRepository.findByUsername("mweixlbaumer");
		instance.setUserId(user.getId());

		instance.setPublished(false);
		instance.setInUserRepository(false);
		instance.setInIssuerInbox(false);

		instance.setTimestamp(new Date());

		List<PropertyInstance<Object>> properties = new ArrayList<>();

		PropertyDefinition<Object> nameDefinition = propertyDefinitionRepository.findOne("name");
		PropertyInstance<Object> nameInstance = propertyDefinitionToPropertyInstanceMapper.toTarget(nameDefinition);
		nameInstance.setValues(new ArrayList<>());
		nameInstance.getValues().add("Brandeinsatz");

		properties.add(nameInstance);

		instance.setProperties(properties);

		List<ClassInstance> instances = new ArrayList<>();
		instances.add(instance);

		classInstanceController.createNewClassInstances(instances);

	}

	public void addFahrtenspangeFake() {

		TaskClassInstance instance = new TaskClassInstance();
		instance.setId("fahrtenspange" + new Date().hashCode());
		instance.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		instance.setName("Fahrtenspange Bronze");
		instance.setIssuerId("FFA");
		instance.setImagePath("");

		Volunteer user = volunteerRepository.findByUsername("mweixlbaumer");
		instance.setUserId(user.getId());

		instance.setPublished(false);
		instance.setInUserRepository(false);
		instance.setInIssuerInbox(false);

		instance.setTimestamp(new Date());

		List<PropertyInstance<Object>> properties = new ArrayList<>();

		PropertyDefinition<Object> nameDefinition = propertyDefinitionRepository.findOne("name");
		PropertyInstance<Object> nameInstance = propertyDefinitionToPropertyInstanceMapper.toTarget(nameDefinition);
		nameInstance.setValues(new ArrayList<>());
		nameInstance.getValues().add("Fahrtenspange Bronze");

		properties.add(nameInstance);

		instance.setProperties(properties);

		List<ClassInstance> instances = new ArrayList<>();
		instances.add(instance);

		classInstanceController.createNewClassInstances(instances);
	}

	public void createResetState() {
		classesAndRelationshipsToResetRepository.deleteAll();

		ClassesAndRelationshipsToReset classesAndRelationshipsToReset = new ClassesAndRelationshipsToReset();
		classesAndRelationshipsToReset.getClassDefinitions().addAll(classDefinitionRepository.findAll());
		classesAndRelationshipsToReset.getRelationships().addAll(relationshipRepository.findAll());
		classesAndRelationshipsToReset.getConfigurators().addAll(configuratorRepository.findAll());

		classesAndRelationshipsToResetRepository.save(classesAndRelationshipsToReset);

	}

}

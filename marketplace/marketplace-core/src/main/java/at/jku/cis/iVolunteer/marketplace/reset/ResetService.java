package at.jku.cis.iVolunteer.marketplace.reset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceController;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class ResetService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToPropertyInstanceMapper propertyDefinitionToPropertyInstanceMapper;
	@Autowired private ClassInstanceController classInstanceController;
	
	public void reset() {

		resetFahrtenspangeFake();
		
		resetSunburstFake();
//		Reset Sunburst Fake
			//Reset ClassDefinitions
//			//Reset Flag for retrieval
		
//		Reset Sybos Fake
		
//		Reset
	}

	private void resetFahrtenspangeFake() {
		// Reset Fahrtenspange Derivation-Rule
		derivationRuleRepository.deleteAll();

		// Reset Fahrtenspange Task

		// Reset Fahrtenspange Badge
	}
	
	private void resetSunburstFake() {
		//TODO
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
		
		PropertyDefinition<Object> nameDefinition= propertyDefinitionRepository.findOne("name");
		PropertyInstance<Object> nameInstance = propertyDefinitionToPropertyInstanceMapper.toTarget(nameDefinition);
		nameInstance.setValues(new ArrayList<>());
		nameInstance.getValues().add("NAME - TBD");
		
		properties.add(nameInstance);
		
		instance.setProperties(properties);
		
		List<ClassInstance> instances = new ArrayList<>();
		instances.add(instance);
		
		classInstanceController.createNewClassInstances(instances);
				
				
	}
}

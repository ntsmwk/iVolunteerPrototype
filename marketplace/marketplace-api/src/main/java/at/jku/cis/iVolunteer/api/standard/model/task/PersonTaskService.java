package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class PersonTaskService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;

	
	public void savePersonTasks(List<PersonTask> personTasks) {
		ClassDefinition personTaskClassDefinition = classDefinitionService.getByName("personTask");
		if(personTaskClassDefinition != null) {
			for(PersonTask personTask : personTasks) {
				savePersonTask(personTaskClassDefinition, personTask);
			}
		}
	}


	private void savePersonTask(ClassDefinition personTaskClassDefinition, PersonTask personTask) {
		// @formatter:off
		ClassInstance personTaskClassInstance = classDefinition2InstanceMapper.toTarget(personTaskClassDefinition);
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskID")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskName")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskName(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType1")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType1(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType2")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType2(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType3")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType3(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType4")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType4(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDescription")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDescription(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskRoleID")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskRoleID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskRole")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskRole(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskVehicleID")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskVehicleID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskVehicle")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskVehicle(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskCountAll")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskCountAll(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDateFrom")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDateFrom(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDateTo")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDateTo(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDuration")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDuration(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskLocation")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskLocation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskGeoInformation")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskGeoInformation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerUUID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerSource(), new Object[0])));
		classInstanceRepository.save(personTaskClassInstance);		 
		// @formatter:on
	}

	
}

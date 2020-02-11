package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class PersonTaskService {

	private static final int FF_NEW = 2;
	private static final int MV = 3;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;
	
	

	public void savePersonTasks(List<PersonTask> personTasks, int level) {
		ClassDefinition personTaskClassDefinition = classDefinitionService.getByName("PersonTask");
		List<ClassInstance> classInstances = new ArrayList<ClassInstance>();
		if (personTaskClassDefinition != null) {
			for (PersonTask personTask : personTasks) {
				classInstances.add(savePersonTask(personTaskClassDefinition, personTask, level));
			}
		}
	}

	private TaskClassInstance savePersonTask(ClassDefinition personTaskClassDefinition, PersonTask personTask,
			int level) {
		// @formatter:off
		TaskClassInstance personTaskClassInstance = (TaskClassInstance)classDefinition2InstanceMapper.toTarget(personTaskClassDefinition);
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskId")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskId(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskName")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskName(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType1")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType1(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType2")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType2(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType3")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType3(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType4")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskType4(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDescription")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDescription(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Zweck")).forEach(p -> p.setValues(Lists.asList(personTask.getPurpose(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Rolle")).forEach(p -> p.setValues(Lists.asList(personTask.getRole(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Rang")).forEach(p -> p.setValues(Lists.asList(personTask.getRank(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Phase")).forEach(p -> p.setValues(Lists.asList(personTask.getPhase(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Arbeitsteilung")).forEach(p -> p.setValues(Lists.asList(personTask.getUnit(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Ebene")).forEach(p -> p.setValues(Lists.asList(personTask.getLevel(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDateFrom")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDateFrom(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDateTo")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDateTo(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskDuration")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskDuration(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskLocation")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskLocation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskGeoInformation")).forEach(p -> p.setValues(Lists.asList(personTask.getTaskGeoInformation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID")).forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerUUID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource")).forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerSource(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID")).forEach(p -> p.setValues(Lists.asList(personTask.getPersonID(), new Object[0])));	
		personTaskClassInstance.setUserId(userMappingService.getByExternalUserId(personTask.getPersonID()).getiVolunteerUserId());
		personTaskClassInstance.setIssuerId(level == MV?"MVS":"FFA");
		personTaskClassInstance.setTimestamp(new Date());
		
		return classInstanceRepository.save(personTaskClassInstance);		 
		// @formatter:on
	}

}

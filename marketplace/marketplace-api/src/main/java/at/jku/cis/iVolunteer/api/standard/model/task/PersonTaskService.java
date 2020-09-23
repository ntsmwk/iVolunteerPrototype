package at.jku.cis.iVolunteer.api.standard.model.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;

@Service
public class PersonTaskService {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private UserMappingService userMappingService;
	@Autowired private MarketplaceService marketplaceService;

	public void savePersonTasks(List<PersonTask> personTasks, String tenantId) {
		ClassDefinition personTaskClassDefinition = classDefinitionService.getByName("PersonTask", tenantId);
		List<ClassInstance> classInstances = new ArrayList<ClassInstance>();
		if (personTaskClassDefinition != null) {
			for (PersonTask personTask : personTasks) {
				classInstances.add(savePersonTask(personTaskClassDefinition, personTask, tenantId));
			}

		}
	}

	private TaskClassInstance savePersonTask(ClassDefinition personTaskClassDefinition, PersonTask personTask,
			String tenantId) {
		// @formatter:off
		TaskClassInstance personTaskClassInstance = (TaskClassInstance) classDefinition2InstanceMapper
				.toTarget(personTaskClassDefinition);
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskId"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskId())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskName())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskType1"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType1())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskType2"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType2())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskType3"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType3())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskType4"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType4())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Description"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskDescription())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Purpose"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getPurpose())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Role"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getRole())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Rank"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getRank())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Phase"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getPhase())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Unit"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getUnit())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Level"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getLevel())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> {
			try {
				p.setValues(Collections.singletonList(DateUtils.parseDate(personTask.getTaskDateFrom(), "yyyy-MM-dd HH:mm:ss").getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("End Date")).forEach(p -> {
			try {
				p.setValues(Collections.singletonList(DateUtils.parseDate(personTask.getTaskDateTo(), "yyyy-MM-dd HH:mm:ss").getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Duration"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskDuration())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Location"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskLocation())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("GeoInformation"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskGeoInformation())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("IVolunteerUUID"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getiVolunteerUUID())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("IVolunteerSource"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getiVolunteerSource())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("PersonID"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getPersonID())));

		personTaskClassInstance
				.setUserId(userMappingService.getByExternalUserId(personTask.getPersonID()).getiVolunteerUserId());

		personTaskClassInstance.setIssuerId(tenantId);
		personTaskClassInstance.setTenantId(tenantId);
		personTaskClassInstance.setBlockchainDate(new Date());
		personTaskClassInstance.setMarketplaceId(marketplaceService.getMarketplaceId());
		personTaskClassInstance.setIssued(true);

		personTaskClassInstance.setTimestamp(new Date());

		return classInstanceRepository.save(personTaskClassInstance);
		// @formatter:on
	}

}

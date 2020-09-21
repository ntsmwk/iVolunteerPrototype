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
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskId"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskId())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("name"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskName())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType1"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType1())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType2"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType2())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType3"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType3())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType4"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskType4())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Description"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskDescription())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("purpose"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getPurpose())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("role"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getRole())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("rank"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getRank())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("phase"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getPhase())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("unit"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getUnit())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("level"))
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
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("duration"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskDuration())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Location"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskLocation())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("geoInformation"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskGeoInformation())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getiVolunteerUUID())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getiVolunteerSource())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID"))
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

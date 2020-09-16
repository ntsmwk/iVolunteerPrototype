package at.jku.cis.iVolunteer.api.standard.model.task;

import java.text.ParseException;
import java.util.ArrayList;
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
import jersey.repackaged.com.google.common.collect.Lists;

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
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskId(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("name"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskName(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType1"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskType1(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType2"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskType2(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType3"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskType3(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("taskType4"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskType4(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Description"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskDescription(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("purpose"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getPurpose(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("role"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getRole(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("rank"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getRank(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("phase"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getPhase(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("unit"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getUnit(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("level"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getLevel(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Starting Date")).forEach(p -> {
			try {
				p.setValues(Lists.asList(DateUtils.parseDate(personTask.getTaskDateFrom(), "yyyy-MM-dd HH:mm:ss").getTime(),
						new Object[0]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("End Date")).forEach(p -> {
			try {
				p.setValues(Lists.asList(DateUtils.parseDate(personTask.getTaskDateTo(), "yyyy-MM-dd HH:mm:ss").getTime(),
						new Object[0]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("duration"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskDuration(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Location"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskLocation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("geoInformation"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getTaskGeoInformation(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerUUID"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerUUID(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("iVolunteerSource"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getiVolunteerSource(), new Object[0])));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("personID"))
				.forEach(p -> p.setValues(Lists.asList(personTask.getPersonID(), new Object[0])));

		personTaskClassInstance
				.setUserId(userMappingService.getByExternalUserId(personTask.getPersonID()).getiVolunteerUserId());

		personTaskClassInstance.setIssuerId(tenantId);
		personTaskClassInstance.setTenantId(tenantId);
		personTaskClassInstance.setBlockchainDate(new Date());
		personTaskClassInstance.setMarketplaceId(marketplaceService.getMarketplaceId());

		personTaskClassInstance.setTimestamp(new Date());

		return classInstanceRepository.save(personTaskClassInstance);
		// @formatter:on
	}

}

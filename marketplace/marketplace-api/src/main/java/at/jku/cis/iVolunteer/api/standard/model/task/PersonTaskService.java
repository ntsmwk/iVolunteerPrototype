package at.jku.cis.iVolunteer.api.standard.model.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.task.XTaskInstanceService;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyEntry;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.GeoInformation;

@Service
public class PersonTaskService {

	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private ClassInstanceRepository classInstanceRepository;
	@Autowired
	private TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired
	private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired
	private UserMappingService userMappingService;
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private XTaskInstanceService xTaskInstanceService;

	private static final ArrayList<String> LEVEL0_PROPERTIES = new ArrayList<String>(
			Arrays.asList((new String[] { "ID", "Name", "Description" })));
	private static final ArrayList<String> LEVEL1_PROPERTIES = new ArrayList<String>(
			Arrays.asList((new String[] { "Starting Date", "End Date", "Location" })));
	private static final ArrayList<String> LEVEL2_PROPERTIES = new ArrayList<String>(
			Arrays.asList((new String[] { "Rank", "Duration" })));
	// Everything else Level3

	public void savePersonTasks(List<PersonTask> personTasks, String tenantId) {
		ClassDefinition personTaskClassDefinition = classDefinitionService.getByName("PersonTask", tenantId);
		List<ClassInstance> classInstances = new ArrayList<ClassInstance>();

		if (personTaskClassDefinition != null) {
			for (PersonTask personTask : personTasks) {
				ClassInstance classInstance = createPersonTask(personTaskClassDefinition, personTask, tenantId);
				assignLevels(classInstance);
				classInstances.add(classInstance);
				classInstance = classInstanceRepository.save(classInstance);

				TaskInstance taskInstance = new TaskInstance();
				taskInstance = taskInstance.updateTaskInstance(classInstance);
				taskInstance.setId(classInstance.getId());
				taskInstance.setStatus(TaskInstanceStatus.CLOSED);
				taskInstance.setTenantId(classInstance.getTenantId());
				xTaskInstanceService.addOrOverwriteTaskInstance(taskInstance);
			}
		}
	}

	private ClassInstance createPersonTask(ClassDefinition personTaskClassDefinition, PersonTask personTask,
			String tenantId) {
		// @formatter:off
		ClassInstance personTaskClassInstance = classDefinition2InstanceMapper
				.toTarget(personTaskClassDefinition);

		// @formatter:on
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskType")).forEach(p -> {
			TreePropertyDefinition propertyDefinition = treePropertyDefinitionRepository
					.getByNameAndTenantId("TaskType", tenantId);
			List<TreePropertyEntry> allPropertyEntries = propertyDefinition.getEntries();

			TreePropertyEntry entry1 = allPropertyEntries.stream()
					.filter(e -> e.getValue().equals(personTask.getTaskType1())).findFirst().orElse(null);

			List<String> tt2Ids = propertyDefinition.getRelationships().stream()
					.filter(r -> r.getSourceId().equals(entry1.getId())).map(r -> r.getTargetId())
					.collect(Collectors.toList());
			List<TreePropertyEntry> tt2Entries = allPropertyEntries.stream().filter(e -> tt2Ids.contains(e.getId()))
					.collect(Collectors.toList());
			TreePropertyEntry entry2 = tt2Entries.stream().filter(e -> e.getValue().equals(personTask.getTaskType2()))
					.findFirst().orElse(null);

			if (entry2 != null) {
				List<String> tt3Ids = propertyDefinition.getRelationships().stream()
						.filter(r -> r.getSourceId().equals(entry2.getId())).map(r -> r.getTargetId())
						.collect(Collectors.toList());
				List<TreePropertyEntry> tt3Entries = allPropertyEntries.stream().filter(e -> tt3Ids.contains(e.getId()))
						.collect(Collectors.toList());
				TreePropertyEntry entry3 = tt3Entries.stream()
						.filter(e -> e.getValue().equals(personTask.getTaskType3())).findFirst().orElse(null);

				if (entry3 != null) {
					List<String> tt4Ids = propertyDefinition.getRelationships().stream()
							.filter(r -> r.getSourceId().equals(entry3.getId())).map(r -> r.getTargetId())
							.collect(Collectors.toList());
					List<TreePropertyEntry> tt4Entries = allPropertyEntries.stream()
							.filter(e -> tt4Ids.contains(e.getId())).collect(Collectors.toList());
					TreePropertyEntry entry4 = tt4Entries.stream()
							.filter(e -> e.getValue().equals(personTask.getTaskType4())).findFirst().orElse(null);

					if (entry4 != null) {
						entry4.setParents(Arrays.asList(entry3, entry2, entry1));
						p.setValues(Collections.singletonList(entry4));
					} else {
						// entry 4 == null
						entry3.setParents(Arrays.asList(entry2, entry1));
						p.setValues(Collections.singletonList(entry3));
					}

				} else {
					// entry3 == null
					entry2.setParents(Arrays.asList(entry1));
					p.setValues(Collections.singletonList(entry2));
				}

			} else {
				// entry 2 == null
				p.setValues(Collections.singletonList(entry1));
			}
		});
		// @formatter:off

		// TODO Philipp: for now, add random SozMin Bereich to imported data
		List<String> bereiche = Arrays.asList("Katastrophenhilfs- & Rettungsdienste", "Ältere Menschen", "Soziales",
		"Entwicklungshilfe, Frieden, Menschrechte", "Gesundheit & Pflege", "Kirche & Religion",
		"Bürgerschaftliche Aktivitäten & Gemeinwesen", "Sport & Bewegung", "Kinder & Jugendliche",
		"Bildung & Coaching", "Natur, Umwelt, Tierschutz", "Kultur, Kunst, Unterhaltung, Freizeit",
		"Menschen mit Beeinträchtigung", "Migrantinnen und Migranten");
		Random rand = new Random();
		String randomElement = bereiche.get(rand.nextInt(bereiche.size()));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Bereich"))
		.forEach(p -> p.setValues(Collections.singletonList(randomElement)));

		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("TaskId"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskId())));
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Name"))
				.forEach(p -> p.setValues(Collections.singletonList(personTask.getTaskName())));
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
		
		String taskLocation = personTask.getTaskLocation();
		GeoInformation taskGeoInformation = personTask.getTaskGeoInformation();
		Location location = new Location(taskLocation, taskGeoInformation);
		personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("Location"))
				.forEach(p -> p.setValues(Collections.singletonList(location)));
	
		personTaskClassInstance.getProperties().remove(personTaskClassInstance.getProperties().stream().filter(p -> p.getName().equals("GeoInformation")).findAny().get());
		
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
		personTaskClassInstance.setName(personTask.getTaskName());

		personTaskClassInstance.setTimestamp(new Date());

		return personTaskClassInstance;
		// @formatter:on
	}

	private void assignLevels(ClassInstance classInstance) {
		for (PropertyInstance<Object> pi : classInstance.getProperties()) {
			if (LEVEL0_PROPERTIES.contains(pi.getName())) {
				pi.setLevel(0);
			} else if (LEVEL1_PROPERTIES.contains(pi.getName())) {
				pi.setLevel(1);
			} else if (LEVEL2_PROPERTIES.contains(pi.getName())) {
				pi.setLevel(2);
			} else {
				pi.setLevel(3);
			}
		}
	}

}

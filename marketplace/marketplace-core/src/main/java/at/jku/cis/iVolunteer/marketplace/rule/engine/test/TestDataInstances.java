package at.jku.cis.iVolunteer.marketplace.rule.engine.test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataInstances.RolesAmbulanceService;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.user.User;

@Service
public class TestDataInstances {

	@Autowired
	private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired
	private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired ClassDefinitionToInstanceMapper classDefinitionToInstanceMapper;
	
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private ClassInstanceService classInstanceService;
	@Autowired
	private ClassPropertyService classPropertyService;

	@Autowired
	private CoreTenantRestClient coreTenantRestClient;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassConfigurationRepository classConfigurationRepository;

	@Autowired
	private TestDataClasses testDataClasses;

	public static final String CERTIFICATE_SEF_MODUL1 = "SEF-Modul 1";
	public static final String CERTIFICATE_SEF_MODUL2 = "SEF-Modul 2";
	public static final String CERTIFICATE_SEF_AUSFORTBILDUNG = "SEF Aus- und Fortbildung";
	public static final String CERTIFICATE_SEF_WORKSHOP = "SEF Workshop";
	public static final String CERTIFICATE_SEF_TRAINING_NOTARZT = "SEF – Theorie- und Praxistraining Notarzt";
	public static final String CERTIFICATE_SEF_LADEGUTSICHERUNG = "SEF Ladegutsicherung LKW";
	public static final String CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG = "SEF Theorietrainerausbildung";

	public static final String TASK_RK_RETTUNGSDIENST = "Rettungsdienst";
	public static final String TASK_RK_AUSFAHRT = "Ausfahrt";
	public static final String TASK_RK_EINSATZ = "Einsatz";
	public static final String TASK_RK_DIENST = "Dienst";

	private static final String RKWILHERING = "RK Wilhering";
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.GERMAN);

	public enum RolesAmbulanceService {
		DISPONENT("Disponent"), EINSATZLENKER("Einsatzlenker"), SANITÄTER("Sanitäter"),
		AUSZUBILDENDER("Auszubildender");

		private String description;

		RolesAmbulanceService(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	public static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static LocalDateTime createRandomDateTime(int startYear, int endYear) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int year = createRandomIntBetween(startYear, endYear);
		//
		Random rand = new Random();
		// Store a random seed
		long seed = rand.nextLong();
		Random generator = new Random(seed);
		LocalTime time = LocalTime.MIN.plusSeconds(generator.nextLong());
		return LocalDateTime.of(year, month, day, time.getHour(), 0);
	}

	public void createUserData() {
		createDataKBauer();
		createDataEWagner();
		createDataWHaube();
		createDataMJachs();
	}

	private void createDataKBauer() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassConfiguration classConfig = classConfigurationRepository.findByNameAndTenantId("Test-Rule-Engine RK", tenantId);		
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);;
		
		User volunteer = userRepository.findByUsername("KBauer");

		if (volunteer == null)
			return;
		
//		ClassDefinition certClass = classDefinitionRepository.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
		ClassDefinition certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
	
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		

		generateTaskAusfahrt(tenantId, volunteer, 80, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 100, RolesAmbulanceService.EINSATZLENKER, "Wels", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);

		generateTaskEinsatz(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Wels", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns", classConfig);

		generateTaskDienst(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);
	}

	private void createDataEWagner() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassConfiguration classConfig = classConfigurationRepository.findByNameAndTenantId("Test-Rule-Engine RK", tenantId);
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);;
		User volunteer = userRepository.findByUsername("EWagner");

		if (volunteer == null)
			return;

		ClassDefinition certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);

		generateTaskAusfahrt(tenantId, volunteer, 90, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 100, RolesAmbulanceService.EINSATZLENKER, "Wels", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);

		generateTaskEinsatz(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Wels", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns", classConfig);
		generateTaskDienst(tenantId, volunteer, 100, RolesAmbulanceService.DISPONENT, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);
	}

	private void createDataWHaube() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassConfiguration classConfig = classConfigurationRepository.findByNameAndTenantId("Test-Rule-Engine RK", tenantId);
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);
		User volunteer = userRepository.findByUsername("WHaube");

		if (volunteer == null)
			return;
		
		ClassDefinition certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);

		generateTaskAusfahrt(tenantId, volunteer, 1000, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 20, RolesAmbulanceService.EINSATZLENKER, "Wels", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);

		generateTaskEinsatz(tenantId, volunteer, 75, RolesAmbulanceService.DISPONENT, "Wels", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 57, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns", classConfig);

		generateTaskDienst(tenantId, volunteer, 500, RolesAmbulanceService.DISPONENT, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);
	}

	private void createDataMJachs() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassConfiguration classConfig = classConfigurationRepository.findByNameAndTenantId("Test-Rule-Engine RK", tenantId);
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);
		User volunteer = userRepository.findByUsername("MJachs");

		if (volunteer == null)
			return;

		ClassDefinition certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
		certClass = classDefinitions.stream().filter(cd -> cd.getName().equals(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE)).findFirst().get();
		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);

		generateTaskAusfahrt(tenantId, volunteer, 200, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 60, RolesAmbulanceService.EINSATZLENKER, "Wels", classConfig);
		generateTaskAusfahrt(tenantId, volunteer, 40, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);

		generateTaskEinsatz(tenantId, volunteer, 150, RolesAmbulanceService.DISPONENT, "Wels", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 56, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskEinsatz(tenantId, volunteer, 23, RolesAmbulanceService.SANITÄTER, "Enns", classConfig);

		generateTaskDienst(tenantId, volunteer, 200, RolesAmbulanceService.DISPONENT, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz", classConfig);
		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz", classConfig);
	}

	public void generateTaskAusfahrt(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort, ClassConfiguration classConfig) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(volunteer.getBirthday());
		int fromYear = cal.get(Calendar.YEAR) + 20;

		for (int i = 0; i < num; i++) {
			LocalDateTime randomDateTime = createRandomDateTime(fromYear, 2019);
			
			List<ClassDefinition> classDefinitions = new ArrayList<>();
			classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);
			ClassDefinition taskDef = classDefinitions.stream().filter(cd -> cd.getName().equals("Ausfahrt")).findFirst().get();
			
			ClassProperty<Object> cpStartingDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("Starting Date", tenantId));
			cpStartingDate.setDefaultValues(Collections.singletonList(randomDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			taskDef.getProperties().add(cpStartingDate);

			ClassProperty<Object> cpEndDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("End Date", tenantId));
			cpEndDate.setDefaultValues(Collections.singletonList(randomDateTime.plusHours(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			taskDef.getProperties().add(cpEndDate);

			ClassProperty<Object> cpRole = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("role", tenantId));
			cpRole.setDefaultValues(Collections.singletonList(role.description));
			taskDef.getProperties().add(cpRole);

			ClassProperty<Object> cpOrt = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("ort", tenantId));
			cpOrt.setDefaultValues(Collections.singletonList(ort));
			taskDef.getProperties().add(cpOrt);
			
			
			taskDef.setConfigurationId(null);
			ClassInstance ci = classDefinitionToInstanceMapper.toTarget(taskDef);
			ci.setIssuerId(tenantId);
			ci.setUserId(volunteer.getId());
			
			classInstanceService.newClassInstance(ci);
			
//			ClassInstance ti = classInstanceService.newClassInstance(volunteer, taskDef.getId(),tenantId);
		}
	}

	public void generateTaskEinsatz(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort, ClassConfiguration classConfig) {
		for (int i = 0; i < num; i++) {
			LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
			List<ClassDefinition> classDefinitions = new ArrayList<>();
			classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);
			ClassDefinition taskDef = classDefinitions.stream().filter(cd -> cd.getName().equals("Einsatz")).findFirst().get();			

			ClassProperty<Object> cpStartDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("Starting Date", tenantId));
			cpStartDate.setDefaultValues(Collections.singletonList(randomDateTime.format(dateFormatter)));
			taskDef.getProperties().add(cpStartDate);

			randomDateTime.plusHours(1);
			randomDateTime.plusMinutes(30);
			
			ClassProperty<Object> cpEndDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("End Date", tenantId));
			cpEndDate.setDefaultValues(Collections.singletonList(randomDateTime.format(dateFormatter)));
			taskDef.getProperties().add(cpEndDate);
			
			ClassProperty<Object> cpRole = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("role", tenantId));
			cpRole.setDefaultValues(Collections.singletonList(role.getDescription()));
			taskDef.getProperties().add(cpRole);
			
			ClassProperty<Object> cpOrt = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("ort", tenantId));
			cpOrt.setDefaultValues(Collections.singletonList(ort));
			taskDef.getProperties().add(cpOrt);
			

			ClassInstance ci = classDefinitionToInstanceMapper.toTarget(taskDef);
			ci.setIssuerId(tenantId);
			ci.setUserId(volunteer.getId());

			classInstanceService.newClassInstance(ci);
		}
	}

	public void generateTaskDienst(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort, ClassConfiguration classConfig) {
		for (int i = 0; i < num; i++) {
			LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
			List<ClassDefinition> classDefinitions = new ArrayList<>();
			classDefinitionRepository.findAll(classConfig.getClassDefinitionIds()).forEach(classDefinitions::add);
			ClassDefinition taskDef = classDefinitions.stream().filter(cd -> cd.getName().equals("Dienst")).findFirst().get();	
			
	
			ClassProperty<Object> cpStartDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("Starting Date", tenantId));
			cpStartDate.setDefaultValues(Collections.singletonList(randomDateTime.format(dateFormatter)));
			taskDef.getProperties().add(cpStartDate);

			randomDateTime.plusHours(1);
			randomDateTime.plusMinutes(30);
			
			ClassProperty<Object> cpEndDate = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("End Date", tenantId));
			cpEndDate.setDefaultValues(Collections.singletonList(randomDateTime.format(dateFormatter)));
			taskDef.getProperties().add(cpEndDate);
			
			ClassProperty<Object> cpRole = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("role", tenantId));
			cpRole.setDefaultValues(Collections.singletonList(role.getDescription()));
			taskDef.getProperties().add(cpRole);
			
			ClassProperty<Object> cpOrt = propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.getByNameAndTenantId("ort", tenantId));
			cpOrt.setDefaultValues(Collections.singletonList(ort));
			taskDef.getProperties().add(cpOrt);
			
			ClassInstance ci = classDefinitionToInstanceMapper.toTarget(taskDef);
			ci.setIssuerId(tenantId);
			ci.setUserId(volunteer.getId());

			classInstanceService.newClassInstance(ci);
		}
	}
}

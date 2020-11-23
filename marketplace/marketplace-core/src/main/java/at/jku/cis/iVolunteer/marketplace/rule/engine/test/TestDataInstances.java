//package at.jku.cis.iVolunteer.marketplace.rule.engine.test;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.util.Locale;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
//import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
//import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
//import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
//import at.jku.cis.iVolunteer.model.user.User;
//
//@Service
//public class TestDataInstances {
//
//	@Autowired private ClassDefinitionRepository classDefinitionRepository;
//	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
//	@Autowired private MarketplaceService marketplaceService;
//	@Autowired private ClassInstanceService classInstanceService;
//	@Autowired private ClassPropertyService classPropertyService;
//
//	@Autowired private CoreTenantRestClient coreTenantRestClient;
//	@Autowired private UserRepository userRepository;
//
//	@Autowired private TestDataClasses testDataClasses;
//
//	public static final String CERTIFICATE_SEF_MODUL1 = "SEF-Modul 1";
//	public static final String CERTIFICATE_SEF_MODUL2 = "SEF-Modul 2";
//	public static final String CERTIFICATE_SEF_AUSFORTBILDUNG = "SEF Aus- und Fortbildung";
//	public static final String CERTIFICATE_SEF_WORKSHOP = "SEF Workshop";
//	public static final String CERTIFICATE_SEF_TRAINING_NOTARZT = "SEF – Theorie- und Praxistraining Notarzt";
//	public static final String CERTIFICATE_SEF_LADEGUTSICHERUNG = "SEF Ladegutsicherung LKW";
//	public static final String CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG = "SEF Theorietrainerausbildung";
//
//	public static final String TASK_RK_RETTUNGSDIENST = "Rettungsdienst";
//	public static final String TASK_RK_AUSFAHRT = "Ausfahrt";
//	public static final String TASK_RK_EINSATZ = "Einsatz";
//	public static final String TASK_RK_DIENST = "Dienst";
//
//	private static final String RKWILHERING = "RK Wilhering";
//	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.GERMAN);
//
//	public enum RolesAmbulanceService {
//		DISPONENT("Disponent"), EINSATZLENKER("Einsatzlenker"), SANITÄTER("Sanitäter"),
//		AUSZUBILDENDER("Auszubildender");
//
//		private String description;
//
//		RolesAmbulanceService(String description) {
//			this.description = description;
//		}
//
//		public String getDescription() {
//			return description;
//		}
//	}
//
//	public static int createRandomIntBetween(int start, int end) {
//		return start + (int) Math.round(Math.random() * (end - start));
//	}
//
//	public static LocalDateTime createRandomDateTime(int startYear, int endYear) {
//		int day = createRandomIntBetween(1, 28);
//		int month = createRandomIntBetween(1, 12);
//		int year = createRandomIntBetween(startYear, endYear);
//		//
//		Random rand = new Random();
//		// Store a random seed
//		long seed = rand.nextLong();
//		Random generator = new Random(seed);
//		LocalTime time = LocalTime.MIN.plusSeconds(generator.nextLong());
//		return LocalDateTime.of(year, month, day, time.getHour(), 0);
//	}
//
//	public void createUserData() {
//		createDataKBauer();
//		createDataEWagner();
//		createDataWHaube();
//		createDataMJachs();
//	}
//
//	private void createDataKBauer() {
//		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		User volunteer = userRepository.findByUsername("KBauer");
//
//		if (volunteer == null)
//			return;
//
//		AchievementClassDefinition certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//
//		generateTaskAusfahrt(tenantId, volunteer, 999, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskAusfahrt(tenantId, volunteer, 100, RolesAmbulanceService.EINSATZLENKER, "Wels");
//		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz");
//
//		generateTaskEinsatz(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Wels");
//		generateTaskEinsatz(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns");
//
//		generateTaskDienst(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Linz");
//		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz");
//	}
//
//	private void createDataEWagner() {
//		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		User volunteer = userRepository.findByUsername("EWagner");
//
//		if (volunteer == null)
//			return;
//
//		AchievementClassDefinition certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//
//		generateTaskAusfahrt(tenantId, volunteer, 90, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskAusfahrt(tenantId, volunteer, 100, RolesAmbulanceService.EINSATZLENKER, "Wels");
//		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz");
//
//		generateTaskEinsatz(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Wels");
//		generateTaskEinsatz(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns");
//		generateTaskDienst(tenantId, volunteer, 100, RolesAmbulanceService.DISPONENT, "Linz");
//		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz");
//	}
//
//	private void createDataWHaube() {
//		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		User volunteer = userRepository.findByUsername("WHaube");
//
//		if (volunteer == null)
//			return;
//
//		AchievementClassDefinition certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//
//		generateTaskAusfahrt(tenantId, volunteer, 1000, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskAusfahrt(tenantId, volunteer, 20, RolesAmbulanceService.EINSATZLENKER, "Wels");
//		generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz");
//
//		generateTaskEinsatz(tenantId, volunteer, 75, RolesAmbulanceService.DISPONENT, "Wels");
//		generateTaskEinsatz(tenantId, volunteer, 57, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns");
//
//		generateTaskDienst(tenantId, volunteer, 500, RolesAmbulanceService.DISPONENT, "Linz");
//		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz");
//	}
//
//	private void createDataMJachs() {
//		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		User volunteer = userRepository.findByUsername("MJachs");
//
//		if (volunteer == null)
//			return;
//
//		AchievementClassDefinition certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//		certClass = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
//		classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
//
//		generateTaskAusfahrt(tenantId, volunteer, 2046, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskAusfahrt(tenantId, volunteer, 646, RolesAmbulanceService.EINSATZLENKER, "Wels");
//		generateTaskAusfahrt(tenantId, volunteer, 446, RolesAmbulanceService.SANITÄTER, "Linz");
//
//		generateTaskEinsatz(tenantId, volunteer, 150, RolesAmbulanceService.DISPONENT, "Wels");
//		generateTaskEinsatz(tenantId, volunteer, 56, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskEinsatz(tenantId, volunteer, 23, RolesAmbulanceService.SANITÄTER, "Enns");
//
//		generateTaskDienst(tenantId, volunteer, 200, RolesAmbulanceService.DISPONENT, "Linz");
//		generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
//		generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz");
//	}
//
//	public void generateTaskAusfahrt(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(volunteer.getBirthday());
//		int fromYear = cal.get(Calendar.YEAR) + 20;
//
//		for (int i = 0; i < num; i++) {
//			LocalDateTime randomDateTime = createRandomDateTime(fromYear, 2019);
//			TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository
//					.findByNameAndTenantId("Ausfahrt", tenantId);
//			TaskClassInstance ti = (TaskClassInstance) classInstanceService.newClassInstance(volunteer, taskDef.getId(),
//					tenantId);
//			ti.setIssuerId(tenantId);
//
//			ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date",
//					tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime);
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime.plusHours(1));
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), ort);
//		}
//	}
//
//	public void generateTaskEinsatz(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort) {
//		for (int i = 0; i < num; i++) {
//			LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
//			TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository
//					.findByNameAndTenantId("Einsatz", tenantId);
//			TaskClassInstance ti = (TaskClassInstance) (TaskClassInstance) classInstanceService
//					.newClassInstance(volunteer, taskDef.getId(), tenantId);
//			ti.setIssuerId(tenantId);
//
//			ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date",
//					tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
//
//			randomDateTime.plusHours(1);
//			randomDateTime.plusMinutes(30);
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), ort);
//		}
//	}
//
//	public void generateTaskDienst(String tenantId, User volunteer, int num, RolesAmbulanceService role, String ort) {
//		for (int i = 0; i < num; i++) {
//			LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
//			TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository
//					.findByNameAndTenantId("Dienst", tenantId);
//			TaskClassInstance ti = (TaskClassInstance) (TaskClassInstance) classInstanceService
//					.newClassInstance(volunteer, taskDef.getId(), tenantId);
//			ti.setIssuerId(tenantId);
//
//			ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date",
//					tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), randomDateTime.plusHours(12).format(dateFormatter));
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
//
//			cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
//			classInstanceService.setProperty(ti, cp.getId(), ort);
//		}
//	}
//}

package at.jku.cis.iVolunteer.test.data;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;
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
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class TestDataRK {
		
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private ClassPropertyService classPropertyService;
	
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private VolunteerRepository volunteerRepository;	
	@Autowired private TestDataClasses testDataClasses;
	
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
		DISPONENT("Disponent"), EINSATZLENKER("Einsatzlenker"), SANITÄTER("Sanitäter"), AUSZUBILDENDER("Auszubildender");
		
		private String description;
	
		RolesAmbulanceService(String description) {
			this.description = description;
		}
			
		public String getDescription() {
			return description;
		}
	}
		
	public void load() {
		
		// create user data
		createUserData();
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
        //Store a random seed
        long seed = rand.nextLong();
        Random generator = new Random(seed);
  	  	LocalTime time = LocalTime.MIN.plusSeconds(generator.nextLong());
        return LocalDateTime.of(year, month, day, time.getHour(), 0);
    }
	
	public void createUserData() {
			String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
			Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
			if (volunteer == null) return;
			AchievementClassDefinition certClass = (AchievementClassDefinition) classDefinitionRepository.
					findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
			classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
			certClass = (AchievementClassDefinition) classDefinitionRepository.
					findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
			classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
			certClass = (AchievementClassDefinition) classDefinitionRepository.
					findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
			classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
			certClass = (AchievementClassDefinition) classDefinitionRepository.
					findByNameAndTenantId(TestDataClasses.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
			classInstanceService.newClassInstance(volunteer, certClass.getId(), tenantId);
					
			generateTaskAusfahrt(tenantId, volunteer, 999, RolesAmbulanceService.EINSATZLENKER, "Linz");
			generateTaskAusfahrt(tenantId, volunteer, 100, RolesAmbulanceService.EINSATZLENKER, "Wels");
			generateTaskAusfahrt(tenantId, volunteer, 21, RolesAmbulanceService.SANITÄTER, "Linz");
			
			generateTaskEinsatz(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Wels");
			generateTaskEinsatz(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
			generateTaskEinsatz(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Enns");
			
			generateTaskDienst(tenantId, volunteer, 10, RolesAmbulanceService.DISPONENT, "Linz");
			generateTaskDienst(tenantId, volunteer, 30, RolesAmbulanceService.EINSATZLENKER, "Linz");
			generateTaskDienst(tenantId, volunteer, 20, RolesAmbulanceService.SANITÄTER, "Linz");
		}
		
		private void generateTaskAusfahrt(String tenantId, Volunteer volunteer, int num, RolesAmbulanceService role, String ort) {
			for (int i = 0; i < num; i++) {
	            LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
	            TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Ausfahrt", tenantId);
	            TaskClassInstance ti = (TaskClassInstance) classInstanceService.newClassInstance(volunteer, taskDef.getId(), tenantId);
	            ti.setIssuerId(tenantId);
	            
	            ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime);
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime.plusHours(1));
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), ort);
	        }
		}
		
		private void generateTaskEinsatz(String tenantId, Volunteer volunteer, int num, RolesAmbulanceService role, String ort) {
			for (int i = 0; i < num; i++) {
	            LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
	            TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Einsatz", tenantId);
	            TaskClassInstance ti = (TaskClassInstance) (TaskClassInstance) classInstanceService.newClassInstance(volunteer, taskDef.getId(), tenantId);
	            ti.setIssuerId(tenantId);
	            
	            ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
	            
	            randomDateTime.plusHours(1);
	            randomDateTime.plusMinutes(30);
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), ort);
	        }
		}
		
		private void generateTaskDienst(String tenantId, Volunteer volunteer, int num, RolesAmbulanceService role, String ort) {
			for (int i = 0; i < num; i++) {
	            LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
	            TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Dienst", tenantId);
	            TaskClassInstance ti = (TaskClassInstance)(TaskClassInstance) classInstanceService.newClassInstance(volunteer, taskDef.getId(), tenantId);
	            ti.setIssuerId(tenantId);
	            
	            ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Starting Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime.format(dateFormatter));
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), randomDateTime.plusHours(12).format(dateFormatter));
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), role.getDescription());
	            
	            cp = classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId);
	            classInstanceService.setProperty(ti, cp.getId(), ort);
	        }
		}
}

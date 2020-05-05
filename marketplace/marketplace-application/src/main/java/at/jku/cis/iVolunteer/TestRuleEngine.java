package at.jku.cis.iVolunteer;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;
import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerService.DrivingLevel;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerService.LicenseType;
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
public class TestRuleEngine {
	
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ContainerRuleEntryRepository containerRuleEntryRepository;
	@Autowired private VolunteerService volunteerService;
	@Autowired private RuleService ruleService;
	
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private HelpSeekerRepository helpSeekerRepository;
	
	private static final String CERTIFICATE_SEF_MODUL1 = "SEF-Modul 1";
	private static final String CERTIFICATE_SEF_MODUL2 = "SEF-Modul 2";
	private static final String CERTIFICATE_SEF_AUSFORTBILDUNG = "SEF Aus- und Fortbildung";
	private static final String CERTIFICATE_SEF_WORKSHOP = "SEF Workshop";
	private static final String CERTIFICATE_SEF_TRAINING_NOTARZT = "SEF – Theorie- und Praxistraining Notarzt";
	private static final String CERTIFICATE_SEF_LADEGUTSICHERUNG = "SEF Ladegutsicherung LKW";
	private static final String CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG = "SEF Theorietrainerausbildung";
	
	private static final String TASK_RK_RETTUNGSDIENST = "Rettungsdienst";
	private static final String TASK_RK_AUSFAHRT = "Ausfahrt";
	private static final String TASK_RK_EINSATZ = "Einsatz";

	
	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
	private static final String RKWILHERING = "RK Wilhering";
	
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
	
	public void setup(){
		// updatePersonRoleRK();
		// prepare date 
		createGeneralCompetences();
		createClassCertificatesRK();
		createClassTasksRK();
		
		cleanUp();
		
		// create user data
		createUserData();
		// create test cases
		// testCaseAddDrivingLicense();
		// testCaseAddDrivingCompetenceByRules(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
		// testCaseAddDrivingCompetenceByRules(coreTenantRestClient.getTenantIdByName(RKWILHERING));
		// testCaseImproveDrivingCompetenceRKL2();
		testCaseImproveDrivingCompetenceRKL3();
	}
	
	/** 
	 * Car Driving: Level 1 --> Level 2
	 */
	public void testCaseImproveDrivingCompetenceRKL2() {
		System.out.println("==============================================================================================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteer = volunteerRepository.findByUsername("CVoj");
		//
		executeRule(volunteer, tenantId, "ivol-test", "competence-driving", ruleCompetenceDriving);
		// reset assets
		CompetenceClassInstance ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		// cleanUp(); XXX
	    volunteerService.setProperty(ci, "Driving Level", VolunteerService.DrivingLevel.LEVEL1);
	    deleteInstances(volunteer, tenantId, CERTIFICATE_SEF_MODUL1);
	    deleteInstances(volunteer, tenantId, CERTIFICATE_SEF_MODUL2);
	    
		cleanUpContainer(tenantId, "ivol-test");
		// 
		assertFalse("Kompetenz noch < Level 2 " + RKWILHERING + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, ci.getProperty("Driving Level").equals("LEVEL1")) ; 
		
		// create new assets
		volunteerService.addClassInstance(volunteer, tenantId, CERTIFICATE_SEF_MODUL1);
		volunteerService.addClassInstance(volunteer, tenantId, CERTIFICATE_SEF_MODUL2);
		
	//	ClassInstance cii = volunteerService.getClassInstance(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR).get(0); 
	//	  volunteerService.addPropertyValue(ci,VolunteerService.PROPERTY_EVIDENCE, volunteerService.getClassInstance(volunteer, tenantId,"SEF-Modul 1" ).get(0)));
	//  vs.addPropertyValue(ci,VolunteerService.PROPERTY_EVIDENCE, vs.getAchievement(v, t.getId(), vs.getClassInstance(v, t.getId(),"SEF-Modul 2" )));
	//	  vs.setProperty(ci,VolunteerService.PROPERTY_DRIVING_LEVEL, VolunteerService.DrivingLevel.LEVEL2);:
		//volunteerService.addClassInstance(volunteer, tenantId, CERTIFICATE_SEF_MODUL2);
		
		addRule2Container(tenantId, marketplaceService.getMarketplaceId(), "ivol-test", "competence-driving", ruleCompetenceDriving);
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		
		// check whether competence level was upgraded to level 2
		// refresh competence
		ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		// check properties
		assertEquals("LEVEL2", ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		// check wheter modules are saved as evidence for level 2
		// get class instances for certificate SEF-Modul 1 and SEF-Modul 2
	    ClassInstance ciSEFM1 = volunteerService.getClassInstance(volunteer, tenantId, CERTIFICATE_SEF_MODUL1).get(0);
		ClassInstance ciSEFM2 = volunteerService.getClassInstance(volunteer, tenantId, CERTIFICATE_SEF_MODUL2).get(0);
		
		assertFalse(ci.getProperty(VolunteerService.PROPERTY_EVIDENCE).getValues() == null);
		assertTrue("Zertifikat SEF-Modul 1 nicht in Evidenz! ", volunteerService.propertyValuesContain(ci.getProperty(VolunteerService.PROPERTY_EVIDENCE), ciSEFM1));
		assertTrue("Zertifikat SEF-Modul 2 nicht in Evidenz! ", volunteerService.propertyValuesContain(ci.getProperty(VolunteerService.PROPERTY_EVIDENCE), ciSEFM2));
		
		System.out.println("==============================================================================================");
	}
	
	/** 
	 * Car Driving: Level 2 --> Level 3
	 */
	public void testCaseImproveDrivingCompetenceRKL3() {
		System.out.println("==============================================================================================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteer = volunteerRepository.findByUsername("CVoj");
		//
		// prepare the data
		testCaseImproveDrivingCompetenceRKL2();
			    
		cleanUpContainer(tenantId, "ivol-test");
		
		CompetenceClassInstance ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		// 
		assertFalse("Kompetenz noch < Level 2 " + RKWILHERING + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, ci.getProperty("Driving Level").equals("LEVEL2")) ; 
		
		// addRule2Container(tenantId, marketplaceService.getMarketplaceId(), "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		
		// check whether competence level was upgraded to level 2
		// refresh competence
		ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		// check properties
		assertEquals("LEVEL3", ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		System.out.println("==============================================================================================");
	}


	
	/** 
	 * Add driving license for user.
	 */
	public void testCaseAddDrivingLicense() {
		System.out.println("=========== test case add driving license =========================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		volunteer = volunteerRepository.findByUsername("CVoj");
		
		// 
		assertFalse("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_BUS, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_TRUCK, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		
		// create new assets
		volunteerService.addClassInstance(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		volunteerService.addClassInstance(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS);
		volunteerService.addClassInstance(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_TRUCK);
		volunteerService.addClassInstance(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE);
		
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-driving", ruleDriverLicense);
		
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_BUS, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_TRUCK, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		
		// check properties
		CompetenceClassInstance ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		assertEquals(ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0), "LEVEL1");
		
		System.out.println("==============================================================================================");
	}
	
	/** 
	 * After adding driving licenses,
	 * driving competence are added automatically by rules
	 */
	public void testCaseAddDrivingCompetenceByRules(String tenantId) {
		System.out.println("================== test cases add competence by rules ============================");
		Tenant tenant = coreTenantRestClient.getTenantById(tenantId);
		Volunteer volunteer;
		// Feuerwehr
		volunteer = volunteerRepository.findByUsername("CVoj");
		//
		cleanUp();
		cleanUpContainer(tenantId, "test-comp1");
		// ruleService.printContainers();
		// 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_BUS, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_TRUCK, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		
		// create new assets
		volunteerService.addLicense(volunteer, tenantId, LicenseType.B);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.C);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.D);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.A);
		
		// 
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-driving", ruleCompetenceDriving);
		
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_CAR, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_BUS, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_TRUCK, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS)) ; 
		
		// check properties
		CompetenceClassInstance ci = volunteerService.getCompetence(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR);
		assertEquals(ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0), "LEVEL1");
		// DrivingLevel level = DrivingLevel.valueOf((String)ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		// System.out.println("............................................. " + level.getDescription());
		// printAllAssets(volunteer, tenantId);
		
		System.out.println("==============================================================================================");
	}
	
	public void executeRule(Volunteer volunteer, String tenantId, String container, String ruleName, String ruleContent) {
		String marketplaceId = marketplaceService.getMarketplaceId();
		addRule2Container(tenantId, marketplaceId, container, ruleName, ruleContent);
		ruleService.refreshContainer();
		ruleService.printContainers();
		ruleService.executeRules(tenantId, container, volunteer.getId());
	}
	
	public void createUserData() {
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		volunteer = volunteerRepository.findByUsername("CVoj");
		// create new assets
		volunteerService.addLicense(volunteer, tenantId, LicenseType.B);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.C);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.D);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.A);
	
		// Rotes Kreuz
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.B);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.C);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.D);
		volunteerService.addLicense(volunteer, tenantId, LicenseType.A);
		
		generateTasks(tenantId, volunteer, TASK_RK_AUSFAHRT, 200);
	}
	
	private void generateTasks(String tenantId, Volunteer volunteer, String className, int num) {
		for (int i = 0; i < num; i++) {
            LocalDateTime randomDateTime = createRandomDateTime(2000, 2019);
            TaskClassInstance ti = (TaskClassInstance) volunteerService.addClassInstance(volunteer, tenantId, className);
            volunteerService.setProperty(ti, "Start Date", randomDateTime);
            volunteerService.setProperty(ti, "End Date", randomDateTime.plusHours(1));
            volunteerService.setProperty(ti, "role", RolesAmbulanceService.EINSATZLENKER);
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
        //Store a random seed
        long seed = rand.nextLong();
        Random generator = new Random(seed);
  	  	LocalTime time = LocalTime.MIN.plusSeconds(generator.nextLong());
        return LocalDateTime.of(year, month, day, time.getHour(), 0);
    }
	public void cleanUp() {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		Volunteer volunteer = volunteerRepository.findByUsername("CVoj");
		
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE);
		
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE);
	}
	
	private void deleteInstances(Volunteer volunteer, String tenantId, String className) {
		ClassDefinition classComp = volunteerService.getClassDefinition(className, tenantId); // classDefinitionRepository.findByNameAndTenantId(compName, tenantId);	
		if (classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classComp.getId(), tenantId) != null) {
			List<ClassInstance> list = classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), classComp.getId(), tenantId);
            list.forEach(ci -> {
            	classInstanceRepository.delete(ci.getId());
            });
		}
	}
	
	private PropertyDefinition<Object> obtainProperty(String name, String tenantId) {
		List<PropertyDefinition<Object>> pdList = propertyDefinitionRepository.getByNameAndTenantId(name, tenantId);
		PropertyDefinition<Object> pd;
		if (pdList.size() == 0) {
			pd = new PropertyDefinition<Object>(name, PropertyType.TEXT, tenantId);
		    propertyDefinitionRepository.save(pd);
	    } else
	    	pd = pdList.get(0);
		
		return pd;
	}
	
	private PropertyDefinition<Object> obtainProperty(String name, String tenantId, List<Object> allowedValues) {
		PropertyDefinition<Object> pd = obtainProperty(name, tenantId);
		pd.setAllowedValues(allowedValues);
		propertyDefinitionRepository.save(pd);
		return pd;
	}
	
	private void updatePersonRoleRK() {
		HelpSeeker helpSeeker = helpSeekerRepository.findByUsername("OERK");

		// adding Dienstart to RK - roles
		List<PropertyDefinition<Object>> pdList = propertyDefinitionRepository.getByNameAndTenantId("Type of Service", helpSeeker.getTenantId());
		PropertyDefinition pd;
		if (pdList.size() == 0) {
			pd = new PropertyDefinition<Object>("Type of Service", PropertyType.TEXT, helpSeeker.getTenantId());
			pd.setAllowedValues(new ArrayList<String>(Arrays.asList("Hauptamt", "Ehrenamt", "Zivildienst")));
		    propertyDefinitionRepository.save(pd);
	    } else
	    	pd = pdList.get(0);
		List<ClassDefinition> functionDefinition = classDefinitionRepository.getByClassArchetypeAndTenantId(ClassArchetype.FUNCTION, helpSeeker.getTenantId());
		for (ClassDefinition fd: functionDefinition) {
			Boolean found = false;
			for (ClassProperty<Object> p: fd.getProperties()) {
				if (p.getName().equals(pd.getName()))
					found = true;
			}
			if (!found)
				fd.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		}
		classDefinitionRepository.save(functionDefinition);
		
	}
	
	private ClassDefinition obtainClass(String tenantId, String name, ClassArchetype classArchetype) {
		ClassDefinition a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		if (a0 == null) {	
			switch (name) {
			case VolunteerService.CERTIFICATE_DRIVING_LICENSE: createCertificateHead(tenantId, name); break;
		//	case VolunteerService.COMPETENCE_DRIVING: createCompetenceHead(tenantId, name); break;
			case "Training": createCertificateHead(tenantId, name); break;
			case TASK_RK_RETTUNGSDIENST: createTaskHead(tenantId, name); break;
			}
			a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		}
		return a0;
	}
	
	private boolean classDefinitonExists(String tenantId, String name) {
		return classDefinitionRepository.findByNameAndTenantId(name, tenantId) != null;
	}
	
	private ClassDefinition obtainClass(String tenantId, String name, ClassDefinition parent) {
		System.out.println(" obtain class " + name + ", tenantId: " + tenantId + ", parent: " + parent.getName());
		ClassDefinition a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		if (a0 == null) {	
			createClass(tenantId, name, parent);
			a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		}
		return a0;
	}
	
	public void createGeneralCompetences() {
		createDrivingSkills(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
		createDrivingSkills(coreTenantRestClient.getTenantIdByName(RKWILHERING));
	}
	
	public void createClassCertificatesRK() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		// Training certificates
		AchievementClassDefinition certClassTraining = (AchievementClassDefinition) obtainClass(tenantId, "Training", ClassArchetype.ACHIEVEMENT);
		// Certificate SEF-MODUL 1
		System.out.println(" new Training class " + certClassTraining);
		AchievementClassDefinition certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_MODUL1, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Perfektionstraining für neue Einsatzlenker/innen (SEF-MODUL 1)");
		// 
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_MODUL2, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Theorie- & Praxistraining für erfahrene Einsatzlenker/innen (SEF-MODUL 2)");
  		//
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_AUSFORTBILDUNG, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Aus- und Fortbildung für SEF-Praxistrainer/innen");
		//
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_WORKSHOP, certClassTraining);
		setClassProperty(certClass, "Description", "SEF Workshop");
		//
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_TRAINING_NOTARZT, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Theorie- und Praxistraining für Notarztdienste");
		//
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_LADEGUTSICHERUNG, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Ladegutsicherung für Rotkreuz LKW-Lenker/innen");
		//
		certClass = (AchievementClassDefinition) obtainClass(tenantId, CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, certClassTraining);
		setClassProperty(certClass, "Description", "SEF – Theorietrainerausbildung");	
	}
	
	public void createClassRolesRK() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		// Function
		FunctionClassDefinition functionClassAmbulanceService = (FunctionClassDefinition) obtainClass(tenantId, "Ambulance Service", ClassArchetype.TASK_HEAD);
		// Certificate SEF-MODUL 1
		System.out.println(" new Task class " + functionClassAmbulanceService);
		TaskClassDefinition certClass = (TaskClassDefinition) obtainClass(tenantId, "Rettungssanitäter", functionClassAmbulanceService);
		//setClassProperty(certClass, "Description", "Rettungseinsatz");
		// 
		certClass = (TaskClassDefinition) obtainClass(tenantId, "Notfallsanitäter", functionClassAmbulanceService);
		//setClassProperty(certClass, "Description", "Sanitätseinsatz");
  		//
	}
	
	public void createClassTasksRK() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		// Task
		TaskClassDefinition taskClassAmbulanceService = (TaskClassDefinition) obtainClass(tenantId, TASK_RK_RETTUNGSDIENST, ClassArchetype.TASK_HEAD);
		
		System.out.println(" new Task class " + taskClassAmbulanceService);
		TaskClassDefinition taskClass = (TaskClassDefinition) obtainClass(tenantId, TASK_RK_EINSATZ, taskClassAmbulanceService);
		setClassProperty(taskClass, "Description", "Rettungseinsatz");
		taskClass.getProperty("role").setAllowedValues(Arrays.asList(RolesAmbulanceService.DISPONENT, RolesAmbulanceService.EINSATZLENKER,
				                                                     RolesAmbulanceService.SANITÄTER, RolesAmbulanceService.AUSZUBILDENDER));

		classDefinitionRepository.save(taskClass);
		// 
		taskClass = (TaskClassDefinition) obtainClass(tenantId, TASK_RK_AUSFAHRT, taskClassAmbulanceService);
		setClassProperty(taskClass, "Description", "Sanitätseinsatz");
		taskClass.getProperty("role").setAllowedValues(Arrays.asList(RolesAmbulanceService.EINSATZLENKER,
                													 RolesAmbulanceService.SANITÄTER, RolesAmbulanceService.AUSZUBILDENDER));
		classDefinitionRepository.save(taskClass);
		//
	}
	
	private void setClassProperty(ClassDefinition classDefinition, String propertyName, String value) {
		System.out.println("property: " + classDefinition.getProperty(propertyName));
		classDefinition.getProperty(propertyName).setDefaultValues(Arrays.asList(value));
		classDefinitionRepository.save(classDefinition);
	}
	
	public void createDrivingSkills(String tenantId) {
		// certificate --> driving license
		ClassDefinition certClassDrivingLicense = obtainClass(tenantId, VolunteerService.CERTIFICATE_DRIVING_LICENSE, ClassArchetype.ACHIEVEMENT_HEAD);
		ClassDefinition certClassDrivingLicenseCar = obtainClass(tenantId, VolunteerService.CERTIFICATE_DRIVING_LICENSE_CAR, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseTruck = obtainClass(tenantId, VolunteerService.CERTIFICATE_DRIVING_LICENSE_TRUCK, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseBus = obtainClass(tenantId, VolunteerService.CERTIFICATE_DRIVING_LICENSE_BUS, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseMotorcycle = obtainClass(tenantId, VolunteerService.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, certClassDrivingLicense);
		
		// grouping and hierarchy in certificate "driving license"
		List<Inheritance> iList = new ArrayList<Inheritance>();
		System.out.println(" ----------- > " + certClassDrivingLicense + " " + certClassDrivingLicenseCar); 
		Inheritance i1 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseCar.getId(), certClassDrivingLicense.getId());
		i1.setId("drivingCarLicense");
		iList.add(i1);
		i1  = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseTruck.getId(), certClassDrivingLicense.getId());
		i1.setId("drivingTruckLicense");
		iList.add(i1);
		i1 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseBus.getId(), certClassDrivingLicense.getId());
		i1.setId("drivingBusLicense");
		iList.add(i1);
		i1 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseMotorcycle.getId(), certClassDrivingLicense.getId());
		i1.setId("drivingMotorcycleLicense");
		iList.add(i1);
		for (Inheritance i: iList) {
			if (!relationshipRepository.exists(i.getId()))
				relationshipRepository.save(i);
		}
		
		// competences --> driving
		ClassDefinition compClassDriving = createDrivingCompetenceHead(tenantId);
		ClassDefinition compClassDrivingCar = obtainClass(tenantId, VolunteerService.COMPETENCE_DRIVING_CAR, compClassDriving); 
		ClassDefinition compClassDrivingTruck = obtainClass(tenantId, VolunteerService.COMPETENCE_DRIVING_TRUCK, compClassDriving); 
		ClassDefinition compClassDrivingBus = obtainClass(tenantId, VolunteerService.COMPETENCE_DRIVING_BUS, compClassDriving); 
		ClassDefinition compClassDrivingMotorcycle = obtainClass(tenantId, VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE, compClassDriving);

		// competence driving needs evidence of certificate (driving license)
		List<Association> aList = new ArrayList<Association>();
		Association a1 = new Association(compClassDrivingCar.getId(), certClassDrivingLicense.getId(), AssociationCardinality.ONE, AssociationCardinality.ONE);
		a1.setId("evidenceByDriverLicenseB");
		aList.add(a1);
		a1 = new Association(compClassDrivingTruck.getId(), certClassDrivingLicense.getId(), AssociationCardinality.ONE, AssociationCardinality.ONE);
		a1.setId("evidenceByDriverLicenseC");
		aList.add(a1);
		a1 = new Association(compClassDrivingBus.getId(), certClassDrivingLicense.getId(), AssociationCardinality.ONE, AssociationCardinality.ONE);
		a1.setId("evidenceByDriverLicenseD");
		aList.add(a1);
		a1 = new Association(compClassDrivingMotorcycle.getId(), certClassDrivingLicense.getId(), AssociationCardinality.ONE, AssociationCardinality.ONE);
		a1.setId("evidenceByDriverLicenseA");
		aList.add(a1);
		
		for (Association a: aList) {
			if (!relationshipRepository.exists(a.getId()))
				relationshipRepository.save(a);
		}
		// grouping and hierarchy in competence "driving"
		iList = new ArrayList<Inheritance>();
		i1 = new Inheritance(compClassDriving.getId(), compClassDrivingCar.getId(), compClassDriving.getId());
		i1.setId("drivingCar");
		iList.add(i1);
		i1  = new Inheritance(compClassDriving.getId(), compClassDrivingTruck.getId(), compClassDriving.getId());
		i1.setId("drivingTruck");
		iList.add(i1);
		i1 = new Inheritance(compClassDriving.getId(), compClassDrivingBus.getId(), compClassDriving.getId());
		i1.setId("drivingBus");
		iList.add(i1);
 		i1 = new Inheritance(compClassDriving.getId(), compClassDrivingMotorcycle.getId(), compClassDriving.getId());
		i1.setId("drivingMotorcycle");
		iList.add(i1);
		for (Inheritance i: iList) {
			if (!relationshipRepository.exists(i.getId()))
				relationshipRepository.save(i);
		}
		
		// printClassDefinitions(tenantId);
	}
	
	private void printAllAssets(Volunteer volunteer, String tenantId) {
		System.out.println("all assets from " + volunteer.getUsername() + ": ");
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.ACHIEVEMENT);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.COMPETENCE);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.FUNCTION);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.TASK);
	}
	
	private void printArchetypeAssets(Volunteer volunteer, String tenantId, ClassArchetype classArchetype) {
		System.out.println("..... " + classArchetype.getArchetype() + ": ");
		List<ClassDefinition> classes = classDefinitionRepository.getByClassArchetypeAndTenantId(classArchetype, tenantId);
		for (ClassDefinition cd: classes) {
			List<ClassInstance> assets = classInstanceRepository.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), cd.getId(), tenantId);
			System.out.println("------- class: " + cd.getName() + " number of assets == " + assets.size());
			assets.forEach(ci -> {
				System.out.println(".............. " + ci.getName());
			});
		}
	}
	
	private void printClassDefinitions(String tenantId) {
		List<ClassDefinition> classList = classDefinitionRepository.findByTenantId(tenantId);
		classList.forEach(c -> {
			System.out.println(" Class: " + c.getName() + ", parent: " + c.getParentId());
		});
	}
	
	private ClassInstance createInstance(ClassDefinition target, Volunteer volunteer, String tenantId) {
		ClassInstance ci;
		switch (target.getClassArchetype()) {
		case ACHIEVEMENT: ci = new AchievementClassInstance(); break;
		case COMPETENCE: ci = new CompetenceClassInstance(); break;
		default: ci = null; 
		}
		ci.setName(target.getName());
		ci.setClassDefinitionId(target.getId());
		ci.setUserId(volunteer.getId());
		ci.setMarketplaceId(marketplaceService.getMarketplaceId());
		ci.setTenantId(tenantId);
		// copy properties from target class
		List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propLicenseList = target.getProperties();
		for (ClassProperty<Object> cp: propLicenseList) {
			propInstList.add(classPropertyToPropertyInstanceMapper.toTarget(cp));
		}
		ci.setProperties(propInstList);
        classInstanceRepository.save(ci);
		return ci;
	}
	
	private ClassDefinition createDrivingCompetenceHead(String tenantId) {
		CompetenceClassDefinition c1 = new CompetenceClassDefinition();
		// c1.setId("test1");
		c1.setName(VolunteerService.COMPETENCE_DRIVING);
		c1.setClassArchetype(ClassArchetype.COMPETENCE_HEAD);
		c1.setTenantId(tenantId);
		c1.setRoot(true);
		// set properties
		List<Object> levelValues = Arrays.asList(VolunteerService.DrivingLevel.LEVEL1, VolunteerService.DrivingLevel.LEVEL2, VolunteerService.DrivingLevel.LEVEL3, VolunteerService.DrivingLevel.LEVEL4); 
		PropertyDefinition<Object> pdLevel = obtainProperty(VolunteerService.PROPERTY_DRIVING_LEVEL, tenantId, levelValues);
		PropertyDefinition<Object> pdEvidence = obtainProperty(VolunteerService.PROPERTY_EVIDENCE, tenantId);
		c1.setProperties(new ArrayList<ClassProperty<Object>>());
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdEvidence));
		classDefinitionRepository.save(c1);
		return c1;
	}
	
	
	
	private void createCertificateHead(String tenantId, String name) {
		ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("PersonCertificate", tenantId);
		
		AchievementClassDefinition a = new AchievementClassDefinition();
		a.setMarketplaceId(marketplaceService.getMarketplaceId());
		a.setName(name);
		a.setParentId(certificateClass.getId());
		a.setTimestamp(new Date());
		a.setTenantId(tenantId);
		a.setClassArchetype(ClassArchetype.ACHIEVEMENT_HEAD);
		a.setProperties(new ArrayList<ClassProperty<Object>>());
		PropertyDefinition<Object> pdDescription = obtainProperty("Description", tenantId);
		a.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
		classDefinitionRepository.save(a);
		System.out.println("************************************************** " + a.getId());
	}
	
	private void createTaskHead(String tenantId, String name) {
		ClassDefinition taskClass = classDefinitionRepository.findByNameAndTenantId("PersonTask", tenantId);
		
		TaskClassDefinition a = new TaskClassDefinition();
		a.setMarketplaceId(marketplaceService.getMarketplaceId());
		a.setName(name);
		a.setParentId(taskClass.getId());
		a.setTimestamp(new Date());
		a.setTenantId(tenantId);
		a.setClassArchetype(ClassArchetype.TASK_HEAD);
		a.setProperties(new ArrayList<ClassProperty<Object>>());
		PropertyDefinition<Object> pd = obtainProperty("Description", tenantId);
		a.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		pd = obtainProperty("role", tenantId);
		a.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		pd = obtainProperty("Start Date", tenantId);
		a.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		pd = obtainProperty("End Date", tenantId);
		a.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		classDefinitionRepository.save(a);
		System.out.println("************************************************** " + a.getName());
	}
	
	private void createClass(String tenantId, String name, ClassDefinition parent) {
		System.out.println("============================================== " + tenantId + " " + name + " " + parent);
		//+ " archetype.parent: " 		+ parent.getClassArchetype());
		ClassDefinition a = newClassDefinition(parent);
		a.setMarketplaceId(marketplaceService.getMarketplaceId());
		a.setName(name);
		a.setParentId(parent.getId());
		a.setTimestamp(new Date());
		a.setTenantId(tenantId);
		parent.getProperties().forEach(p -> {
			System.out.println("-------------------> " + p.getName());
		});
		a.setProperties(parent.getProperties());
		classDefinitionRepository.save(a);
		System.out.println("************************************************** " + a.getId());
	}
	
	private ClassDefinition newClassDefinition(ClassDefinition parent) {
		ClassDefinition cd = null;
		switch(parent.getClassArchetype()) {
		case ACHIEVEMENT:
		case ACHIEVEMENT_HEAD:
				cd = new AchievementClassDefinition();
				cd.setClassArchetype(ClassArchetype.ACHIEVEMENT); 
				break;
		case COMPETENCE:
		case COMPETENCE_HEAD:
				cd = new CompetenceClassDefinition(); 
		        cd.setClassArchetype(ClassArchetype.COMPETENCE);
		        break;
		case TASK:
		case TASK_HEAD:
				cd = new TaskClassDefinition();
		        cd.setClassArchetype(ClassArchetype.TASK); 
		        break;
		default:
			break;
		}
		return cd;
	}
	
	private void cleanUpContainer(String tenantId, String container) {
		List<ContainerRuleEntry> containerEntries = containerRuleEntryRepository.getByTenantIdAndContainer(tenantId, container);
		containerEntries.forEach(r -> {
			containerRuleEntryRepository.delete(r.getId());
		});
	}
	
	private void addRule2Container(String tenantId, String marketplaceId, String container, String name, String content) {
		ContainerRuleEntry containerRule = new ContainerRuleEntry(tenantId, marketplaceId, container, name, content);
		containerRuleEntryRepository.insert(containerRule);
	}
	
	public void initTestData(String tenantId) {
		String marketplaceId = marketplaceService.getMarketplaceId();
		addRule2Container(tenantId, marketplaceId, "general", "hello-world", ruleHelloWorld);
		addRule2Container(tenantId, marketplaceId, "math", "fibonacci", ruleFibonacci);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "print-info", ruleVolPrintInfo);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "check-mindestalter", ruleVolMindestalter);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "check-hoechstalter", ruleVolHoechstalter);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "check-position", ruleInitPosition);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "check-2-conditions", rule2Conditions);
		addRule2Container(tenantId, marketplaceId, "ivol-test", "check-license-B", ruleDriverLicense);
		// addRule2Container(tenantId, marketplaceId, "ivol-test", "competence-driving", ruleCompetenceDriving);
		
	}
	
	public final static String ruleFibonacci =  "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" +
	     	"import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Fibonacci;\r\n" + 
			"dialect \"mvel\"\r\n" + 
			"\r\n" + 
			"rule Recurse\r\n" + 
			"    salience 10\r\n" + 
			"    when\r\n" + 
			"        f : Fibonacci ( value == -1 )\r\n" + 
			"        not ( Fibonacci ( sequence == 1 ) )    \r\n" + 
			"    then\r\n" + 
			"        insert( new Fibonacci( f.sequence - 1 ) );\r\n" + 
			"        System.out.println( \"recurse for \" + f.sequence );\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule Bootstrap\r\n" + 
			"    when\r\n" + 
			"        f : Fibonacci( sequence == 1 || == 2, value == -1 ) // this is a multi-restriction || on a single field\r\n" + 
			"    then \r\n" + 
			"        modify ( f ){ value = 1 };\r\n" + 
			"        System.out.println( f.sequence + \" == \" + f.value );\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule Calculate\r\n" + 
			"    when\r\n" + 
			"        f1 : Fibonacci( s1 : sequence, value != -1 ) // here we bind sequence\r\n" + 
			"        f2 : Fibonacci( sequence == (s1 + 1 ), value != -1 ) // here we don't, just to demonstrate the different way bindings can be used\r\n" + 
			"        f3 : Fibonacci( s3 : sequence == (f2.sequence + 1 ), value == -1 )              \r\n" + 
			"    then    \r\n" + 
			"        modify ( f3 ) { value = f1.value + f2.value };\r\n" + 
			"        System.out.println( s3 + \" == \" + f3.value ); // see how you can access pattern and field  bindings\r\n" + 
			"end \r\n";
	
	public final static String ruleHelloWorld =  "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" +
	        "import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Message;\r\n" + 
			"global java.util.List list\r\n" + 
			" \r\n" + 
			"rule \"Hello World\"\r\n" + 
			"    dialect \"mvel\"\r\n" + 
			"    when\r\n" + 
			"        m : Message( status == Message.HELLO, message : message )\r\n" + 
			"    then\r\n" + 
			"        System.out.println( message );\r\n" + 
			"//        modify ( m ) { setMessage( \"Goodbyte cruel world\" ),\r\n" + 
			"//                       setStatus( Message.GOODBYE ) };\r\n" + 
			"    modify ( m ) { message = \"Goodbye cruel world\",\r\n" + 
			"                   status = Message.GOODBYE };\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule \"Good Bye\"\r\n" + 
			"    dialect \"java\"\r\n" + 
			"    when\r\n" + 
			"        Message( status == Message.GOODBYE, message : message )\r\n" + 
			"    then\r\n" + 
			"        System.out.println( message );\r\n" + 
			"end";
	
	public final static String ruleVolPrintInfo = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule Test\r\n" + 
			"when\r\n" + 
			"  v : Volunteer ()\r\n" +
			"  vs: VolunteerService() \r\n" +
			"then\r\n" + 
			"  System.out.println(vs.currentAge(v));\r\n" + 
			"  System.out.println(v.getFirstname());\r\n" + 
			"end";
	
	public final static String ruleVolMindestalter = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule CheckMindestalter\r\n" + 
			"when\r\n" + 
			"  v : Volunteer ()\r\n" +
			"  vs: VolunteerService( currentAge(v) >= 18) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"Mindestalter ist erreicht\");\r\n" + 
			"end";
	
	public final static String ruleVolHoechstalter = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule CheckHoechstalter\r\n" + 
			"when\r\n" + 
			"  v : Volunteer ()\r\n" +
			"  vs: VolunteerService( currentAge(v) < 65) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"Höchstalter nicht überschritten\");\r\n" + 
			"end";
	
	public final static String ruleInitPosition = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule PositionInit\r\n" + 
			"when\r\n" + 
			"  v : Volunteer (position == null)\r\n" +
			"then\r\n" + 
			" v.setPosition(\"Anfänger\");\r\n" +
			"  System.out.println(\" --> \" + v.getPosition());\r\n" + 
			"end\r\n" +
			"\r\n" +
			"rule PositionBeginner\r\n" + 
			"when\r\n" + 
			"  v : Volunteer (position == \"Anfänger\")\r\n" +
			"then\r\n" + 
			" v.setPosition(\"Fortgeschrittener\");\r\n" +
			"  System.out.println(\"Anfänger --> \" + v.getPosition());\r\n" + 
			"end\r\n" +
			"\r\n" +
			"rule PositionIntermediate\r\n" + 
			"when\r\n" + 
			"  v : Volunteer (position == \"Fortgeschrittener\")\r\n" +
			"then\r\n" + 
			" v.setPosition(\"Experte\");\r\n" +
			"  System.out.println(\"Fortgeschrittener --> \" + v.getPosition());\r\n" + 
			"end";

	public final static String rule2Conditions = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule CheckMAPosition\r\n" + 
			"when\r\n" + 
			"  v : Volunteer (position == \"Anfänger\")\r\n" +
			"  vs: VolunteerService( currentAge(v) >= 18) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"Mindestalter ist erreicht und Anfänger!\");\r\n" + 
			"end";
	
	public final static String ruleDriverLicense = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule CheckLicenseB\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.B) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein B!\");\r\n" + 
			"end\r\n" +
			"rule CheckLicenseC\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.C) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein C!\");\r\n" + 
			"end\r\n"+
			"rule CheckLicenseD\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.D) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein D!\");\r\n" + 
			"end\r\n"+
			"rule CheckLicenseA\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.A) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein A!\");\r\n" + 
			"end";
	
	public final static String ruleCompetenceDriving = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;\r\n" + 
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;\r\n"+
			"import java.util.Arrays;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule addCompDrivingCar\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.B) && " + 
			"      !hasCompetence(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR)) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein B und bekommt Kompetenz Driving Car!\");\r\n" + 
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR); \r\n" +
            "  vs.setProperty(ci,VolunteerService.PROPERTY_EVIDENCE, vs.getAchievement(v, t.getId(), VolunteerService.CERTIFICATE_DRIVING_LICENSE_CAR));\r\n"+
            "  vs.setProperty(ci,VolunteerService.PROPERTY_DRIVING_LEVEL, VolunteerService.DrivingLevel.LEVEL1);\r\n"+
			"end\r\n" +
			"rule addCompDrivingBus\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.C) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein C und bekommt Kompetenz Drivin Truck!\");\r\n" +
			"  vs.addClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_TRUCK); \r\n" +
			"end\r\n"+
			"rule addCompDrivingTruck\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.D) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein D und bekommt Kompetenz Driving Bus!\");\r\n" + 
			"  vs.addClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_BUS); \r\n" +
			"end\r\n"+
			"rule addCompDrivingMotorcycle\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasDriverLicense(v, t.getId(), VolunteerService.LicenseType.A) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein A und bekommt Kompetenz Driving Motorcycle!\");\r\n" + 
			"  vs.addClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_MOTORCYCLE); \r\n" +
			"end";
	
	public final static String ruleImproveDrivingCompetenceRK = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;\r\n"+
			"import java.util.Arrays;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule init\r\n" + 
			"salience 1" + 
		    "no-loop " +
		    "//No condition\r\n" + 
		    "when\r\n" +
			" v : Volunteer() \r\n" +
			" t : Tenant() \r\n" +
			" vs: VolunteerService(hasClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR) == true) \r\n" +
			"then\r\n" + 
			"  insert(vs.getClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR).get(0));\r\n" + 
			"end\r\n" +
			"rule improveCompDrivingCarL2 \r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  c : ClassInstance() \r\n" +
			"  vs: VolunteerService(c != null && \r\n" + 
			"          propertyValueEquals(c.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL), \"LEVEL1\") == true, \r\n"+
			"          hasClassInstance(v, t.getId(), \"SEF-Modul 1\") == true, \r\n" + 
			"          hasClassInstance(v, t.getId(), \"SEF-Modul 2\") == true) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat SEF-Modul 1 und 2 verbessert Kompetenz Driving Car!\");\r\n" + 
			"  ClassInstance ci = vs.getClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR).get(0); \r\n" +
			 " vs.setProperty(ci,VolunteerService.PROPERTY_DRIVING_LEVEL, VolunteerService.DrivingLevel.LEVEL2);\r\n"+
			 " vs.addPropertyValue(ci,VolunteerService.PROPERTY_EVIDENCE, vs.getClassInstance(v, t.getId(), \"SEF-Modul 1\").get(0));\r\n"+
			 " vs.addPropertyValue(ci,VolunteerService.PROPERTY_EVIDENCE, vs.getClassInstance(v, t.getId(),\"SEF-Modul 2\" ).get(0));\r\n"+
            "end\r\n" +
            "rule improveCompDrivingCarL3 \r\n" +  
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  c : ClassInstance() \r\n" +
			"  vs: VolunteerService(c != null, \r\n" + 
			"          propertyValueEquals(c.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL), \"LEVEL2\") == true,  \r\n"+
     		"          getFilteredInstancesByProperty(v, t.getId(), \"Ausfahrt\", \"role\", \"EINSATZLENKER\").size() > 100) \r\n"+
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat mehr als 100 Ausfahrten als Fahrer und verbessert Kompetenz Driving Car auf Level 3!\");\r\n" + 
			"  ClassInstance ci = vs.getClassInstance(v, t.getId(), VolunteerService.COMPETENCE_DRIVING_CAR).get(0); \r\n" +
			 " vs.setProperty(ci,VolunteerService.PROPERTY_DRIVING_LEVEL, VolunteerService.DrivingLevel.LEVEL3);\r\n"+
			 " vs.addPropertyValue(ci,VolunteerService.PROPERTY_EVIDENCE, \"Anzahl Ausfahrten > 100\");\r\n"+
            "end";



	
}

package at.jku.cis.iVolunteer.test.data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class TestData {
	
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ClassDefinitionService classDefinitionService;	
	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private VolunteerRepository volunteerRepository;
	
	public static final String ROOT_FREIWILLIGENPASS_EINTRAG = "Freiwilligenpass-\nEintrag";
	public static final String CERTIFICATE_HEAD_AUSBILDUNG = "Ausbildung";
	
	public static final String CERTIFICATE_DRIVING_LICENSE = "Führerschein";
	public static final String CERTIFICATE_DRIVING_LICENSE_CAR = "Driving License Car";
	public static final String CERTIFICATE_DRIVING_LICENSE_TRUCK = "Driving License Truck";
	public static final String CERTIFICATE_DRIVING_LICENSE_BUS = "Driving License Bus";
	public static final String CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE = "Driving License Motorcycle";
	
	public static final String COMPETENCE_DRIVING = "Driving";
	public static final String COMPETENCE_DRIVING_CAR = "Car Driving";
	public static final String COMPETENCE_DRIVING_TRUCK = "Truck Driving";
	public static final String COMPETENCE_DRIVING_BUS = "Bus Driving";
	public static final String COMPETENCE_DRIVING_MOTORCYCLE = "Motorcycle Driving";

	public static final String PROPERTY_LICENSE_TYPE = "License Type";
	public static final String PROPERTY_DRIVING_LEVEL = "Driving Level";
	public static final String PROPERTY_EVIDENCE = "Evidence"; 
	
	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
	private static final String RKWILHERING = "RK Wilhering";
	
	protected static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.GERMAN);
	
	public enum LicenseType{
		A, B, C, D, E
	}
	
	public enum DrivingLevel{
		// levels from https://www.semanticscholar.org/paper/DRIVER-COMPETENCE-IN-A-HIERARCHICAL-PERSPECTIVE%3B-Per%C3%A4aho-Keskinen/c64e45ece27720782367038220abe008924151a2
		LEVEL1("Level 1", "Vehicle manoeuvring"), LEVEL2("Level 2", "Mastery of traffic situations"), 
		LEVEL3("Level 3", "Goals and context of driving"), LEVEL4("Level 4", "Goals for life and skills for living");
				
		private String name;
		private String levelDesc;
		
		DrivingLevel(String name, String levelDesc) {
			this.name = name;
			this.levelDesc = levelDesc;
		}
		
		public String getName() {
			return name;
		}
		public String getDescription() {
			return levelDesc;
		}
	}
	
	public void load() {
		createGeneralClasses();
		createGeneralCompetences();
	}
	
	public void createGeneralClasses() {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		
		// Freiwilligenpasseinträge analog Alex in InitializationService
		ClassDefinition root = obtainClassFreiwilligenpassEintrag(tenantId);
        ClassDefinition certificateClass = obtainClassZertifikat(tenantId, root);
        ClassDefinition competenceClass = obtainClassKompetenz(tenantId, root);
        ClassDefinition taskClass = obtainClassTask(tenantId, root);
        ClassDefinition functionClass = obtainClassFunktion(tenantId, root);
        ClassDefinition verdienstClass = obtainClassVerdienst(tenantId, root);
		
        // relationships noch setzen XXX Claudia
        
       tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		
		// Freiwilligenpasseinträge analog Alex in InitializationService
		root = obtainClassFreiwilligenpassEintrag(tenantId);
		certificateClass = obtainClassZertifikat(tenantId, root);
        competenceClass = obtainClassKompetenz(tenantId, root);
        taskClass = obtainClassTask(tenantId, root);
        functionClass = obtainClassFunktion(tenantId, root);
        verdienstClass = obtainClassVerdienst(tenantId, root);
	}
	
	public void createGeneralCompetences() {
		createDrivingSkills(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
		createDrivingSkills(coreTenantRestClient.getTenantIdByName(RKWILHERING));
		createRandomCompetences(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
		createRandomCompetences(coreTenantRestClient.getTenantIdByName(RKWILHERING));	
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
    
    protected void deleteInstances(Volunteer volunteer, String tenantId, String className) {
		// System.out.println("Volunteer: " + volunteer + " tenant: " + tenantId + " className " + className);
		ClassDefinition classComp = classDefinitionService.getByName(className, tenantId);	
		if (classComp != null)
			classInstanceService.deleteClassInstances(volunteer, classComp.getId(), tenantId);
	}
	
	protected ClassDefinition obtainClass(String tenantId, String name, ClassDefinition parent) {
		// System.out.println(" obtain class " + name + ", tenantId: " + tenantId + ", parent: " + parent.getName());
		ClassDefinition a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		if (a0 == null) {	
			createClass(tenantId, name, parent);
			a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
		}
		return a0;
	}

	protected PropertyDefinition<Object> obtainProperty(String name, PropertyType type, String tenantId) {
		List<PropertyDefinition<Object>> pdList = propertyDefinitionRepository.getByNameAndTenantId(name, tenantId);
		PropertyDefinition<Object> pd;
		if (pdList.size() == 0) {
			pd = new PropertyDefinition<Object>(name, type, tenantId);
		    propertyDefinitionRepository.save(pd);
	    } else
	    	pd = pdList.get(0);
		
		return pd;
	}

	
	private ClassDefinition obtainClassFreiwilligenpassEintrag(String tenantId) {
		ClassDefinition fwPassEintrag = classDefinitionRepository.findByNameAndTenantId(ROOT_FREIWILLIGENPASS_EINTRAG, tenantId);
		if (fwPassEintrag == null) {	
			fwPassEintrag = new ClassDefinition();
			//fwPassEintrag.setId(new ObjectId().toHexString());
			fwPassEintrag.setTenantId(tenantId);
			fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
			fwPassEintrag.setRoot(true);
			fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
			fwPassEintrag.setWriteProtected(true);
			fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
			// properties
			PropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("id", PropertyType.TEXT, tenantId);
			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("name", PropertyType.TEXT, tenantId);
			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("evidenz", PropertyType.TEXT, tenantId);
			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			classDefinitionRepository.save(fwPassEintrag);
		}
		fwPassEintrag = classDefinitionRepository.findByNameAndTenantId(ROOT_FREIWILLIGENPASS_EINTRAG, tenantId);
		return fwPassEintrag;
	}
	
	public void createDrivingSkills(String tenantId) {
		// certificate --> driving license
		ClassDefinition certClassDrivingLicense = obtainClassDrivingLicense(tenantId);
		ClassDefinition certClassDrivingLicenseCar = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_CAR, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseTruck = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_TRUCK, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseBus = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_BUS, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseMotorcycle = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, certClassDrivingLicense);
		
		// grouping and hierarchy in certificate "driving license"
		List<Inheritance> iList = new ArrayList<Inheritance>();
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
		ClassDefinition compClassDriving = createDrivingCompetence(tenantId);
		ClassDefinition compClassDrivingCar = obtainClass(tenantId, COMPETENCE_DRIVING_CAR, compClassDriving); 
		ClassDefinition compClassDrivingTruck = obtainClass(tenantId, COMPETENCE_DRIVING_TRUCK, compClassDriving); 
		ClassDefinition compClassDrivingBus = obtainClass(tenantId, COMPETENCE_DRIVING_BUS, compClassDriving); 
		ClassDefinition compClassDrivingMotorcycle = obtainClass(tenantId, COMPETENCE_DRIVING_MOTORCYCLE, compClassDriving);

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
	
	protected ClassDefinition obtainClassDrivingLicense(String tenantId) {
		ClassDefinition drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE, tenantId);
		if (drivingLicense == null) {
			ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
			drivingLicense = new AchievementClassDefinition();
			drivingLicense.setMarketplaceId(marketplaceService.getMarketplaceId());
			drivingLicense.setName(CERTIFICATE_DRIVING_LICENSE);
			drivingLicense.setParentId(certificateClass.getId());
			drivingLicense.setTimestamp(new Date());
			drivingLicense.setTenantId(tenantId);
			drivingLicense.setClassArchetype(ClassArchetype.ACHIEVEMENT);
			drivingLicense.setWriteProtected(true);
			drivingLicense.setParentId(certificateClass.getId());
			drivingLicense.setProperties(new ArrayList<ClassProperty<Object>>());
		
			classDefinitionRepository.save(drivingLicense);
		}
		drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE, tenantId);
		return drivingLicense;	
	}
	protected ClassDefinition createDrivingCompetence(String tenantId) {
		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Kompetenz", tenantId);
		CompetenceClassDefinition c1 = new CompetenceClassDefinition();
		c1.setName(COMPETENCE_DRIVING);
		c1.setClassArchetype(ClassArchetype.COMPETENCE);
		c1.setTenantId(tenantId);
		c1.setRoot(false);
		c1.setParentId(compClass.getId());
		// set properties
		List<Object> levelValues = Arrays.asList(DrivingLevel.LEVEL1.getName(), DrivingLevel.LEVEL2.getName(), DrivingLevel.LEVEL3.getName(), DrivingLevel.LEVEL4.getName()); 
		PropertyDefinition<Object> pdLevel = obtainProperty(PROPERTY_DRIVING_LEVEL, PropertyType.TEXT, tenantId);
		pdLevel.setAllowedValues(levelValues);
		PropertyDefinition<Object> pdEvidence = obtainProperty(PROPERTY_EVIDENCE, PropertyType.TEXT, tenantId);
		PropertyDefinition<Object> pdIssued = obtainProperty("Issued", PropertyType.DATE, tenantId);
		c1.setProperties(new ArrayList<ClassProperty<Object>>());
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdEvidence));
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
		classDefinitionRepository.save(c1);
		return c1;
	}
	
	protected void createRandomCompetences(String tenantId) {
		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository.
				findByNameAndTenantId("Kompetenz", tenantId);
		CompetenceClassDefinition c1 = (CompetenceClassDefinition) classDefinitionRepository.
				findByNameAndTenantId("Maturity", tenantId);
		if (c1 == null) {		
			c1 = new CompetenceClassDefinition();
			c1.setName("Maturity");
			c1.setClassArchetype(ClassArchetype.COMPETENCE);
			c1.setTenantId(tenantId);
			c1.setRoot(false);
			c1.setParentId(compClass.getId());
			// set properties
			PropertyDefinition<Object> pdLevel = obtainProperty("Maturity Level", PropertyType.WHOLE_NUMBER, tenantId);
			c1.setProperties(new ArrayList<ClassProperty<Object>>());
			c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
			classDefinitionRepository.save(c1);
		}
	}
	
	
	protected ClassDefinition obtainClassZertifikat(String tenantId, ClassDefinition root) {
		ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
		if (certificateClass == null) {
			certificateClass = new AchievementClassDefinition();
			certificateClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			certificateClass.setName("Zertifikat");
			certificateClass.setParentId(certificateClass.getId());
			certificateClass.setTimestamp(new Date());
			certificateClass.setTenantId(tenantId);
			certificateClass.setClassArchetype(ClassArchetype.ACHIEVEMENT);
			certificateClass.setWriteProtected(true);
			certificateClass.setParentId(root.getId());
			certificateClass.setProperties(new ArrayList<ClassProperty<Object>>());
		
			PropertyDefinition<Object> pdDescription = obtainProperty("Description", PropertyType.TEXT, tenantId);
			certificateClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
			PropertyDefinition<Object> pdIssued = obtainProperty("Issued", PropertyType.DATE, tenantId);
			certificateClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
			classDefinitionRepository.save(certificateClass);
		}
		certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
		return certificateClass;	
	}
	
	protected ClassDefinition obtainClassKompetenz(String tenantId, ClassDefinition root) {
		ClassDefinition competenceClass = classDefinitionRepository.findByNameAndTenantId("Kompetenz", tenantId);
		if (competenceClass == null) {
			competenceClass = new CompetenceClassDefinition();
			competenceClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			competenceClass.setName("Kompetenz");
			competenceClass.setTimestamp(new Date());
			competenceClass.setTenantId(tenantId);
			competenceClass.setClassArchetype(ClassArchetype.COMPETENCE);
			competenceClass.setWriteProtected(true);
			competenceClass.setParentId(root.getId());
			competenceClass.setProperties(new ArrayList<ClassProperty<Object>>());
		
			PropertyDefinition<Object> pdDescription = obtainProperty("Level", PropertyType.TEXT, tenantId);
			competenceClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
			PropertyDefinition<Object> pdIssued = obtainProperty("evidence", PropertyType.TEXT, tenantId);
			competenceClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
			classDefinitionRepository.save(competenceClass);
		}
		competenceClass = classDefinitionRepository.findByNameAndTenantId("Kompetenz", tenantId);
		return competenceClass;	
	}
	
	protected ClassDefinition obtainClassTask(String tenantId, ClassDefinition root) {
		ClassDefinition taskClass = classDefinitionRepository.findByNameAndTenantId("Tätigkeit", tenantId);
		if (taskClass == null) {
			taskClass = new TaskClassDefinition();
			taskClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			taskClass.setName("Tätigkeit");
			taskClass.setParentId(root.getId());
			taskClass.setTimestamp(new Date());
			taskClass.setTenantId(tenantId);
			taskClass.setClassArchetype(ClassArchetype.TASK);
			taskClass.setWriteProtected(true);
			taskClass.setProperties(new ArrayList<ClassProperty<Object>>());
			
			PropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("role", PropertyType.TEXT, tenantId);
			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("Starting Date", PropertyType.DATE, tenantId);
			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("End Date", PropertyType.DATE, tenantId);
			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("Ort", PropertyType.TEXT, tenantId);
			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			
			classDefinitionRepository.save(taskClass);
		}
		taskClass = classDefinitionRepository.findByNameAndTenantId("Tätigkeit", tenantId);
		return taskClass;
	}
	
	protected ClassDefinition obtainClassVerdienst(String tenantId, ClassDefinition root) {
		ClassDefinition verdienstClass = classDefinitionRepository.findByNameAndTenantId("Verdienst", tenantId);
		if (verdienstClass == null) {
			verdienstClass = new AchievementClassDefinition();
			verdienstClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			verdienstClass.setName("Verdienst");
			verdienstClass.setParentId(root.getId());
			verdienstClass.setTimestamp(new Date());
			verdienstClass.setTenantId(tenantId);
			verdienstClass.setClassArchetype(ClassArchetype.ACHIEVEMENT);
			verdienstClass.setWriteProtected(true);
			verdienstClass.setProperties(new ArrayList<ClassProperty<Object>>());
			
			PropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
			verdienstClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("issuedOn", PropertyType.DATE, tenantId); 
			verdienstClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			classDefinitionRepository.save(verdienstClass);
		}
		verdienstClass = classDefinitionRepository.findByNameAndTenantId("Verdienst", tenantId);
		return verdienstClass;
	}
	
	protected ClassDefinition obtainClassFunktion(String tenantId, ClassDefinition root) {
		ClassDefinition functionClass = classDefinitionRepository.findByNameAndTenantId("Funktion", tenantId);
		if (functionClass == null) {
			functionClass = new FunctionClassDefinition();
			functionClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			functionClass.setName("Funktion");
			functionClass.setParentId(root.getId());
			functionClass.setTimestamp(new Date());
			functionClass.setTenantId(tenantId);
			functionClass.setClassArchetype(ClassArchetype.FUNCTION);
			functionClass.setWriteProtected(true);
			functionClass.setProperties(new ArrayList<ClassProperty<Object>>());
			
			PropertyDefinition<Object> pd = obtainProperty("Starting Date", PropertyType.DATE, tenantId);
			functionClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("End Date", PropertyType.DATE, tenantId);
			functionClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			classDefinitionRepository.save(functionClass);
		}
		functionClass = classDefinitionRepository.findByNameAndTenantId("Funktion", tenantId);
		return functionClass;
	}
	
	protected void createClass(String tenantId, String name, ClassDefinition parent) {
		ClassDefinition a = newClassDefinition(parent);
		a.setMarketplaceId(marketplaceService.getMarketplaceId());
		a.setName(name);
		a.setParentId(parent.getId());
		a.setTimestamp(new Date());
		a.setTenantId(tenantId);
		a.setClassArchetype(parent.getClassArchetype());
		/*parent.getProperties().forEach(p -> {
			System.out.println("-------------------> " + p.getName());
		});*/
	   a.setProperties(parent.getProperties());
	   //classDefinitionRepository.save(a);
	   classDefinitionService.newClassDefinition(a);
	}
	
	protected ClassDefinition newClassDefinition(ClassDefinition parent) {
		ClassDefinition cd = null;
		switch(parent.getClassArchetype()) {
		case ACHIEVEMENT:
				cd = new AchievementClassDefinition();
				cd.setClassArchetype(ClassArchetype.ACHIEVEMENT); 
				break;
		case COMPETENCE:
				cd = new CompetenceClassDefinition(); 
		        cd.setClassArchetype(ClassArchetype.COMPETENCE);
		        break;
		case TASK:
				cd = new TaskClassDefinition();
		        cd.setClassArchetype(ClassArchetype.TASK); 
		        break;
		case FUNCTION:
				cd = new FunctionClassDefinition();
				cd.setClassArchetype(ClassArchetype.FUNCTION);
				break;
		default:
			break;
		}
		return cd;
	}


    
    public void cleanUp() {
		Volunteer volunteer = volunteerRepository.findByUsername("CVoj");
		
		if (volunteer != null) {
			String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
			
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_CAR); 	
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_BUS);
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_TRUCK); 	
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_MOTORCYCLE);
		
			tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_CAR); 	
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_BUS);
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_TRUCK); 	
			deleteInstances(volunteer, tenantId, COMPETENCE_DRIVING_MOTORCYCLE);
		}
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		ArrayList<String> delCD = new ArrayList<String>();
		delCD.add(classDefinitionService.getByName("Maturity", tenantId).getId());
		
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);	
		delCD.add(classDefinitionService.getByName("Maturity", tenantId).getId());
		
		classDefinitionService.deleteClassDefinition(delCD);
	}
}

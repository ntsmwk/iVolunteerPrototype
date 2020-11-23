//package at.jku.cis.iVolunteer.marketplace.rule.engine.test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
//import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
//import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
//import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
//import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
//import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
//import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
//import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
//import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
//import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
//import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
//import at.jku.cis.iVolunteer.model.user.User;
//
//@Service
//public class TestDataClasses {
//
//	@Autowired private ClassConfigurationController classConfigurationController;
//	@Autowired private ClassDefinitionRepository classDefinitionRepository;
//	@Autowired private ClassInstanceRepository classInstanceRepository;
//	@Autowired private ClassPropertyService classPropertyService;
//	@Autowired private RelationshipRepository relationshipRepository;
//	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
//	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
//	@Autowired private MarketplaceService marketplaceService;
//	@Autowired private ClassDefinitionService classDefinitionService;
//	@Autowired private ClassInstanceService classInstanceService;
//	@Autowired private CoreTenantRestClient coreTenantRestClient;
//	@Autowired private UserRepository userRepository;
//
//	public static final String ROOT_FREIWILLIGENPASS_EINTRAG = "Freiwilligenpass-\nEintrag";
//	public static final String CERTIFICATE_HEAD_AUSBILDUNG = "Ausbildung";
//
//	public static final String CERTIFICATE_DRIVING_LICENSE = "Führerschein";
//	public static final String CERTIFICATE_DRIVING_LICENSE_CAR = "Führerschein B";
//	public static final String CERTIFICATE_DRIVING_LICENSE_TRUCK = "Führerschein C";
//	public static final String CERTIFICATE_DRIVING_LICENSE_BUS = "Führerschein D";
//	public static final String CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE = "Führerschein A";
//
//	public static final String COMPETENCE_DRIVING = "Fahren";
//	public static final String COMPETENCE_DRIVING_CAR = "Autofahren";
//	public static final String COMPETENCE_DRIVING_TRUCK = "LKW-Fahren";
//	public static final String COMPETENCE_DRIVING_BUS = "Busfahren";
//	public static final String COMPETENCE_DRIVING_MOTORCYCLE = "Motorradfahren";
//
//	public static final String PROPERTY_DRIVING_LEVEL = "Fahrlevel";
//	public static final String PROPERTY_EVIDENCE = "Evidenz";
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
//	private static final String FFEIDENBERG = "FF Eidenberg";
//	private static final String RKWILHERING = "RK Wilhering";
//
//	public enum LicenseType {
//		A, B, C, D, E
//	}
//
//	public enum DrivingLevel {
//		// levels from
//		// https://www.semanticscholar.org/paper/DRIVER-COMPETENCE-IN-A-HIERARCHICAL-PERSPECTIVE%3B-Per%C3%A4aho-Keskinen/c64e45ece27720782367038220abe008924151a2
//		LEVEL1("Level 1", "Vehicle manoeuvring"), LEVEL2("Level 2", "Mastery of traffic situations"),
//		LEVEL3("Level 3", "Goals and context of driving"), LEVEL4("Level 4", "Goals for life and skills for living");
//
//		private String name;
//		private String levelDesc;
//
//		DrivingLevel(String name, String levelDesc) {
//			this.name = name;
//			this.levelDesc = levelDesc;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public String getDescription() {
//			return levelDesc;
//		}
//	}
//
//	public void createClassConfigurations() {
//		String slotName = "Test-Rule-Engine";
//		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
//		ClassConfiguration classConfigFF = new ClassConfiguration();
//		classConfigFF.setTimestamp(new Date());
//		classConfigFF.setId(slotName);
//		classConfigFF.setName(slotName);
//		classConfigFF.setClassDefinitionIds(new ArrayList<>());
//		classConfigFF.setRelationshipIds(new ArrayList<>());
//		classConfigFF.setTenantId(tenantId);
//
//		createGeneralClasses(classConfigFF);
//		createGeneralCompetences(classConfigFF);
//		createProperties(classConfigFF);
//
//		this.classConfigurationController.saveClassConfiguration(classConfigFF);
//
//		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		ClassConfiguration classConfigRK = new ClassConfiguration();
//		classConfigRK.setTimestamp(new Date());
//		classConfigRK.setId(slotName);
//		classConfigRK.setName(slotName);
//		classConfigRK.setClassDefinitionIds(new ArrayList<>());
//		classConfigRK.setRelationshipIds(new ArrayList<>());
//		classConfigRK.setTenantId(tenantId);
//
//		createGeneralClasses(classConfigRK);
//		createGeneralCompetences(classConfigRK);
//		createProperties(classConfigRK);
//
//		createClassCertificatesRK(classConfigRK);
//		createClassTasksRK(classConfigRK);
//		createClassRolesRK(classConfigRK);
//		createClassVerdiensteRK(classConfigRK);
//
//		this.classConfigurationController.saveClassConfiguration(classConfigRK);
//	}
//
//	public void createGeneralClasses(ClassConfiguration classConfig) {
//		List<ClassDefinition> classDefinitions = new ArrayList<>();
//		List<Relationship> relationships = new ArrayList<>();
//		String tenantId = classConfig.getTenantId();
//
//		// Freiwilligenpasseinträge analog Alex in InitializationService
//		ClassDefinition root = obtainClassFreiwilligenpassEintrag(tenantId);
//		ClassDefinition certificateClass = obtainClassZertifikat(tenantId, root);
//		ClassDefinition competenceClass = obtainClassKompetenz(tenantId, root);
//		ClassDefinition taskClass = obtainClassTask(tenantId, root);
//		ClassDefinition functionClass = obtainClassFunktion(tenantId, root);
//		ClassDefinition verdienstClass = obtainClassVerdienst(tenantId, root);
//
//		classDefinitions.add(root);
//		classDefinitions.add(certificateClass);
//		classDefinitions.add(competenceClass);
//		classDefinitions.add(taskClass);
//		classDefinitions.add(functionClass);
//		classDefinitions.add(verdienstClass);
//
//		Inheritance r1 = new Inheritance();
//		r1.setRelationshipType(RelationshipType.INHERITANCE);
//		r1.setTarget(certificateClass.getId());
//		r1.setSource(root.getId());
//		Inheritance r2 = new Inheritance();
//		r2.setRelationshipType(RelationshipType.INHERITANCE);
//		r2.setTarget(competenceClass.getId());
//		r2.setSource(root.getId());
//		Inheritance r3 = new Inheritance();
//		r3.setRelationshipType(RelationshipType.INHERITANCE);
//		r3.setTarget(taskClass.getId());
//		r3.setSource(root.getId());
//		Inheritance r4 = new Inheritance();
//		r4.setRelationshipType(RelationshipType.INHERITANCE);
//		r4.setTarget(functionClass.getId());
//		r4.setSource(root.getId());
//		Inheritance r5 = new Inheritance();
//		r5.setRelationshipType(RelationshipType.INHERITANCE);
//		r5.setTarget(verdienstClass.getId());
//		r5.setSource(root.getId());
//
//		relationships.add(r1);
//		relationships.add(r2);
//		relationships.add(r3);
//		relationships.add(r4);
//		relationships.add(r5);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			this.classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//
//		for (Relationship r : relationships) {
//			this.relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
//	}
//
//	public void createGeneralCompetences(ClassConfiguration classConfig) {
//		createDrivingSkills(classConfig);
//		createRandomCompetences(classConfig);
//	}
//
//	public void createProperties(ClassConfiguration classConfig) {
//		if (propertyDefinitionRepository.getByNameAndTenantId("Alter", classConfig.getTenantId()).size() == 0) {
//			FlatPropertyDefinition<Object> pdAlter = new FlatPropertyDefinition<Object>();
//			pdAlter.setTenantId(classConfig.getTenantId());
//			pdAlter.setMarketplaceId(classConfig.getMarketplaceId());
//			pdAlter.setType(PropertyType.WHOLE_NUMBER);
//			pdAlter.setName("Alter");
//			propertyDefinitionRepository.save(pdAlter);
//		}
//	}
//
//	protected void deleteInstances(User volunteer, String tenantId, String className) {
//		// System.out.println("Volunteer: " + volunteer + " tenant: " + tenantId + "
//		// className " + className);
//		ClassDefinition classComp = classDefinitionService.getByName(className, tenantId);
//		if (classComp != null)
//			classInstanceService.deleteClassInstances(volunteer, classComp.getId(), tenantId);
//	}
//
//	protected ClassDefinition obtainClass(String tenantId, String name, ClassDefinition parent) {
//		// System.out.println(" obtain class " + name + ", tenantId: " + tenantId + ",
//		// parent: " + parent.getName());
//		ClassDefinition a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
//		if (a0 == null) {
//			createClass(tenantId, name, parent);
//			a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
//		}
//		return a0;
//	}
//
//	public FlatPropertyDefinition<Object> obtainProperty(String name, PropertyType type, String tenantId) {
//		List<FlatPropertyDefinition<Object>> pdList = propertyDefinitionRepository.getByNameAndTenantId(name, tenantId);
//		FlatPropertyDefinition<Object> pd;
//		if (pdList.size() == 0) {
//			pd = new FlatPropertyDefinition<Object>(name, type, tenantId);
//			propertyDefinitionRepository.save(pd);
//		} else
//			pd = pdList.get(0);
//
//		return pd;
//	}
//
//	private ClassDefinition obtainClassFreiwilligenpassEintrag(String tenantId) {
//
//		ClassDefinition fwPassEintrag = classDefinitionRepository.findByNameAndTenantId(ROOT_FREIWILLIGENPASS_EINTRAG,
//				tenantId);
//		if (fwPassEintrag == null) {
//			fwPassEintrag = new ClassDefinition();
//			// fwPassEintrag.setId(new ObjectId().toHexString());
//			fwPassEintrag.setTenantId(tenantId);
//			fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
//			fwPassEintrag.setRoot(true);
//			fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
//			fwPassEintrag.setWriteProtected(true);
//			fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
//			// properties
//			FlatPropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
//			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("id", PropertyType.TEXT, tenantId);
//			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("name", PropertyType.TEXT, tenantId);
//			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("evidenz", PropertyType.TEXT, tenantId);
//			fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			classDefinitionRepository.save(fwPassEintrag);
//		}
//
//		fwPassEintrag = classDefinitionRepository.findByNameAndTenantId(ROOT_FREIWILLIGENPASS_EINTRAG, tenantId);
//		return fwPassEintrag;
//	}
//
//	public void createDrivingSkills(ClassConfiguration classConfig) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		// certificate --> driving license
//		ClassDefinition certClassDrivingLicense = obtainClassDrivingLicense(classConfig.getTenantId());
//		ClassDefinition certClassDrivingLicenseCar = obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_DRIVING_LICENSE_CAR, certClassDrivingLicense);
//		ClassDefinition certClassDrivingLicenseTruck = obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_DRIVING_LICENSE_TRUCK, certClassDrivingLicense);
//		ClassDefinition certClassDrivingLicenseBus = obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_DRIVING_LICENSE_BUS, certClassDrivingLicense);
//		ClassDefinition certClassDrivingLicenseMotorcycle = obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, certClassDrivingLicense);
//
//		classDefinitions.add(certClassDrivingLicense);
//		classDefinitions.add(certClassDrivingLicenseCar);
//		classDefinitions.add(certClassDrivingLicenseTruck);
//		classDefinitions.add(certClassDrivingLicenseBus);
//		classDefinitions.add(certClassDrivingLicenseMotorcycle);
//
//		// grouping and hierarchy in certificate "driving license"
//		Inheritance i1 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseCar.getId());
//		i1.setId("drivingCarLicense");
//		relationships.add(i1);
//		Inheritance i2 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseTruck.getId());
//		i2.setId("drivingTruckLicense");
//		relationships.add(i2);
//		Inheritance i3 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseBus.getId());
//		i3.setId("drivingBusLicense");
//		relationships.add(i3);
//		Inheritance i4 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseMotorcycle.getId());
//		i4.setId("drivingMotorcycleLicense");
//		relationships.add(i4);
//
//		// competences --> driving
//		ClassDefinition compClassDriving = createDrivingCompetence(classConfig.getTenantId());
//		ClassDefinition compClassDrivingCar = obtainClass(classConfig.getTenantId(), COMPETENCE_DRIVING_CAR,
//				compClassDriving);
//		ClassDefinition compClassDrivingTruck = obtainClass(classConfig.getTenantId(), COMPETENCE_DRIVING_TRUCK,
//				compClassDriving);
//		ClassDefinition compClassDrivingBus = obtainClass(classConfig.getTenantId(), COMPETENCE_DRIVING_BUS,
//				compClassDriving);
//		ClassDefinition compClassDrivingMotorcycle = obtainClass(classConfig.getTenantId(),
//				COMPETENCE_DRIVING_MOTORCYCLE, compClassDriving);
//
//		classDefinitions.add(compClassDriving);
//		classDefinitions.add(compClassDrivingCar);
//		classDefinitions.add(compClassDrivingTruck);
//		classDefinitions.add(compClassDrivingBus);
//		classDefinitions.add(compClassDrivingMotorcycle);
//
//		// competence driving needs evidence of certificate (driving license)
//		Association a1 = new Association(compClassDrivingCar.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a1.setId("evidenceByDriverLicenseB");
//		relationships.add(a1);
//		Association a2 = new Association(compClassDrivingTruck.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a2.setId("evidenceByDriverLicenseC");
//		relationships.add(a2);
//		Association a3 = new Association(compClassDrivingBus.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a3.setId("evidenceByDriverLicenseD");
//		relationships.add(a3);
//		Association a4 = new Association(compClassDrivingMotorcycle.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a4.setId("evidenceByDriverLicenseA");
//		relationships.add(a4);
//
//		// grouping and hierarchy in competence "driving"
//		Inheritance i5 = new Inheritance(compClassDriving.getId(), compClassDrivingCar.getId());
//		i5.setId("drivingCar");
//		relationships.add(i5);
//		Inheritance i6 = new Inheritance(compClassDriving.getId(), compClassDrivingTruck.getId());
//		i6.setId("drivingTruck");
//		relationships.add(i6);
//		Inheritance i7 = new Inheritance(compClassDriving.getId(), compClassDrivingBus.getId());
//		i7.setId("drivingBus");
//		relationships.add(i7);
//		Inheritance i8 = new Inheritance(compClassDriving.getId(), compClassDrivingMotorcycle.getId());
//		i8.setId("drivingMotorcycle");
//		relationships.add(i8);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			this.classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//
//		for (Relationship r : relationships) {
//			if (!relationshipRepository.exists(r.getId())) {
//				relationshipRepository.save(r);
//				classConfig.getRelationshipIds().add(r.getId());
//			}
//		}
//	}
//
//	private void printAllAssets(User volunteer, String tenantId) {
//		System.out.println("all assets from " + volunteer.getUsername() + ": ");
//		printArchetypeAssets(volunteer, tenantId, ClassArchetype.ACHIEVEMENT);
//		printArchetypeAssets(volunteer, tenantId, ClassArchetype.COMPETENCE);
//		printArchetypeAssets(volunteer, tenantId, ClassArchetype.FUNCTION);
//		printArchetypeAssets(volunteer, tenantId, ClassArchetype.TASK);
//	}
//
//	private void printArchetypeAssets(User volunteer, String tenantId, ClassArchetype classArchetype) {
//		System.out.println("..... " + classArchetype.getArchetype() + ": ");
//		List<ClassDefinition> classes = classDefinitionRepository.getByClassArchetypeAndTenantId(classArchetype,
//				tenantId);
//		for (ClassDefinition cd : classes) {
//			List<ClassInstance> assets = classInstanceRepository
//					.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), cd.getId(), tenantId);
//			System.out.println("------- class: " + cd.getName() + " number of assets == " + assets.size());
//			assets.forEach(ci -> {
//				System.out.println(".............. " + ci.getName());
//			});
//		}
//	}
//
//	private void printClassDefinitions(String tenantId) {
//		List<ClassDefinition> classList = classDefinitionRepository.findByTenantId(tenantId);
//		classList.forEach(c -> {
//			System.out.println(" Class: " + c.getName() + ", parent: " + c.getParentId());
//		});
//	}
//
//	protected ClassDefinition obtainClassDrivingLicense(String tenantId) {
//		ClassDefinition drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE,
//				tenantId);
//		if (drivingLicense == null) {
//			ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
//			drivingLicense = new AchievementClassDefinition();
//			drivingLicense.setMarketplaceId(marketplaceService.getMarketplaceId());
//			drivingLicense.setName(CERTIFICATE_DRIVING_LICENSE);
//			drivingLicense.setParentId(certificateClass.getId());
//			drivingLicense.setTimestamp(new Date());
//			drivingLicense.setTenantId(tenantId);
//			drivingLicense.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//			drivingLicense.setWriteProtected(true);
//			drivingLicense.setParentId(certificateClass.getId());
//			drivingLicense.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			classDefinitionRepository.save(drivingLicense);
//		}
//		drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE, tenantId);
//		return drivingLicense;
//	}
//
//	protected ClassDefinition createDrivingCompetence(String tenantId) {
//		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Kompetenz", tenantId);
//
//		CompetenceClassDefinition c1 = new CompetenceClassDefinition();
//		c1.setName(COMPETENCE_DRIVING);
//		c1.setClassArchetype(ClassArchetype.COMPETENCE);
//		c1.setTenantId(tenantId);
//		c1.setRoot(false);
//		c1.setParentId(compClass.getId());
//		// set properties
//		List<Object> levelValues = Arrays.asList(DrivingLevel.LEVEL1.getName(), DrivingLevel.LEVEL2.getName(),
//				DrivingLevel.LEVEL3.getName(), DrivingLevel.LEVEL4.getName());
//		FlatPropertyDefinition<Object> pdLevel = obtainProperty(PROPERTY_DRIVING_LEVEL, PropertyType.TEXT, tenantId);
//		pdLevel.setAllowedValues(levelValues);
//		FlatPropertyDefinition<Object> pdEvidence = obtainProperty(PROPERTY_EVIDENCE, PropertyType.TEXT, tenantId);
//		FlatPropertyDefinition<Object> pdIssued = obtainProperty("Issued", PropertyType.DATE, tenantId);
//		c1.setProperties(new ArrayList<ClassProperty<Object>>());
//		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
//		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdEvidence));
//		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
//		classDefinitionRepository.save(c1);
//		return c1;
//	}
//
//	protected void createRandomCompetences(ClassConfiguration classConfig) {
//		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Kompetenz", classConfig.getTenantId());
//		CompetenceClassDefinition c1 = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Maturity", classConfig.getTenantId());
//		if (c1 == null) {
//			c1 = new CompetenceClassDefinition();
//			c1.setConfigurationId(classConfig.getId());
//			c1.setName("Maturity");
//			c1.setClassArchetype(ClassArchetype.COMPETENCE);
//			c1.setTenantId(classConfig.getTenantId());
//			c1.setRoot(false);
//			c1.setParentId(compClass.getId());
//			// set properties
//			FlatPropertyDefinition<Object> pdLevel = obtainProperty("Maturity Level", PropertyType.WHOLE_NUMBER,
//					classConfig.getTenantId());
//			c1.setProperties(new ArrayList<ClassProperty<Object>>());
//			c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
//			classDefinitionRepository.save(c1);
//			Inheritance i = new Inheritance();
//			i.setSource(compClass.getId());
//			i.setTarget(c1.getId());
//			relationshipRepository.save(i);
//			classConfig.getRelationshipIds().add(i.getId());
//		}
//	}
//
//	protected ClassDefinition obtainClassZertifikat(String tenantId, ClassDefinition root) {
//		ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
//		if (certificateClass == null) {
//			certificateClass = new AchievementClassDefinition();
//			certificateClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			certificateClass.setName("Zertifikat");
//			certificateClass.setParentId(certificateClass.getId());
//			certificateClass.setTimestamp(new Date());
//			certificateClass.setTenantId(tenantId);
//			certificateClass.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//			certificateClass.setWriteProtected(true);
//			certificateClass.setParentId(root.getId());
//			certificateClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pdDescription = obtainProperty("Description", PropertyType.TEXT, tenantId);
//			certificateClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
//			FlatPropertyDefinition<Object> pdIssued = obtainProperty("Issued", PropertyType.DATE, tenantId);
//			certificateClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
//			classDefinitionRepository.save(certificateClass);
//		}
//		certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
//		return certificateClass;
//	}
//
//	protected ClassDefinition obtainClassKompetenz(String tenantId, ClassDefinition root) {
//		ClassDefinition competenceClass = classDefinitionRepository.findByNameAndTenantId("Kompetenz", tenantId);
//		if (competenceClass == null) {
//			competenceClass = new CompetenceClassDefinition();
//			competenceClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			competenceClass.setName("Kompetenz");
//			competenceClass.setTimestamp(new Date());
//			competenceClass.setTenantId(tenantId);
//			competenceClass.setClassArchetype(ClassArchetype.COMPETENCE);
//			competenceClass.setWriteProtected(true);
//			competenceClass.setParentId(root.getId());
//			competenceClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pdDescription = obtainProperty("Level", PropertyType.TEXT, tenantId);
//			competenceClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
//			FlatPropertyDefinition<Object> pdIssued = obtainProperty("evidence", PropertyType.TEXT, tenantId);
//			competenceClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
//			classDefinitionRepository.save(competenceClass);
//		}
//		competenceClass = classDefinitionRepository.findByNameAndTenantId("Kompetenz", tenantId);
//		return competenceClass;
//	}
//
//	protected ClassDefinition obtainClassTask(String tenantId, ClassDefinition root) {
//		ClassDefinition taskClass = classDefinitionRepository.findByNameAndTenantId("Tätigkeit", tenantId);
//		if (taskClass == null) {
//			taskClass = new TaskClassDefinition();
//			taskClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			taskClass.setName("Tätigkeit");
//			taskClass.setParentId(root.getId());
//			taskClass.setTimestamp(new Date());
//			taskClass.setTenantId(tenantId);
//			taskClass.setClassArchetype(ClassArchetype.TASK);
//			taskClass.setWriteProtected(true);
//			taskClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
//			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("role", PropertyType.TEXT, tenantId);
//			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("Starting Date", PropertyType.DATE, tenantId);
//			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("End Date", PropertyType.DATE, tenantId);
//			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("Ort", PropertyType.TEXT, tenantId);
//			taskClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//
//			classDefinitionRepository.save(taskClass);
//		}
//		taskClass = classDefinitionRepository.findByNameAndTenantId("Tätigkeit", tenantId);
//		return taskClass;
//	}
//
//	protected ClassDefinition obtainClassVerdienst(String tenantId, ClassDefinition root) {
//		ClassDefinition verdienstClass = classDefinitionRepository.findByNameAndTenantId("Verdienst", tenantId);
//		if (verdienstClass == null) {
//			verdienstClass = new AchievementClassDefinition();
//			verdienstClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			verdienstClass.setName("Verdienst");
//			verdienstClass.setParentId(root.getId());
//			verdienstClass.setTimestamp(new Date());
//			verdienstClass.setTenantId(tenantId);
//			verdienstClass.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//			verdienstClass.setWriteProtected(true);
//			verdienstClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
//			verdienstClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("issuedOn", PropertyType.DATE, tenantId);
//			verdienstClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			classDefinitionRepository.save(verdienstClass);
//		}
//		verdienstClass = classDefinitionRepository.findByNameAndTenantId("Verdienst", tenantId);
//		return verdienstClass;
//	}
//
//	protected ClassDefinition obtainClassFunktion(String tenantId, ClassDefinition root) {
//		ClassDefinition functionClass = classDefinitionRepository.findByNameAndTenantId("Funktion", tenantId);
//		if (functionClass == null) {
//			functionClass = new FunctionClassDefinition();
//			functionClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			functionClass.setName("Funktion");
//			functionClass.setParentId(root.getId());
//			functionClass.setTimestamp(new Date());
//			functionClass.setTenantId(tenantId);
//			functionClass.setClassArchetype(ClassArchetype.FUNCTION);
//			functionClass.setWriteProtected(true);
//			functionClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pd = obtainProperty("Starting Date", PropertyType.DATE, tenantId);
//			functionClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("End Date", PropertyType.DATE, tenantId);
//			functionClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			classDefinitionRepository.save(functionClass);
//		}
//		functionClass = classDefinitionRepository.findByNameAndTenantId("Funktion", tenantId);
//		return functionClass;
//	}
//
//	protected void createClass(String tenantId, String name, ClassDefinition parent) {
//		ClassDefinition a = newClassDefinition(parent);
//		a.setMarketplaceId(marketplaceService.getMarketplaceId());
//		a.setName(name);
//		a.setParentId(parent.getId());
//		a.setTimestamp(new Date());
//		a.setTenantId(tenantId);
//		a.setClassArchetype(parent.getClassArchetype());
//		/*
//		 * parent.getProperties().forEach(p -> {
//		 * System.out.println("-------------------> " + p.getName()); });
//		 */
//		a.setProperties(parent.getProperties());
//		// classDefinitionRepository.save(a);
//		classDefinitionService.newClassDefinition(a);
//	}
//
//	protected ClassDefinition newClassDefinition(ClassDefinition parent) {
//		ClassDefinition cd = null;
//		switch (parent.getClassArchetype()) {
//		case ACHIEVEMENT:
//			cd = new AchievementClassDefinition();
//			cd.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//			break;
//		case COMPETENCE:
//			cd = new CompetenceClassDefinition();
//			cd.setClassArchetype(ClassArchetype.COMPETENCE);
//			break;
//		case TASK:
//			cd = new TaskClassDefinition();
//			cd.setClassArchetype(ClassArchetype.TASK);
//			break;
//		case FUNCTION:
//			cd = new FunctionClassDefinition();
//			cd.setClassArchetype(ClassArchetype.FUNCTION);
//			break;
//		default:
//			break;
//		}
//		return cd;
//	}
//
//	public void createClassVolunteerRK() {
//		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
//		ClassDefinition volClass = classDefinitionRepository.findByNameAndTenantId("Volunteer RK", tenantId);
//		if (volClass == null) {
//			volClass = new ClassDefinition();
//			volClass.setName("Volunteer RK");
//			volClass.setMarketplaceId(marketplaceService.getMarketplaceId());
//			volClass.setTimestamp(new Date());
//			volClass.setTenantId(tenantId);
//			volClass.setWriteProtected(true);
//			volClass.setProperties(new ArrayList<ClassProperty<Object>>());
//
//			FlatPropertyDefinition<Object> pd = obtainProperty("Eintrittsdatum", PropertyType.DATE, tenantId);
//			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("Hauptdienststelle", PropertyType.TEXT, tenantId);
//			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//			pd = obtainProperty("Dienstart", PropertyType.TEXT, tenantId);
//			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
//
//			classDefinitionRepository.save(volClass);
//		}
//		volClass = classDefinitionRepository.findByNameAndTenantId("Volunteer RK", tenantId);
//	}
//
//	public void createClassCertificatesRK(ClassConfiguration classConfig) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		AchievementClassDefinition certClassRoot = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Zertifikat", classConfig.getTenantId());
//		// Training certificates
//		AchievementClassDefinition certClassTraining = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), "Ausbildung", certClassRoot);
//		classDefinitions.add(certClassRoot);
//		// Certificate SEF-MODUL 1
//		AchievementClassDefinition certClassSEF1 = (AchievementClassDefinition) obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_SEF_MODUL1, certClassTraining);
//		ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(certClassSEF1.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF – Perfektionstraining für neue Einsatzlenker/innen (SEF-MODUL 1)"));
//		classPropertyService.updateClassProperty(certClassSEF1.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEF1);
//		//
//		AchievementClassDefinition certClassSEF2 = (AchievementClassDefinition) obtainClass(classConfig.getTenantId(),
//				CERTIFICATE_SEF_MODUL2, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEF2.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(
//				Arrays.asList("SEF – Theorie- & Praxistraining für erfahrene Einsatzlenker/innen (SEF-MODUL 2)"));
//		classPropertyService.updateClassProperty(certClassSEF2.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEF1);
//		//
//		AchievementClassDefinition certClassSEFPraxistrainer = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), CERTIFICATE_SEF_AUSFORTBILDUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFPraxistrainer.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF – Aus- und Fortbildung für SEF-Praxistrainer/innen"));
//		classPropertyService.updateClassProperty(certClassSEFPraxistrainer.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEFPraxistrainer);
//		//
//		AchievementClassDefinition certClassSEFWorkshop = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), CERTIFICATE_SEF_WORKSHOP, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFWorkshop.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF Workshop"));
//		classPropertyService.updateClassProperty(certClassSEFWorkshop.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEFWorkshop);
//		//
//		AchievementClassDefinition certClassSEFNotarzt = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), CERTIFICATE_SEF_TRAINING_NOTARZT, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFNotarzt.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF – Theorie- und Praxistraining für Notarztdienste"));
//		classPropertyService.updateClassProperty(certClassSEFNotarzt.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEFNotarzt);
//		//
//		AchievementClassDefinition certClassSEFLadegut = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), CERTIFICATE_SEF_LADEGUTSICHERUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFLadegut.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF – Ladegutsicherung für Rotkreuz LKW-Lenker/innen"));
//		classPropertyService.updateClassProperty(certClassSEFLadegut.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEFLadegut);
//		//
//		AchievementClassDefinition certClassTheorietrainer = (AchievementClassDefinition) obtainClass(
//				classConfig.getTenantId(), CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassTheorietrainer.getId(), "Description",
//				classConfig.getTenantId());
//		cp.setDefaultValues(Arrays.asList("SEF – Theorietrainerausbildung"));
//		classPropertyService.updateClassProperty(certClassTheorietrainer.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassSEFLadegut);
//
//		Inheritance i1 = new Inheritance();
//		i1.setSource(certClassRoot.getId());
//		i1.setTarget(certClassSEF1.getId());
//		relationships.add(i1);
//		Inheritance i2 = new Inheritance();
//		i2.setSource(certClassRoot.getId());
//		i2.setTarget(certClassSEF2.getId());
//		relationships.add(i2);
//		Inheritance i3 = new Inheritance();
//		i3.setSource(certClassRoot.getId());
//		i3.setTarget(certClassSEFPraxistrainer.getId());
//		relationships.add(i3);
//		Inheritance i4 = new Inheritance();
//		i4.setSource(certClassRoot.getId());
//		i4.setTarget(certClassSEFWorkshop.getId());
//		relationships.add(i4);
//		Inheritance i5 = new Inheritance();
//		i5.setSource(certClassRoot.getId());
//		i5.setTarget(certClassSEFNotarzt.getId());
//		relationships.add(i5);
//		Inheritance i6 = new Inheritance();
//		i6.setSource(certClassRoot.getId());
//		i6.setTarget(certClassSEFLadegut.getId());
//		relationships.add(i6);
//		Inheritance i7 = new Inheritance();
//		i7.setSource(certClassRoot.getId());
//		i7.setTarget(certClassTheorietrainer.getId());
//		relationships.add(i7);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
//	}
//
//	public void createClassTasksRK(ClassConfiguration classConfig) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
//		// Task
//		TaskClassDefinition taskClassRoot = (TaskClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Tätigkeit", tenantId);
//
//		TaskClassDefinition taskClassEinsatz = (TaskClassDefinition) obtainClass(tenantId, TASK_RK_EINSATZ,
//				taskClassRoot);
//		ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(taskClassEinsatz.getId(), "Description",
//				tenantId);
//		cp.setDefaultValues(Arrays.asList("Rettungseinsatz"));
//		classPropertyService.updateClassProperty(taskClassEinsatz.getId(), cp.getId(), cp);
//
//		cp = classPropertyService.getClassPropertyByName(taskClassEinsatz.getId(), "Description", tenantId);
//		cp.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
//				TestDataInstances.RolesAmbulanceService.SANITÄTER,
//				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
//		classPropertyService.updateClassProperty(taskClassEinsatz.getId(), cp.getId(), cp);
//		classDefinitions.add(taskClassEinsatz);
//		//
//		TaskClassDefinition taskClassAusfahrt = (TaskClassDefinition) obtainClass(tenantId, TASK_RK_AUSFAHRT,
//				taskClassRoot);
//		cp = classPropertyService.getClassPropertyByName(taskClassAusfahrt.getId(), "Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("Sanitätseinsatz"));
//		classPropertyService.updateClassProperty(taskClassAusfahrt.getId(), cp.getId(), cp);
//		cp = classPropertyService.getClassPropertyByName(taskClassAusfahrt.getId(), "role", tenantId);
//		cp.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
//				TestDataInstances.RolesAmbulanceService.SANITÄTER,
//				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
//		classPropertyService.updateClassProperty(taskClassAusfahrt.getId(), cp.getId(), cp);
//		classDefinitions.add(taskClassAusfahrt);
//		//
//		TaskClassDefinition taskClassDienst = (TaskClassDefinition) obtainClass(tenantId, "Dienst", taskClassRoot);
//		cp = classPropertyService.getClassPropertyByName(taskClassDienst.getId(), "Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("Dienst"));
//		classPropertyService.updateClassProperty(taskClassDienst.getId(), cp.getId(), cp);
//		cp = classPropertyService.getClassPropertyByName(taskClassDienst.getId(), "role", tenantId);
//		cp.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.DISPONENT,
//				TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
//				TestDataInstances.RolesAmbulanceService.SANITÄTER,
//				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
//		classPropertyService.updateClassProperty(taskClassDienst.getId(), cp.getId(), cp);
//		classDefinitions.add(taskClassDienst);
//
//		Inheritance i1 = new Inheritance();
//		i1.setSource(taskClassRoot.getId());
//		i1.setTarget(taskClassEinsatz.getId());
//		relationships.add(i1);
//		Inheritance i2 = new Inheritance();
//		i2.setSource(taskClassRoot.getId());
//		i2.setTarget(taskClassAusfahrt.getId());
//		relationships.add(i2);
//		Inheritance i3 = new Inheritance();
//		i3.setSource(taskClassRoot.getId());
//		i3.setTarget(taskClassDienst.getId());
//		relationships.add(i3);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
//	}
//
//	public void createClassRolesRK(ClassConfiguration classConfig) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
//		FunctionClassDefinition functionClassRoot = (FunctionClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Funktion", tenantId);
//		// Function
//		FunctionClassDefinition functionClassAmbulanceService = (FunctionClassDefinition) obtainClass(tenantId,
//				"Funktion durch Ausbildung", functionClassRoot);
//		FlatPropertyDefinition<Object> pdDescription = obtainProperty("Description", PropertyType.TEXT, tenantId);
//		functionClassAmbulanceService.getProperties()
//				.add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
//		classDefinitions.add(functionClassAmbulanceService);
//		//
//		FunctionClassDefinition functionClassRettungssanitäter = (FunctionClassDefinition) obtainClass(tenantId,
//				"Rettungssanitäter", functionClassAmbulanceService);
//		classDefinitions.add(functionClassRettungssanitäter);
//		//
//		FunctionClassDefinition functionClassNotfallsanitäter = (FunctionClassDefinition) obtainClass(tenantId,
//				"Notfallsanitäter", functionClassAmbulanceService);
//		classDefinitions.add(functionClassNotfallsanitäter);
//
//		Inheritance i1 = new Inheritance();
//		i1.setSource(functionClassRoot.getId());
//		i1.setTarget(functionClassAmbulanceService.getId());
//		relationships.add(i1);
//		Inheritance i2 = new Inheritance();
//		i1.setSource(functionClassAmbulanceService.getId());
//		i1.setTarget(functionClassRettungssanitäter.getId());
//		relationships.add(i2);
//		Inheritance i3 = new Inheritance();
//		i1.setSource(functionClassAmbulanceService.getId());
//		i1.setTarget(functionClassNotfallsanitäter.getId());
//		relationships.add(i3);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
//	}
//
//	public void createClassVerdiensteRK(ClassConfiguration classConfig) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
//		AchievementClassDefinition certClassRoot = (AchievementClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Verdienst", tenantId);
//		// Fahrtenspange
//		AchievementClassDefinition certClassFahrtenspangeBronze = (AchievementClassDefinition) obtainClass(tenantId,
//				"Fahrtenspange Bronze", certClassRoot);
//		ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeBronze.getId(),
//				"Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 1000 Fahrten mit dem RK"));
//		classPropertyService.updateClassProperty(certClassFahrtenspangeBronze.getId(), cp.getId(), cp);
//		cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeBronze.getId(), "issuedOn", tenantId);
//		classPropertyService.updateClassProperty(certClassFahrtenspangeBronze.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassFahrtenspangeBronze);
//		//
//		AchievementClassDefinition certClassFahrtenspangeSilber = (AchievementClassDefinition) obtainClass(tenantId,
//				"Fahrtenspange Silber", certClassRoot);
//		cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeSilber.getId(), "Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 2500 Fahrten mit dem RK"));
//		classPropertyService.updateClassProperty(certClassFahrtenspangeSilber.getId(), cp.getId(), cp);
//		cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeSilber.getId(), "issuedOn", tenantId);
//		classPropertyService.updateClassProperty(certClassFahrtenspangeSilber.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassFahrtenspangeSilber);
//		//
//		AchievementClassDefinition certClassFahrtenspangeGold = (AchievementClassDefinition) obtainClass(tenantId,
//				"Fahrtenspange Gold", certClassRoot);
//		cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeGold.getId(), "Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 5000 Fahrten mit dem RK"));
//		classPropertyService.updateClassProperty(certClassFahrtenspangeGold.getId(), cp.getId(), cp);
//		cp = classPropertyService.getClassPropertyByName(certClassFahrtenspangeGold.getId(), "issuedOn", tenantId);
//		classPropertyService.updateClassProperty(certClassFahrtenspangeGold.getId(), cp.getId(), cp);
//		classDefinitions.add(certClassFahrtenspangeGold);
//
//		Inheritance i1 = new Inheritance();
//		i1.setSource(certClassRoot.getId());
//		i1.setTarget(certClassFahrtenspangeBronze.getId());
//		relationships.add(i1);
//		Inheritance i2 = new Inheritance();
//		i2.setSource(certClassRoot.getId());
//		i2.setTarget(certClassFahrtenspangeSilber.getId());
//		relationships.add(i2);
//		Inheritance i3 = new Inheritance();
//		i3.setSource(certClassRoot.getId());
//		i3.setTarget(certClassFahrtenspangeGold.getId());
//		relationships.add(i3);
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
//	}
//
//}

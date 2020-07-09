package at.jku.cis.iVolunteer.marketplace.rule.engine.test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
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
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.user.User;

@Service
public class TestDataClasses {

	@Autowired
	private ClassConfigurationController classConfigurationController;
	@Autowired
	private ClassDefinitionRepository classDefinitionRepository;
	@Autowired
	private ClassInstanceRepository classInstanceRepository;
	@Autowired
	private ClassPropertyService classPropertyService;
	@Autowired
	private RelationshipRepository relationshipRepository;
	@Autowired
	private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired
	private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private ClassInstanceService classInstanceService;
	@Autowired
	private CoreTenantRestClient coreTenantRestClient;
	@Autowired
	private UserRepository userRepository;

	public static final String ROOT_FREIWILLIGENPASS_EINTRAG = "Freiwilligenpass-\nEintrag";
	public static final String CERTIFICATE_HEAD_AUSBILDUNG = "Ausbildung";

	public static final String CERTIFICATE_DRIVING_LICENSE = "Führerschein";
	public static final String CERTIFICATE_DRIVING_LICENSE_CAR = "Führerschein B";
	public static final String CERTIFICATE_DRIVING_LICENSE_TRUCK = "Führerschein C";
	public static final String CERTIFICATE_DRIVING_LICENSE_BUS = "Führerschein D";
	public static final String CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE = "Führerschein A";

	public static final String COMPETENCE_DRIVING = "Fahren";
	public static final String COMPETENCE_DRIVING_CAR = "Autofahren";
	public static final String COMPETENCE_DRIVING_TRUCK = "LKW-Fahren";
	public static final String COMPETENCE_DRIVING_BUS = "Busfahren";
	public static final String COMPETENCE_DRIVING_MOTORCYCLE = "Motorradfahren";

	public static final String PROPERTY_DRIVING_LEVEL = "Fahrlevel";
	public static final String PROPERTY_EVIDENCE = "Evidenz";

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

	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String RKWILHERING = "RK Wilhering";
	
	
	private final List<ClassDefinition> classDefinitions = new ArrayList<>();
	private final List<Relationship> relationships = new ArrayList<>();
	
	public enum LicenseType {
		A, B, C, D, E
	}

	public enum DrivingLevel {
		// levels from
		// https://www.semanticscholar.org/paper/DRIVER-COMPETENCE-IN-A-HIERARCHICAL-PERSPECTIVE%3B-Per%C3%A4aho-Keskinen/c64e45ece27720782367038220abe008924151a2
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

	public void createClassConfigurations() {
		String slotName = "Test-Rule-Engine";
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		
		//Create ClassConfiguration with all the "header classes"
		ClassConfiguration classConfigFF = classConfigurationController.createNewClassConfiguration(new String[] {tenantId, slotName + " FF", ""});
		
		//add ClassDefinitions and Relationships created by the function above to our lists of classDefinitions and relationships
		classDefinitionRepository.findAll(classConfigFF.getClassDefinitionIds()).forEach(this.classDefinitions::add);
		relationshipRepository.findAll(classConfigFF.getRelationshipIds()).forEach(this.relationships::add);
		
		//generate our classes and relationships (and add them to the lists)
		createGeneralClasses(classConfigFF.getTenantId());
		createGeneralCompetences(classConfigFF.getTenantId());
		createProperties(classConfigFF);
		
		//Debug print
//		this.classDefinitions.forEach(cd -> System.out.println(cd.getId() + " " + cd.getName()));
//		this.relationships.forEach(cd -> System.out.println(cd.getId() + " " + cd.getRelationshipType()));

		//Save our relationships and classDefintions in the DB
		classDefinitionRepository.save(this.classDefinitions);
		relationshipRepository.save(this.relationships);
		
		//Set ids in configurator
		classConfigFF.setClassDefinitionIds(this.classDefinitions.stream().map(cd -> cd.getId()).collect(Collectors.toList()));
		classConfigFF.setRelationshipIds(this.relationships.stream().map(r -> r.getId()).collect(Collectors.toList()));

		//save classConfiguration
		this.classConfigurationController.saveClassConfiguration(classConfigFF);

		//...Repeat the same for RK

		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassConfiguration classConfigRK = classConfigurationController.createNewClassConfiguration(new String[] {tenantId, slotName + " RK", ""});

		this.classDefinitions.clear();
		this.relationships.clear();
		classDefinitionRepository.findAll(classConfigRK.getClassDefinitionIds()).forEach(this.classDefinitions::add);
		relationshipRepository.findAll(classConfigRK.getRelationshipIds()).forEach(this.relationships::add);

		createGeneralClasses(classConfigRK.getTenantId());
		createGeneralCompetences(classConfigRK.getTenantId());
		createProperties(classConfigRK);

		createClassCertificatesRK(classConfigRK.getTenantId());
		createClassTasksRK(classConfigRK.getTenantId());
		createClassRolesRK(classConfigRK.getTenantId());
		createClassVerdiensteRK(classConfigRK.getTenantId());
		
//		this.classDefinitions.forEach(cd -> {
//			System.out.println(cd.getId() + " " + cd.getName());
//			cd.getProperties().forEach(p -> System.out.println("--" + p.getId() + " " + p.getName()));
//			
//		});
//		this.relationships.forEach(cd -> System.out.println(cd.getId() + " " + cd.getRelationshipType() + " " + cd.getSource() + " " + cd.getTarget()));

//
		classDefinitionRepository.save(this.classDefinitions);
		relationshipRepository.save(this.relationships);
		classConfigRK.setClassDefinitionIds(this.classDefinitions.stream().map(cd -> cd.getId()).collect(Collectors.toList()));
		classConfigRK.setRelationshipIds(this.relationships.stream().map(r -> r.getId()).collect(Collectors.toList()));

		this.classConfigurationController.saveClassConfiguration(classConfigRK);

	}

	public void createGeneralClasses(String tenantId) {
//		List<ClassDefinition> classDefinitions = new ArrayList<>();
//		List<Relationship> relationships = new ArrayList<>();
//
//		// Freiwilligenpasseinträge analog Alex in InitializationService
//		ClassDefinition root = obtainClassFreiwilligenpassEintrag(tenantId);
		ClassDefinition root = this.classDefinitions.stream().filter(cd -> cd.isRoot()).findFirst().get();
		
		
		
		ClassDefinition certificateClass = obtainClassZertifikat(tenantId, root);

		
		
//		ClassDefinition competenceClass = obtainClassKompetenz(tenantId, root);
//		ClassDefinition taskClass = obtainClassTask(tenantId, root);
//		ClassDefinition functionClass = obtainClassFunktion(tenantId, root);
//		ClassDefinition verdienstClass = obtainClassVerdienst(tenantId, root);

//		classDefinitions.add(root);
		this.classDefinitions.add(certificateClass);
//		classDefinitions.add(competenceClass);
//		classDefinitions.add(taskClass);
//		classDefinitions.add(functionClass);
//		classDefinitions.add(verdienstClass);

		Inheritance r1 = new Inheritance();
		r1.setId(new ObjectId().toHexString());
		r1.setRelationshipType(RelationshipType.INHERITANCE);
		r1.setTarget(certificateClass.getId());
		r1.setSource(root.getId());
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

		this.relationships.add(r1);
//		relationships.add(r2);
//		relationships.add(r3);
//		relationships.add(r4);
//		relationships.add(r5);

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
	}

	public void createGeneralCompetences(String tenantId) {
		createDrivingSkills(tenantId);
		createRandomCompetences(tenantId);
	}

	public void createProperties(ClassConfiguration classConfig) {
		if (propertyDefinitionRepository.getByNameAndTenantId("Alter", classConfig.getTenantId()) == null) {
			PropertyDefinition<Object> pdAlter = new PropertyDefinition<Object>();
			pdAlter.setTenantId(classConfig.getTenantId());
			pdAlter.setMarketplaceId(classConfig.getMarketplaceId());
			pdAlter.setType(PropertyType.WHOLE_NUMBER);
			pdAlter.setName("Alter");
			propertyDefinitionRepository.save(pdAlter);
		}
	}
	
	protected void deleteInstances(User volunteer, String tenantId, String className) {
		// System.out.println("Volunteer: " + volunteer + " tenant: " + tenantId + "
		// className " + className);
		ClassDefinition classComp = classDefinitionService.getByName(className, tenantId);
		if (classComp != null)
			classInstanceService.deleteClassInstances(volunteer, classComp.getId(), tenantId);
	}

	protected ClassDefinition obtainClass(String tenantId, String name, ClassDefinition parent) {
		// System.out.println(" obtain class " + name + ", tenantId: " + tenantId + ",
		// parent: " + parent.getName());
//		ClassDefinition a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
//		if (a0 == null) {
			return createClass(tenantId, name, parent);
//			a0 = classDefinitionRepository.findByNameAndTenantId(name, tenantId);
//		}
//		return a0;
	}

	public PropertyDefinition<Object> obtainProperty(String name, PropertyType type, String tenantId) {
		 
		PropertyDefinition<Object> pd = propertyDefinitionRepository.getByNameAndTenantId(name, tenantId);
		if (pd == null) {
			pd = new PropertyDefinition<Object>(name, type, tenantId);
			propertyDefinitionRepository.save(pd);
		}

		return pd;
	}

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
//			PropertyDefinition<Object> pd = obtainProperty("Description", PropertyType.TEXT, tenantId);
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

	public void createDrivingSkills(String tenantId) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		
		ClassDefinition certClass = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Zertifikat")).findFirst().get();
//		
		// certificate --> driving license
		ClassDefinition certClassDrivingLicense = obtainClassDrivingLicense(tenantId);
		ClassDefinition certClassDrivingLicenseCar = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_CAR, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseTruck = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_TRUCK, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseBus = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_BUS, certClassDrivingLicense);
		ClassDefinition certClassDrivingLicenseMotorcycle = obtainClass(tenantId, CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, certClassDrivingLicense);

		this.classDefinitions.add(certClassDrivingLicense);
		this.classDefinitions.add(certClassDrivingLicenseCar);
		this.classDefinitions.add(certClassDrivingLicenseTruck);
		this.classDefinitions.add(certClassDrivingLicenseBus);
		this.classDefinitions.add(certClassDrivingLicenseMotorcycle);
		
		Inheritance i0 = new Inheritance(certClass.getId(), certClassDrivingLicense.getId(), certClass.getId());
		i0.setId(new ObjectId().toHexString());
		this.relationships.add(i0);
		
		// grouping and hierarchy in certificate "driving license"
		Inheritance i1 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseCar.getId(),
				certClassDrivingLicense.getId());
		i1.setId(new ObjectId().toHexString());
		this.relationships.add(i1);
		Inheritance i2 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseTruck.getId(),
				certClassDrivingLicense.getId());
		i2.setId(new ObjectId().toHexString());
		this.relationships.add(i2);
		Inheritance i3 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseBus.getId(),
				certClassDrivingLicense.getId());
		i3.setId(new ObjectId().toHexString());
		this.relationships.add(i3);
		Inheritance i4 = new Inheritance(certClassDrivingLicense.getId(), certClassDrivingLicenseMotorcycle.getId(),
				certClassDrivingLicense.getId());
		i4.setId(new ObjectId().toHexString());
		this.relationships.add(i4);

		ClassDefinition compClass = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Kompetenz")).findFirst().get();

		// competences --> driving
		ClassDefinition compClassDriving = createDrivingCompetence(tenantId);
		ClassDefinition compClassDrivingCar = obtainClass(tenantId, COMPETENCE_DRIVING_CAR, compClassDriving);
		ClassDefinition compClassDrivingTruck = obtainClass(tenantId, COMPETENCE_DRIVING_TRUCK, compClassDriving);
		ClassDefinition compClassDrivingBus = obtainClass(tenantId, COMPETENCE_DRIVING_BUS,	compClassDriving);
		ClassDefinition compClassDrivingMotorcycle = obtainClass(tenantId, COMPETENCE_DRIVING_MOTORCYCLE, compClassDriving);

		this.classDefinitions.add(compClassDriving);
		this.classDefinitions.add(compClassDrivingCar);
		this.classDefinitions.add(compClassDrivingTruck);
		this.classDefinitions.add(compClassDrivingBus);
		this.classDefinitions.add(compClassDrivingMotorcycle);

		
		Inheritance i00 = new Inheritance(compClass.getId(), compClassDriving.getId(), compClass.getId());
		i00.setId(new ObjectId().toHexString());
		this.relationships.add(i00);

		// grouping and hierarchy in competence "driving"
		Inheritance i5 = new Inheritance(compClassDriving.getId(), compClassDrivingCar.getId(),
				compClassDriving.getId());
		i5.setId(new ObjectId().toHexString());
		this.relationships.add(i5);
		Inheritance i6 = new Inheritance(compClassDriving.getId(), compClassDrivingTruck.getId(),
				compClassDriving.getId());
		i6.setId(new ObjectId().toHexString());
		this.relationships.add(i6);
		Inheritance i7 = new Inheritance(compClassDriving.getId(), compClassDrivingBus.getId(),
				compClassDriving.getId());
		i7.setId(new ObjectId().toHexString());
		this.relationships.add(i7);
		Inheritance i8 = new Inheritance(compClassDriving.getId(), compClassDrivingMotorcycle.getId(),
				compClassDriving.getId());
		i8.setId(new ObjectId().toHexString());
		this.relationships.add(i8);
		
//	 AK: erzeugt einen Kreis im Editor - führt zu Problemen - ist auch derzeit nicht möglich
		
		// competence driving needs evidence of certificate (driving license)
//		Association a1 = new Association(compClassDrivingCar.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a1.setId(new ObjectId().toHexString());
//		this.relationships.add(a1);
//		Association a2 = new Association(compClassDrivingTruck.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a2.setId(new ObjectId().toHexString());
//		this.relationships.add(a2);
//		Association a3 = new Association(compClassDrivingBus.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a3.setId(new ObjectId().toHexString());
//		this.relationships.add(a3);
//		Association a4 = new Association(compClassDrivingMotorcycle.getId(), certClassDrivingLicense.getId(),
//				AssociationCardinality.ONE, AssociationCardinality.ONE);
//		a4.setId(new ObjectId().toHexString());
//		this.relationships.add(a4);
		
		

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
	}

	private void printAllAssets(User volunteer, String tenantId) {
		System.out.println("all assets from " + volunteer.getUsername() + ": ");
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.ACHIEVEMENT);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.COMPETENCE);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.FUNCTION);
		printArchetypeAssets(volunteer, tenantId, ClassArchetype.TASK);
	}

	private void printArchetypeAssets(User volunteer, String tenantId, ClassArchetype classArchetype) {
		System.out.println("..... " + classArchetype.getArchetype() + ": ");
		List<ClassDefinition> classes = classDefinitionRepository.getByClassArchetypeAndTenantId(classArchetype,
				tenantId);
		for (ClassDefinition cd : classes) {
			List<ClassInstance> assets = classInstanceRepository
					.getByUserIdAndClassDefinitionIdAndTenantId(volunteer.getId(), cd.getId(), tenantId);
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
//		ClassDefinition drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE,
//				tenantId);
//		if (drivingLicense == null) {
			ClassDefinition certificateClass = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Zertifikat")).findFirst().get();			
			
			ClassDefinition drivingLicense = new ClassDefinition();
			drivingLicense.setId(new ObjectId().toHexString());
			drivingLicense.setMarketplaceId(marketplaceService.getMarketplaceId());
			drivingLicense.setName(CERTIFICATE_DRIVING_LICENSE);
			drivingLicense.setParentId(certificateClass.getId());
			drivingLicense.setTimestamp(new Date());
			drivingLicense.setTenantId(tenantId);
			drivingLicense.setClassArchetype(ClassArchetype.ACHIEVEMENT);
			drivingLicense.setWriteProtected(true);
			drivingLicense.setParentId(certificateClass.getId());
			drivingLicense.setProperties(new ArrayList<ClassProperty<Object>>());

//			classDefinitionRepository.save(drivingLicense);
//		}
//		drivingLicense = classDefinitionRepository.findByNameAndTenantId(CERTIFICATE_DRIVING_LICENSE, tenantId);
		return drivingLicense;
	}

	protected ClassDefinition createDrivingCompetence(String tenantId) {
//		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Kompetenz", tenantId);
//		
		ClassDefinition compClass = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Kompetenz")).findFirst().get();

		ClassDefinition c1 = new ClassDefinition();
		c1.setId(new ObjectId().toHexString());
		c1.setName(COMPETENCE_DRIVING);
		c1.setClassArchetype(ClassArchetype.COMPETENCE);
		c1.setTenantId(tenantId);
		c1.setRoot(false);
		c1.setParentId(compClass.getId());
		
		// set properties
		List<Object> levelValues = Arrays.asList(DrivingLevel.LEVEL1.getName(), DrivingLevel.LEVEL2.getName(),
				DrivingLevel.LEVEL3.getName(), DrivingLevel.LEVEL4.getName());
		PropertyDefinition<Object> pdLevel = obtainProperty(PROPERTY_DRIVING_LEVEL, PropertyType.TEXT, tenantId);
		pdLevel.setAllowedValues(levelValues);
		
		PropertyDefinition<Object> pdEvidence = obtainProperty(PROPERTY_EVIDENCE, PropertyType.TEXT, tenantId);
		PropertyDefinition<Object> pdIssued = obtainProperty("Issued", PropertyType.DATE, tenantId);
		
		c1.setProperties(new ArrayList<ClassProperty<Object>>());
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdEvidence));
		c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdIssued));
//		classDefinitionRepository.save(c1);
		return c1;
	}

	protected void createRandomCompetences(String tenantId) {
//		CompetenceClassDefinition compClass = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Kompetenz", classConfig.getTenantId());
		ClassDefinition compClass = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Kompetenz")).findFirst().get();
		
//		CompetenceClassDefinition c1 = (CompetenceClassDefinition) classDefinitionRepository
//				.findByNameAndTenantId("Maturity", classConfig.getTenantId());
//		if (c1 == null) {
			ClassDefinition c1 = new ClassDefinition();
			c1.setId(new ObjectId().toHexString());
			c1.setConfigurationId(tenantId);
			c1.setName("Maturity");
			c1.setClassArchetype(ClassArchetype.COMPETENCE);
			c1.setTenantId(tenantId);
			c1.setRoot(false);
			c1.setParentId(compClass.getId());
			// set properties
			PropertyDefinition<Object> pdLevel = obtainProperty("Maturity Level", PropertyType.WHOLE_NUMBER, tenantId);
			c1.setProperties(new ArrayList<ClassProperty<Object>>());
			c1.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdLevel));
//			classDefinitionRepository.save(c1);
			Inheritance i = new Inheritance();
			i.setId(new ObjectId().toHexString());
			i.setSource(compClass.getId());
			i.setTarget(c1.getId());
			
			this.classDefinitions.add(c1);
			this.relationships.add(i);
			
			
//			relationshipRepository.save(i);
//			classConfig.getRelationshipIds().add(i.getId());
//		}
	}

	protected ClassDefinition obtainClassZertifikat(String tenantId, ClassDefinition root) {
//		ClassDefinition certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
//		if (certificateClass == null) {
			ClassDefinition certificateClass = new ClassDefinition();
			certificateClass.setId(new ObjectId().toHexString());
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
//			classDefinitionRepository.save(certificateClass);
//		}
//		certificateClass = classDefinitionRepository.findByNameAndTenantId("Zertifikat", tenantId);
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

	protected ClassDefinition createClass(String tenantId, String name, ClassDefinition parent) {
		ClassDefinition a = new ClassDefinition();
		a.setId(new ObjectId().toHexString());
		a.setMarketplaceId(marketplaceService.getMarketplaceId());
		a.setName(name);
		a.setParentId(parent.getId());
		a.setTimestamp(new Date());
		a.setTenantId(tenantId);
		a.setClassArchetype(parent.getClassArchetype());
		/*
		 * parent.getProperties().forEach(p -> {
		 * System.out.println("-------------------> " + p.getName()); });
		 */
		//AK: im Konfigurator können Properties nicht überschrieben werden, also ein Property in einer parent Klasse kann nicht in den children verwendet werden
		a.setProperties(parent.getProperties());
		// classDefinitionRepository.save(a);
		return a;
	}

//	protected ClassDefinition newClassDefinition(ClassDefinition parent) {
//		ClassDefinition cd = new ClassDefini;
//		switch (parent.getClassArchetype()) {
//			case ACHIEVEMENT:
//				cd = new AchievementClassDefinition();
//				cd.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//				break;
//			case COMPETENCE:
//				cd = new CompetenceClassDefinition();
//				cd.setClassArchetype(ClassArchetype.COMPETENCE);
//				break;
//			case TASK:
//				cd = new TaskClassDefinition();
//				cd.setClassArchetype(ClassArchetype.TASK);
//				break;
//			case FUNCTION:
//				cd = new FunctionClassDefinition();
//				cd.setClassArchetype(ClassArchetype.FUNCTION);
//				break;
//			default:
//				break;
//		}
//		return cd;
//	}

	public void createClassVolunteerRK() {
		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		ClassDefinition volClass = classDefinitionRepository.findByNameAndTenantId("Volunteer RK", tenantId);
		if (volClass == null) {
			volClass = new ClassDefinition();
			volClass.setName("Volunteer RK");
			volClass.setMarketplaceId(marketplaceService.getMarketplaceId());
			volClass.setTimestamp(new Date());
			volClass.setTenantId(tenantId);
			volClass.setWriteProtected(true);
			volClass.setProperties(new ArrayList<ClassProperty<Object>>());

			PropertyDefinition<Object> pd = obtainProperty("Eintrittsdatum", PropertyType.DATE, tenantId);
			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("Hauptdienststelle", PropertyType.TEXT, tenantId);
			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
			pd = obtainProperty("Dienstart", PropertyType.TEXT, tenantId);
			volClass.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pd));

			classDefinitionRepository.save(volClass);
		}
		volClass = classDefinitionRepository.findByNameAndTenantId("Volunteer RK", tenantId);
	}

	public void createClassCertificatesRK(String tenantId) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		
		
		ClassDefinition certClassRoot = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Zertifikat")).findFirst().get();
		
		// Training certificates
		ClassDefinition certClassTraining = obtainClass(tenantId, "Ausbildung", certClassRoot);
		this.classDefinitions.add(certClassRoot);
		// Certificate SEF-MODUL 1

		ClassDefinition certClassSEF1 =  obtainClass(tenantId, CERTIFICATE_SEF_MODUL1, certClassTraining);
		
// AK: keine Solche classProperty vorhanden muss erst erzeugt / von der DB geholt werden werden - wie du eh vorher schon gemacht hast

//		ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(certClassSEF1.getId(), "Description", tenantId);
//		cp.setDefaultValues(Arrays.asList("SEF – Perfektionstraining für neue Einsatzlenker/innen (SEF-MODUL 1)"));
//		classPropertyService.updateClassProperty(certClassSEF1.getId(), cp.getId(), cp);
		
		ClassProperty<Object> cp = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("Description", PropertyType.TEXT, tenantId));
		cp.setDefaultValues(Arrays.asList("SEF – Perfektionstraining für neue Einsatzlenker/innen (SEF-MODUL 1)"));
		certClassSEF1.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEF1.getProperties().add(cp);
		
		this.classDefinitions.add(certClassSEF1);
		//
		ClassDefinition certClassSEF2 = obtainClass(tenantId, CERTIFICATE_SEF_MODUL2, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEF2.getId(), "Description", tenantId);
		cp.setDefaultValues(Arrays.asList("SEF – Theorie- & Praxistraining für erfahrene Einsatzlenker/innen (SEF-MODUL 2)"));
//		classPropertyService.updateClassProperty(certClassSEF2.getId(), cp.getId(), cp);
		certClassSEF2.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEF2.getProperties().add(cp);
		this.classDefinitions.add(certClassSEF2);
		//
		ClassDefinition certClassSEFPraxistrainer = obtainClass(tenantId, CERTIFICATE_SEF_AUSFORTBILDUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFPraxistrainer.getId(), "Description", tenantId);
		cp.setDefaultValues(Arrays.asList("SEF – Aus- und Fortbildung für SEF-Praxistrainer/innen"));
		certClassSEFPraxistrainer.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEFPraxistrainer.getProperties().add(cp);
		this.classDefinitions.add(certClassSEFPraxistrainer);
		//
		ClassDefinition certClassSEFWorkshop = obtainClass(tenantId, CERTIFICATE_SEF_WORKSHOP, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFWorkshop.getId(), "Description", tenantId);
		cp.setDefaultValues(Arrays.asList("SEF Workshop"));
		certClassSEFWorkshop.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEFWorkshop.getProperties().add(cp);
		this.classDefinitions.add(certClassSEFWorkshop);
		//
		ClassDefinition certClassSEFNotarzt = obtainClass(tenantId, CERTIFICATE_SEF_TRAINING_NOTARZT, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFNotarzt.getId(), "Description", tenantId);
		cp.setDefaultValues(Arrays.asList("SEF – Theorie- und Praxistraining für Notarztdienste"));
		certClassSEFNotarzt.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEFNotarzt.getProperties().add(cp);
		this.classDefinitions.add(certClassSEFNotarzt);
		//
		ClassDefinition certClassSEFLadegut = obtainClass(tenantId, CERTIFICATE_SEF_LADEGUTSICHERUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassSEFLadegut.getId(), "Description",tenantId);
		cp.setDefaultValues(Arrays.asList("SEF – Ladegutsicherung für Rotkreuz LKW-Lenker/innen"));
		certClassSEFLadegut.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassSEFLadegut.getProperties().add(cp);
		this.classDefinitions.add(certClassSEFLadegut);
		//
		ClassDefinition certClassTheorietrainer = obtainClass(tenantId, CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, certClassTraining);
//		cp = classPropertyService.getClassPropertyByName(certClassTheorietrainer.getId(), "Description", tenantId);
		cp.setDefaultValues(Arrays.asList("SEF – Theorietrainerausbildung"));
		certClassTheorietrainer.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassTheorietrainer.getProperties().add(cp);
		this.classDefinitions.add(certClassTheorietrainer);

		Inheritance i1 = new Inheritance();
		i1.setId(new ObjectId().toHexString());
		i1.setSource(certClassRoot.getId());
		i1.setTarget(certClassSEF1.getId());
		this.relationships.add(i1);
		Inheritance i2 = new Inheritance();
		i2.setId(new ObjectId().toHexString());
		i2.setSource(certClassRoot.getId());
		i2.setTarget(certClassSEF2.getId());
		this.relationships.add(i2);
		Inheritance i3 = new Inheritance();
		i3.setId(new ObjectId().toHexString());
		i3.setSource(certClassRoot.getId());
		i3.setTarget(certClassSEFPraxistrainer.getId());
		this.relationships.add(i3);
		Inheritance i4 = new Inheritance();
		i4.setId(new ObjectId().toHexString());
		i4.setSource(certClassRoot.getId());
		i4.setTarget(certClassSEFWorkshop.getId());
		this.relationships.add(i4);
		Inheritance i5 = new Inheritance();
		i5.setId(new ObjectId().toHexString());
		i5.setSource(certClassRoot.getId());
		i5.setTarget(certClassSEFNotarzt.getId());
		this.relationships.add(i5);
		Inheritance i6 = new Inheritance();
		i6.setId(new ObjectId().toHexString());
		i6.setSource(certClassRoot.getId());
		i6.setTarget(certClassSEFLadegut.getId());
		this.relationships.add(i6);
		Inheritance i7 = new Inheritance();
		i7.setId(new ObjectId().toHexString());
		i7.setSource(certClassRoot.getId());
		i7.setTarget(certClassTheorietrainer.getId());
		this.relationships.add(i7);

//		for (ClassDefinition cd : classDefinitions) {
//			classDefinitionRepository.save(cd);
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//		}
	}

	public void createClassTasksRK(String tenantId) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
		// Task
		ClassDefinition taskClassRoot = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Tätigkeit")).findFirst().get();

		ClassDefinition taskClassEinsatz = obtainClass(tenantId, TASK_RK_EINSATZ,taskClassRoot);
		ClassProperty<Object> cp = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("Description", PropertyType.LONG_TEXT, tenantId));	
		cp.setDefaultValues(Arrays.asList("Rettungseinsatz"));
		taskClassEinsatz.setProperties(new ArrayList<ClassProperty<Object>>());
		taskClassEinsatz.getProperties().add(cp);
		
		//AK: Verstehe ich nicht  - Rollen "Einsatzleiter" sollen in die Description Property?; sollte es dafür nicht eine eigene Property geben (zB Roles)
		//AK: Wenn "AllowedValues" gesetzt wurden, müssen "DefaultValues" Teilmenge der "AllowedValues" sein.
		//AK: Ich hab das jetzt mal so intepretiert, dass dass in eine eigene Property gehört (nicht description) - weil du unten auch "role" verwendet hast
		
//		cp = classPropertyService.getClassPropertyByName(taskClassEinsatz.getId(), "Roles", tenantId);
		ClassProperty<Object> cpRoles = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("role", PropertyType.TEXT, tenantId));	
		cpRoles.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
				TestDataInstances.RolesAmbulanceService.SANITÄTER,
				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
		taskClassEinsatz.getProperties().add(cpRoles);
		this.classDefinitions.add(taskClassEinsatz);
		//
		
		ClassDefinition taskClassAusfahrt = obtainClass(tenantId, TASK_RK_AUSFAHRT,taskClassRoot);
		
		cp.setDefaultValues(new ArrayList<Object>());
		cp.setDefaultValues(Arrays.asList("Sanitätseinsatz"));
		
		//AK: Hier würdest du das gerade erzeugte ClassProperty wieder überschreiben, ohne es vorher in der ClassDefinition to speichern
//		cp = classPropertyService.getClassPropertyByName(taskClassAusfahrt.getId(), "role", tenantId);
		cpRoles.setAllowedValues(new ArrayList<Object>());
		cpRoles.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
				TestDataInstances.RolesAmbulanceService.SANITÄTER,
				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
		taskClassAusfahrt.setProperties(new ArrayList<>());
		taskClassAusfahrt.getProperties().add(cp);
		taskClassAusfahrt.getProperties().add(cpRoles);
		
		this.classDefinitions.add(taskClassAusfahrt);
		//
		
		ClassDefinition taskClassDienst = obtainClass(tenantId, "Dienst", taskClassRoot);
		cp.setDefaultValues(new ArrayList<Object>());
		cp.setDefaultValues(Arrays.asList("Dienst"));
		cpRoles.setAllowedValues(new ArrayList<Object>());
		cpRoles.setAllowedValues(Arrays.asList(TestDataInstances.RolesAmbulanceService.DISPONENT,
				TestDataInstances.RolesAmbulanceService.EINSATZLENKER,
				TestDataInstances.RolesAmbulanceService.SANITÄTER,
				TestDataInstances.RolesAmbulanceService.AUSZUBILDENDER));
		taskClassDienst.setProperties(new ArrayList<ClassProperty<Object>>());
		taskClassDienst.getProperties().add(cp);
		taskClassDienst.getProperties().add(cpRoles);
		this.classDefinitions.add(taskClassDienst);

		Inheritance i1 = new Inheritance();
		i1.setId(new ObjectId().toHexString());
		i1.setSource(taskClassRoot.getId());
		i1.setTarget(taskClassEinsatz.getId());
		this.relationships.add(i1);
		Inheritance i2 = new Inheritance();
		i2.setId(new ObjectId().toHexString());
		i2.setSource(taskClassRoot.getId());
		i2.setTarget(taskClassAusfahrt.getId());
		this.relationships.add(i2);
		Inheritance i3 = new Inheritance();
		i3.setId(new ObjectId().toHexString());
		i3.setSource(taskClassRoot.getId());
		i3.setTarget(taskClassDienst.getId());
		this.relationships.add(i3);

//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
	}

	public void createClassRolesRK(String tenantId) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
		
		ClassDefinition functionClassRoot = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Funktion")).findFirst().get();
		
		// Function
		// AK: hier ist alles korrekt definiert :)
		ClassDefinition functionClassAmbulanceService = obtainClass(tenantId, "Funktion durch Ausbildung", functionClassRoot);
		PropertyDefinition<Object> pdDescription = obtainProperty("Description", PropertyType.TEXT, tenantId);
		functionClassAmbulanceService.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(pdDescription));
		this.classDefinitions.add(functionClassAmbulanceService);
		//
		ClassDefinition functionClassRettungssanitäter = obtainClass(tenantId, "Rettungssanitäter", functionClassAmbulanceService);
		this.classDefinitions.add(functionClassRettungssanitäter);
		//
		ClassDefinition functionClassNotfallsanitäter = obtainClass(tenantId, "Notfallsanitäter", functionClassAmbulanceService);
		this.classDefinitions.add(functionClassNotfallsanitäter);

		Inheritance i1 = new Inheritance();
		i1.setId(new ObjectId().toHexString());
		i1.setSource(functionClassRoot.getId());
		i1.setTarget(functionClassAmbulanceService.getId());
		this.relationships.add(i1);
		Inheritance i2 = new Inheritance();
		i2.setId(new ObjectId().toHexString());
		//AK: i2 statt i1: i1.setSource(functionClassAmbulanceService.getId());
		i2.setSource(functionClassAmbulanceService.getId());
		i2.setTarget(functionClassRettungssanitäter.getId());
		this.relationships.add(i2);
		Inheritance i3 = new Inheritance();
		i3.setId(new ObjectId().toHexString());
		i3.setSource(functionClassAmbulanceService.getId());
		i3.setTarget(functionClassNotfallsanitäter.getId());
		this.relationships.add(i3);

//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
	}

	public void createClassVerdiensteRK(String tenantId) {
//		ArrayList<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
//		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
//		String tenantId = classConfig.getTenantId();
		
		ClassDefinition certClassRoot = this.classDefinitions.stream().filter(cd -> cd.getName().equals("Verdienst")).findFirst().get();
		// Fahrtenspange
		ClassDefinition certClassFahrtenspangeBronze = obtainClass(tenantId, "Fahrtenspange Bronze", certClassRoot);
		ClassProperty<Object> cp = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("Description", PropertyType.LONG_TEXT, tenantId));	
		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 1000 Fahrten mit dem RK"));
		
		ClassProperty<Object> cpIssuedOn = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("issuedOn", PropertyType.DATE, tenantId));	
		certClassFahrtenspangeBronze.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassFahrtenspangeBronze.getProperties().add(cp);
		certClassFahrtenspangeBronze.getProperties().add(cpIssuedOn);
		
		this.classDefinitions.add(certClassFahrtenspangeBronze);
		//
		ClassDefinition certClassFahrtenspangeSilber = obtainClass(tenantId, "Fahrtenspange Silber", certClassRoot);
		cp = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("Description", PropertyType.LONG_TEXT, tenantId));	
		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 2500 Fahrten mit dem RK"));
		
		cpIssuedOn = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("issuedOn", PropertyType.DATE, tenantId));	

		certClassFahrtenspangeSilber.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassFahrtenspangeSilber.getProperties().add(cp);
		certClassFahrtenspangeSilber.getProperties().add(cpIssuedOn);
		
		this.classDefinitions.add(certClassFahrtenspangeSilber);
		//
		ClassDefinition certClassFahrtenspangeGold = obtainClass(tenantId, "Fahrtenspange Gold", certClassRoot);
		cp = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("Description", PropertyType.LONG_TEXT, tenantId));	
		
		cp.setDefaultValues(Arrays.asList("Fahrtenspange für über 5000 Fahrten mit dem RK"));

		cpIssuedOn = propertyDefinitionToClassPropertyMapper.toTarget(obtainProperty("issuedOn", PropertyType.DATE, tenantId));	

		certClassFahrtenspangeGold.setProperties(new ArrayList<ClassProperty<Object>>());
		certClassFahrtenspangeGold.getProperties().add(cp);
		certClassFahrtenspangeGold.getProperties().add(cpIssuedOn);

		this.classDefinitions.add(certClassFahrtenspangeGold);

		Inheritance i1 = new Inheritance();
		i1.setId(new ObjectId().toHexString());
		i1.setSource(certClassRoot.getId());
		i1.setTarget(certClassFahrtenspangeBronze.getId());
		this.relationships.add(i1);
		Inheritance i2 = new Inheritance();
		i2.setId(new ObjectId().toHexString());
		i2.setSource(certClassRoot.getId());
		i2.setTarget(certClassFahrtenspangeSilber.getId());
		this.relationships.add(i2);
		Inheritance i3 = new Inheritance();
		i3.setId(new ObjectId().toHexString());
		i3.setSource(certClassRoot.getId());
		i3.setTarget(certClassFahrtenspangeGold.getId());
		this.relationships.add(i3);

//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(classConfig.getId());
//			classDefinitionRepository.save(cd);
//			classConfig.getClassDefinitionIds().add(cd.getId());
//		}
//		for (Relationship r : relationships) {
//			relationshipRepository.save(r);
//			classConfig.getRelationshipIds().add(r.getId());
//		}
	}

}

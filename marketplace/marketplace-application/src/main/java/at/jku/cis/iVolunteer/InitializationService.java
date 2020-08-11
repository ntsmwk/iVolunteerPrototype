package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingCollectorConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataClasses;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataInstances;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Service
public class InitializationService {

	@Autowired protected ClassDefinitionRepository classDefinitionRepository;
	@Autowired protected RelationshipRepository relationshipRepository;
	@Autowired protected FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired protected ClassConfigurationRepository classConfigurationRepository;
	@Autowired protected MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired protected MatchingCollectorConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;

	@Autowired private CoreTenantRestClient coreTenantRestClient;

	@Autowired public StandardPropertyDefinitions standardPropertyDefinitions;

	@Autowired private ClassConfigurationController classConfigurationController;

	@Autowired protected TestDataClasses testDataClasses;
	@Autowired protected TestDataInstances testDataInstances;

//	private static final String FFEIDENBERG = "FF Eidenberg";
//	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
//	private static final String RKWILHERING = "RK Wilhering";

	@PostConstruct
	public void init() {
		// finalizationService.destroy(configuratorRepository,
		// classDefinitionRepository, classInstanceRepository,
		// relationshipRepository, propertyDefinitionRepository,
		// derivationRuleRepository);

		// if(environment.acceptsProfiles("dev")) {}

//		addiVolunteerAPIClassDefinition();
		// addTestConfigClasses();
		// addConfigurators();
//		addConfiguratorSlots();
		// this.addTestClassInstances();

		// addTestClassInstances();
		// addTestConfigClasses();
		// addConfigurators();

		// addConfiguratorSlots();
//		addiVolunteerPropertyDefinitions();
//		addClassConfigurations();
//		
//		apiInitializationService.addiVolunteerAPIClassDefinition();
//		// testDataClasses.createClassConfigurations();
//		testDataInstances.createUserData();
		// addTestClassInstances();
	}

	private List<Tenant> getTenants() {
		List<Tenant> tenants = new ArrayList<>();
		tenants = coreTenantRestClient.getAllTenants();

		return tenants;
	}

	public void addiVolunteerPropertyDefinitions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAlliVolunteer(tenant.getId())) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addFlexProdPropertyDefinitions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllFlexProdProperties(tenant.getId())) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addGenericPropertyDefintions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric(tenant.getId())) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addHeaderPropertyDefintions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader(tenant.getId())) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addClassConfigurations() {
		List<Tenant> tenants = getTenants();

		for (Tenant t : tenants) {
			for (int i = 1; i <= 5; i++) {
				this.classConfigurationController
						.createNewClassConfiguration(new String[] { t.getId(), "slot" + i, "" });
			}
		}
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void createClassConfiguration(String tenantId, String slotName) {
//
//		List<ClassDefinition> classDefinitions = new ArrayList<>();
//		List<Relationship> relationships = new ArrayList<>();
//
//		List<PropertyDefinition<Object>> properties = this.propertyDefinitionRepository.findByTenantId(tenantId);
//
//		ClassDefinition fwPassEintrag = new ClassDefinition();
//		fwPassEintrag.setId(new ObjectId().toHexString());
//		fwPassEintrag.setTenantId(tenantId);
//		fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
//		fwPassEintrag.setRoot(true);
//		fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
//		fwPassEintrag.setWriteProtected(true);
//		fwPassEintrag.setCollector(true);
//		fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
//
//		PropertyDefinition idProperty = properties.stream().filter(p -> p.getName().equals("id")).findFirst().get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(idProperty));
//
//		PropertyDefinition nameProperty = properties.stream().filter(p -> p.getName().equals("name")).findFirst().get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(nameProperty));
//
//		PropertyDefinition evidenzProperty = properties.stream().filter(p -> p.getName().equals("evidenz")).findFirst()
//				.get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(evidenzProperty));
//
//		classDefinitions.add(fwPassEintrag);
//
//		ClassDefinition task = new ClassDefinition();
//		task.setId(new ObjectId().toHexString());
//		task.setTenantId(tenantId);
//		task.setName("Tätigkeit");
//		task.setClassArchetype(ClassArchetype.TASK);
//		task.setWriteProtected(true);
//		task.setProperties(new ArrayList<>());
//
//		PropertyDefinition dateFromProperty = properties.stream().filter(p -> p.getName().equals("Starting Date"))
//				.findFirst().get();
//		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateFromProperty));
//
//		PropertyDefinition dateToProperty = properties.stream().filter(p -> p.getName().equals("End Date")).findFirst()
//				.get();
//		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateToProperty));
//
//		classDefinitions.add(task);
//
//		Inheritance r1 = new Inheritance();
//		r1.setRelationshipType(RelationshipType.INHERITANCE);
//		r1.setTarget(task.getId());
//		r1.setSource(fwPassEintrag.getId());
//
//		relationships.add(r1);
//
//		ClassDefinition competence = new ClassDefinition();
//		competence.setId(new ObjectId().toHexString());
//		competence.setTenantId(tenantId);
//		competence.setName("Kompetenz");
//		competence.setClassArchetype(ClassArchetype.COMPETENCE);
//		competence.setWriteProtected(true);
//		competence.setProperties(new ArrayList<>());
//
//		classDefinitions.add(competence);
//
//		Inheritance r2 = new Inheritance();
//		r2.setRelationshipType(RelationshipType.INHERITANCE);
//		r2.setTarget(competence.getId());
//		r2.setSource(fwPassEintrag.getId());
//
//		relationships.add(r2);
//
//		ClassDefinition achievement = new ClassDefinition();
//		achievement.setId(new ObjectId().toHexString());
//		achievement.setTenantId(tenantId);
//		achievement.setName("Verdienst");
//		achievement.setClassArchetype(ClassArchetype.ACHIEVEMENT);
//		achievement.setWriteProtected(true);
//		achievement.setProperties(new ArrayList<>());
//
//		classDefinitions.add(achievement);
//
//		Inheritance r3 = new Inheritance();
//		r3.setRelationshipType(RelationshipType.INHERITANCE);
//		r3.setTarget(achievement.getId());
//		r3.setSource(fwPassEintrag.getId());
//
//		relationships.add(r3);
//
//		ClassDefinition function = new ClassDefinition();
//		function.setId(new ObjectId().toHexString());
//		function.setTenantId(tenantId);
//		function.setName("Funktion");
//		function.setClassArchetype(ClassArchetype.FUNCTION);
//		function.setWriteProtected(true);
//		function.setProperties(new ArrayList<>());
//
//		classDefinitions.add(function);
//
//		Inheritance r4 = new Inheritance();
//		r4.setRelationshipType(RelationshipType.INHERITANCE);
//		r4.setTarget(function.getId());
//		r4.setSource(fwPassEintrag.getId());
//
//		relationships.add(r4);
//
//		///////////////// Philipp Zeug//////////////////////////
//		ClassDefinition myTask = new ClassDefinition();
//		myTask.setId(new ObjectId().toHexString());
//		myTask.setTenantId(tenantId);
//		myTask.setName("myTask");
//		myTask.setClassArchetype(ClassArchetype.TASK);
//		myTask.setProperties(new ArrayList<>());
//
//		PropertyDefinition tt1 = properties.stream().filter(p -> p.getName().equals("taskType1")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt1));
//
//		PropertyDefinition tt2 = properties.stream().filter(p -> p.getName().equals("taskType2")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt2));
//
//		PropertyDefinition tt3 = properties.stream().filter(p -> p.getName().equals("taskType3")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt3));
//
//		PropertyDefinition location = properties.stream().filter(p -> p.getName().equals("Location")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(location));
//
//		PropertyDefinition rank = properties.stream().filter(p -> p.getName().equals("rank")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(rank));
//
//		PropertyDefinition duration = properties.stream().filter(p -> p.getName().equals("duration")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(duration));
//
//		classDefinitions.add(myTask);
//
//		Inheritance r5 = new Inheritance();
//		r5.setRelationshipType(RelationshipType.INHERITANCE);
//		r5.setTarget(myTask.getId());
//		r5.setSource(task.getId());
//
//		relationships.add(r5);
//
//		ClassConfiguration configurator = new ClassConfiguration();
//		configurator.setTimestamp(new Date());
//		configurator.setId(slotName);
//		configurator.setName(slotName);
//		configurator.setClassDefinitionIds(new ArrayList<>());
//		configurator.setRelationshipIds(new ArrayList<>());
//
//		for (ClassDefinition cd : classDefinitions) {
//			cd.setConfigurationId(configurator.getId());
//			this.classDefinitionRepository.save(cd);
//			configurator.getClassDefinitionIds().add(cd.getId());
//		}
//
//		for (Relationship r : relationships) {
//			this.relationshipRepository.save(r);
//			configurator.getRelationshipIds().add(r.getId());
//		}
//
//		this.classConfigurationController.saveClassConfiguration(configurator);
//	}

	public void addRuleTestConfiguration() {
		testDataClasses.createClassConfigurations();
	}

	public void addRuleTestUserData() {
		testDataInstances.createUserData();
	}

	public void deleteClassDefinitions() {
		classDefinitionRepository.deleteAll();
	}

	public void deleteRelationships() {
		relationshipRepository.deleteAll();
	}

	public void deleteClassConfigurations() {
		classConfigurationRepository.deleteAll();
	}

	public void deleteMatchingConfigurations() {
		matchingConfigurationRepository.deleteAll();
	}

	/*
	 * private void addTestRuleEngine() { /****** load rules into database
	 ******/
	// testRuleEngine.initTestData(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
	// testRuleEngine.initTestData(coreTenantRestClient.getTenantIdByName(MUSIKVEREINSCHWERTBERG));
	// testRuleEngine.initTestData(coreTenantRestClient.getTenantIdByName(RKWILHERING));

	// build containers for rule sets
	// ruleService.refreshContainer(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
	// ruleService.refreshContainer(coreTenantRestClient.getTenantIdByName(MUSIKVEREINSCHWERTBERG));
	// ruleService.refreshContainer(coreTenantRestClient.getTenantIdByName(RKWILHERING));

	// ruleService.printContainers();
	// ruleService.executeRules(coreTenantRestClient.getTenantIdByName(FFEIDENBERG),
	// "general", "5e9848778cbb21753070dacc");
	// ruleService.executeRules(coreTenantRestClient.getTenantIdByName(FFEIDENBERG),
	// "ivol-test", "5e9848778cbb21753070dacc");

	// }

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// private void addTestConfigClasses() {
	// // TODO Philipp testConfig for tenant=FFEIDENBERG only
	// String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
	//
	// HelpSeeker ffa = helpSeekerRepository.findByUsername("FFA");
	// if (ffa != null) {
	// CompetenceClassDefinition c1 = new CompetenceClassDefinition();
	// c1.setId("test1");
	// c1.setName("Class 1");
	// c1.setProperties(new ArrayList<ClassProperty<Object>>());
	// c1.setRoot(true);
	// c1.setTenantId(ffa.getTenantId());
	//
	// PropertyDefinition npd = new
	// StandardPropertyDefinitions.NameProperty(tenantId);
	// ClassProperty<Object> ncp =
	// propertyDefinitionToClassPropertyMapper.toTarget(npd);
	// c1.getProperties().add(ncp);
	//
	// PropertyDefinition sdpd = new
	// StandardPropertyDefinitions.StartDateProperty(tenantId);
	// ClassProperty<Object> sdcp =
	// propertyDefinitionToClassPropertyMapper.toTarget(sdpd);
	// c1.getProperties().add(sdcp);
	//
	// PropertyDefinition dpd = new
	// StandardPropertyDefinitions.DescriptionProperty(tenantId);
	// ClassProperty<Object> dcp =
	// propertyDefinitionToClassPropertyMapper.toTarget(dpd);
	// c1.getProperties().add(dcp);
	//
	// c1.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c2 = new CompetenceClassDefinition();
	// c2.setId("test2");
	// c2.setName("Class 2");
	// c2.setTenantId(ffa.getTenantId());
	//
	// c2.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c3 = new CompetenceClassDefinition();
	// c3.setId("test3");
	// c3.setName("Class 3");
	// c3.setTenantId(ffa.getTenantId());
	//
	// c3.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c4 = new CompetenceClassDefinition();
	// c4.setId("test4");
	// c4.setName("Class 4");
	// c4.setTenantId(ffa.getTenantId());
	//
	// c4.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c5 = new CompetenceClassDefinition();
	// c5.setId("test5");
	// c5.setName("Class 5");
	// c5.setTenantId(ffa.getTenantId());
	//
	// c5.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c6 = new CompetenceClassDefinition();
	// c6.setId("test6");
	// c6.setName("Class 6");
	// c6.setTenantId(ffa.getTenantId());
	//
	// c6.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c7 = new CompetenceClassDefinition();
	// c7.setId("test7");
	// c7.setName("Class 7");
	// c7.setTenantId(ffa.getTenantId());
	//
	// c7.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c8 = new CompetenceClassDefinition();
	// c8.setId("test8");
	// c8.setName("Class 8");
	// c8.setTenantId(ffa.getTenantId());
	//
	// c8.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c9 = new CompetenceClassDefinition();
	// c9.setId("test9");
	// c9.setName("Class 9");
	// c9.setTenantId(ffa.getTenantId());
	//
	// c9.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	//// {from: 1, to: 3},
	//// {from: 1, to: 2},
	//// {from: 2, to: 4},
	//// {from: 2, to: 5},
	//// {from: 3, to: 3},
	//// {from: 6, to: 6},
	//
	// Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
	// i1.setId("test_i1");
	//
	// Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
	// i2.setId("test_i2");
	// Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
	// i3.setId("test_i3");
	// Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
	// i4.setId("test_i4");
	//// Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
	//// i5.setId("test_i5");
	//// Inheritance i6 = new Inheritance(c6.getId(), c6.getId(), c6.getId());
	//// i6.setId("test_i6");
	//
	// Inheritance i7 = new Inheritance(c5.getId(), c7.getId(), c5.getId());
	// i7.setId("test_i7");
	// Inheritance i8 = new Inheritance(c5.getId(), c8.getId(), c5.getId());
	// i8.setId("test_i8");
	// Inheritance i9 = new Inheritance(c4.getId(), c9.getId(), c4.getId());
	// i9.setId("test_i9");
	//
	// if (!relationshipRepository.exists(i1.getId())) {
	// relationshipRepository.save(i1);
	// }
	// if (!relationshipRepository.exists(i2.getId())) {
	// relationshipRepository.save(i2);
	// }
	// if (!relationshipRepository.exists(i3.getId())) {
	// relationshipRepository.save(i3);
	// }
	// if (!relationshipRepository.exists(i4.getId())) {
	// relationshipRepository.save(i4);
	// }
	//// if (!relationshipRepository.exists(i5.getId())) {
	//// relationshipRepository.save(i5);
	//// }
	//
	// if (!relationshipRepository.exists(i7.getId())) {
	// relationshipRepository.save(i7);
	// }
	// if (!relationshipRepository.exists(i8.getId())) {
	// relationshipRepository.save(i8);
	// }
	// if (!relationshipRepository.exists(i9.getId())) {
	// relationshipRepository.save(i9);
	// }
	//
	// if (!classDefinitionRepository.exists(c1.getId())) {
	// classDefinitionRepository.save(c1);
	// }
	//
	// if (!classDefinitionRepository.exists(c2.getId())) {
	// classDefinitionRepository.save(c2);
	// }
	//
	// if (!classDefinitionRepository.exists(c3.getId())) {
	// classDefinitionRepository.save(c3);
	// }
	//
	// if (!classDefinitionRepository.exists(c4.getId())) {
	// classDefinitionRepository.save(c4);
	// }
	//
	// if (!classDefinitionRepository.exists(c5.getId())) {
	// classDefinitionRepository.save(c5);
	// }
	//
	// if (!classDefinitionRepository.exists(c6.getId())) {
	// classDefinitionRepository.save(c6);
	// }
	//
	// if (!classDefinitionRepository.exists(c7.getId())) {
	// classDefinitionRepository.save(c7);
	// }
	//
	// if (!classDefinitionRepository.exists(c8.getId())) {
	// classDefinitionRepository.save(c8);
	// }
	//
	// if (!classDefinitionRepository.exists(c9.getId())) {
	// classDefinitionRepository.save(c9);
	// }
	// }
	// }
	//
	// private void addConfigurators() {
	// Configurator c1 = new Configurator();
	// c1.setName("Slot1");
	// c1.setId("slot1");
	// c1.setDate(new Date());
	//
	// c1.setRelationshipIds(new ArrayList<>());
	// c1.getRelationshipIds().add("test_i1");
	// c1.getRelationshipIds().add("test_i2");
	// c1.getRelationshipIds().add("test_i3");
	// c1.getRelationshipIds().add("test_i4");
	//// c1.getRelationshipIds().add("test_i5");
	//// c1.getRelationshipIds().add("test_i6");
	// c1.getRelationshipIds().add("test_i7");
	// c1.getRelationshipIds().add("test_i8");
	// c1.getRelationshipIds().add("test_i9");
	//
	// c1.setClassDefinitionIds(new ArrayList<>());
	// c1.getClassDefinitionIds().add("test1");
	// c1.getClassDefinitionIds().add("test2");
	// c1.getClassDefinitionIds().add("test3");
	// c1.getClassDefinitionIds().add("test4");
	// c1.getClassDefinitionIds().add("test5");
	// c1.getClassDefinitionIds().add("test6");
	// c1.getClassDefinitionIds().add("test7");
	// c1.getClassDefinitionIds().add("test8");
	// c1.getClassDefinitionIds().add("test9");
	//
	// Configurator c2 = new Configurator();
	// c2.setName("Feuerwehr Konfiguration");
	// c2.setId("slot2");
	// c2.setDate(new Date());
	// c2.setUserId("FFA");
	//
	// Configurator c3 = new Configurator();
	// c3.setName("Musiverein Modell");
	// c3.setId("slot3");
	//
	// c3.setDate(new Date());
	// c3.setUserId("MVS");
	//
	// Configurator c4 = new Configurator();
	// c4.setName("Slot4");
	// c4.setId("slot4");
	//
	// c4.setDate(new Date());
	//
	// Configurator c5 = new Configurator();
	// c5.setName("Slot5");
	// c5.setId("slot5");
	// c5.setDate(new Date());
	//
	// if (!configuratorRepository.exists(c1.getId())) {
	// configuratorRepository.save(c1);
	// }
	//
	// if (!configuratorRepository.exists(c2.getId())) {
	// configuratorRepository.save(c2);
	// }
	//
	// if (!configuratorRepository.exists(c3.getId())) {
	// configuratorRepository.save(c3);
	// }
	//
	// if (!configuratorRepository.exists(c3.getId())) {
	// configuratorRepository.save(c3);
	// }
	//
	// if (!configuratorRepository.exists(c4.getId())) {
	// configuratorRepository.save(c4);
	// }
	// if (!configuratorRepository.exists(c5.getId())) {
	// configuratorRepository.save(c5);
	// }
	// }
	//
	// private void addTestClassInstances() {
	// TaskClassInstance ti1 = new TaskClassInstance();
	// ti1.setId("ti1");
	// ti1.setName("Shopping Elementaries");
	// Volunteer volunteer = volunteerRepository.findByUsername("mweissenbek");
	// HelpSeeker oerk = helpSeekerRepository.findByUsername("OERK");
	// HelpSeeker mvs = helpSeekerRepository.findByUsername("MVS");
	// HelpSeeker ffa = helpSeekerRepository.findByUsername("FFA");
	// HelpSeeker efa = helpSeekerRepository.findByUsername("EFA");
	//
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	// ti1.setInUserRepository(true);
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti2");
	// ti1.setName("Equipment Service");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (mvs != null) {
	// ti1.setIssuerId(mvs.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti3");
	// ti1.setName("Shopping Elementaries");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ti1.setIssuerId(efa.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti4");
	// ti1.setName("Shopping Elementaries");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti5");
	// ti1.setName("Donation Collection");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ti1.setIssuerId(efa.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti6");
	// ti1.setName("Medical Care Transport");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// Feedback f1 = new Feedback();
	// f1.setId("f1");
	// f1.setName("Firetruck Driver Renewed");
	// f1.setFeedbackType(FeedbackType.KUDOS);
	// if (volunteer != null) {
	// f1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// f1.setIssuerId(oerk.getId());
	// }
	// f1.setTimestamp(new Date(System.currentTimeMillis()));
	// f1.setFeedbackValue(1);
	// f1.setInUserRepository(false);
	//
	// feedbackRepository.save(f1);
	//
	// f1.setId("f2");
	// f1.setName("Yearly Feedback");
	// f1.setFeedbackType(FeedbackType.STARRATING);
	// if (volunteer != null) {
	// f1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// f1.setIssuerId(ffa.getId());
	// }
	// f1.setTimestamp(new Date(System.currentTimeMillis()));
	// f1.setFeedbackValue(5);
	//
	// feedbackRepository.save(f1);
	//
	// CompetenceClassInstance ci1 = new CompetenceClassInstance();
	// ci1.setId("ci1");
	// ci1.setName("Diligence");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (mvs != null) {
	// ci1.setIssuerId(mvs.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// ci1.setInUserRepository(true);
	//
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci2");
	// ci1.setName("Teamwork");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci3");
	// ci1.setName("Communication Skills");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ci1.setIssuerId(efa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci4");
	// ci1.setName("Project Management");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci5");
	// ci1.setName("Firetruck Driver");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// ci1.setInUserRepository(false);
	// classInstanceRepository.save(ci1);
	// }

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// private void addTestConfigClasses() {
	// testConfig for tenant=FFEIDENBERG only
	// String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
	//
	// HelpSeeker ffa = helpSeekerRepository.findByUsername("FFA");
	// if (ffa != null) {
	// CompetenceClassDefinition c1 = new CompetenceClassDefinition();
	// c1.setId("test1");
	// c1.setName("Class 1");
	// c1.setProperties(new ArrayList<ClassProperty<Object>>());
	// c1.setRoot(true);
	// c1.setTenantId(ffa.getTenantId());
	//
	// PropertyDefinition npd = new
	// StandardPropertyDefinitions.NameProperty(tenantId);
	// ClassProperty<Object> ncp =
	// propertyDefinitionToClassPropertyMapper.toTarget(npd);
	// c1.getProperties().add(ncp);
	//
	// PropertyDefinition sdpd = new
	// StandardPropertyDefinitions.StartDateProperty(tenantId);
	// ClassProperty<Object> sdcp =
	// propertyDefinitionToClassPropertyMapper.toTarget(sdpd);
	// c1.getProperties().add(sdcp);
	//
	// PropertyDefinition dpd = new
	// StandardPropertyDefinitions.DescriptionProperty(tenantId);
	// ClassProperty<Object> dcp =
	// propertyDefinitionToClassPropertyMapper.toTarget(dpd);
	// c1.getProperties().add(dcp);
	//
	// c1.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c2 = new CompetenceClassDefinition();
	// c2.setId("test2");
	// c2.setName("Class 2");
	// c2.setTenantId(ffa.getTenantId());
	//
	// c2.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c3 = new CompetenceClassDefinition();
	// c3.setId("test3");
	// c3.setName("Class 3");
	// c3.setTenantId(ffa.getTenantId());
	//
	// c3.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c4 = new CompetenceClassDefinition();
	// c4.setId("test4");
	// c4.setName("Class 4");
	// c4.setTenantId(ffa.getTenantId());
	//
	// c4.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c5 = new CompetenceClassDefinition();
	// c5.setId("test5");
	// c5.setName("Class 5");
	// c5.setTenantId(ffa.getTenantId());
	//
	// c5.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c6 = new CompetenceClassDefinition();
	// c6.setId("test6");
	// c6.setName("Class 6");
	// c6.setTenantId(ffa.getTenantId());
	//
	// c6.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c7 = new CompetenceClassDefinition();
	// c7.setId("test7");
	// c7.setName("Class 7");
	// c7.setTenantId(ffa.getTenantId());
	//
	// c7.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c8 = new CompetenceClassDefinition();
	// c8.setId("test8");
	// c8.setName("Class 8");
	// c8.setTenantId(ffa.getTenantId());
	//
	// c8.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	// CompetenceClassDefinition c9 = new CompetenceClassDefinition();
	// c9.setId("test9");
	// c9.setName("Class 9");
	// c9.setTenantId(ffa.getTenantId());
	//
	// c9.setClassArchetype(ClassArchetype.COMPETENCE);
	//
	//// {from: 1, to: 3},
	//// {from: 1, to: 2},
	//// {from: 2, to: 4},
	//// {from: 2, to: 5},
	//// {from: 3, to: 3},
	//// {from: 6, to: 6},
	//
	// Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
	// i1.setId("test_i1");
	//
	// Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
	// i2.setId("test_i2");
	// Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
	// i3.setId("test_i3");
	// Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
	// i4.setId("test_i4");
	//// Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
	//// i5.setId("test_i5");
	//// Inheritance i6 = new Inheritance(c6.getId(), c6.getId(), c6.getId());
	//// i6.setId("test_i6");
	//
	// Inheritance i7 = new Inheritance(c5.getId(), c7.getId(), c5.getId());
	// i7.setId("test_i7");
	// Inheritance i8 = new Inheritance(c5.getId(), c8.getId(), c5.getId());
	// i8.setId("test_i8");
	// Inheritance i9 = new Inheritance(c4.getId(), c9.getId(), c4.getId());
	// i9.setId("test_i9");
	//
	// if (!relationshipRepository.exists(i1.getId())) {
	// relationshipRepository.save(i1);
	// }
	// if (!relationshipRepository.exists(i2.getId())) {
	// relationshipRepository.save(i2);
	// }
	// if (!relationshipRepository.exists(i3.getId())) {
	// relationshipRepository.save(i3);
	// }
	// if (!relationshipRepository.exists(i4.getId())) {
	// relationshipRepository.save(i4);
	// }
	//// if (!relationshipRepository.exists(i5.getId())) {
	//// relationshipRepository.save(i5);
	//// }
	//
	// if (!relationshipRepository.exists(i7.getId())) {
	// relationshipRepository.save(i7);
	// }
	// if (!relationshipRepository.exists(i8.getId())) {
	// relationshipRepository.save(i8);
	// }
	// if (!relationshipRepository.exists(i9.getId())) {
	// relationshipRepository.save(i9);
	// }
	//
	// if (!classDefinitionRepository.exists(c1.getId())) {
	// classDefinitionRepository.save(c1);
	// }
	//
	// if (!classDefinitionRepository.exists(c2.getId())) {
	// classDefinitionRepository.save(c2);
	// }
	//
	// if (!classDefinitionRepository.exists(c3.getId())) {
	// classDefinitionRepository.save(c3);
	// }
	//
	// if (!classDefinitionRepository.exists(c4.getId())) {
	// classDefinitionRepository.save(c4);
	// }
	//
	// if (!classDefinitionRepository.exists(c5.getId())) {
	// classDefinitionRepository.save(c5);
	// }
	//
	// if (!classDefinitionRepository.exists(c6.getId())) {
	// classDefinitionRepository.save(c6);
	// }
	//
	// if (!classDefinitionRepository.exists(c7.getId())) {
	// classDefinitionRepository.save(c7);
	// }
	//
	// if (!classDefinitionRepository.exists(c8.getId())) {
	// classDefinitionRepository.save(c8);
	// }
	//
	// if (!classDefinitionRepository.exists(c9.getId())) {
	// classDefinitionRepository.save(c9);
	// }
	// }
	// }
	//
	// private void addConfigurators() {
	// Configurator c1 = new Configurator();
	// c1.setName("Slot1");
	// c1.setId("slot1");
	// c1.setDate(new Date());
	//
	// c1.setRelationshipIds(new ArrayList<>());
	// c1.getRelationshipIds().add("test_i1");
	// c1.getRelationshipIds().add("test_i2");
	// c1.getRelationshipIds().add("test_i3");
	// c1.getRelationshipIds().add("test_i4");
	//// c1.getRelationshipIds().add("test_i5");
	//// c1.getRelationshipIds().add("test_i6");
	// c1.getRelationshipIds().add("test_i7");
	// c1.getRelationshipIds().add("test_i8");
	// c1.getRelationshipIds().add("test_i9");
	//
	// c1.setClassDefinitionIds(new ArrayList<>());
	// c1.getClassDefinitionIds().add("test1");
	// c1.getClassDefinitionIds().add("test2");
	// c1.getClassDefinitionIds().add("test3");
	// c1.getClassDefinitionIds().add("test4");
	// c1.getClassDefinitionIds().add("test5");
	// c1.getClassDefinitionIds().add("test6");
	// c1.getClassDefinitionIds().add("test7");
	// c1.getClassDefinitionIds().add("test8");
	// c1.getClassDefinitionIds().add("test9");
	//
	// Configurator c2 = new Configurator();
	// c2.setName("Feuerwehr Konfiguration");
	// c2.setId("slot2");
	// c2.setDate(new Date());
	// c2.setUserId("FFA");
	//
	// Configurator c3 = new Configurator();
	// c3.setName("Musiverein Modell");
	// c3.setId("slot3");
	//
	// c3.setDate(new Date());
	// c3.setUserId("MVS");
	//
	// Configurator c4 = new Configurator();
	// c4.setName("Slot4");
	// c4.setId("slot4");
	//
	// c4.setDate(new Date());
	//
	// Configurator c5 = new Configurator();
	// c5.setName("Slot5");
	// c5.setId("slot5");
	// c5.setDate(new Date());
	//
	// if (!configuratorRepository.exists(c1.getId())) {
	// configuratorRepository.save(c1);
	// }
	//
	// if (!configuratorRepository.exists(c2.getId())) {
	// configuratorRepository.save(c2);
	// }
	//
	// if (!configuratorRepository.exists(c3.getId())) {
	// configuratorRepository.save(c3);
	// }
	//
	// if (!configuratorRepository.exists(c3.getId())) {
	// configuratorRepository.save(c3);
	// }
	//
	// if (!configuratorRepository.exists(c4.getId())) {
	// configuratorRepository.save(c4);
	// }
	// if (!configuratorRepository.exists(c5.getId())) {
	// configuratorRepository.save(c5);
	// }
	// }
	//
	// private void addTestClassInstances() {
	// TaskClassInstance ti1 = new TaskClassInstance();
	// ti1.setId("ti1");
	// ti1.setName("Shopping Elementaries");
	// Volunteer volunteer = volunteerRepository.findByUsername("mweissenbek");
	// HelpSeeker oerk = helpSeekerRepository.findByUsername("OERK");
	// HelpSeeker mvs = helpSeekerRepository.findByUsername("MVS");
	// HelpSeeker ffa = helpSeekerRepository.findByUsername("FFA");
	// HelpSeeker efa = helpSeekerRepository.findByUsername("EFA");
	//
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	// ti1.setInUserRepository(true);
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti2");
	// ti1.setName("Equipment Service");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (mvs != null) {
	// ti1.setIssuerId(mvs.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti3");
	// ti1.setName("Shopping Elementaries");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ti1.setIssuerId(efa.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti4");
	// ti1.setName("Shopping Elementaries");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti5");
	// ti1.setName("Donation Collection");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ti1.setIssuerId(efa.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// ti1.setId("ti6");
	// ti1.setName("Medical Care Transport");
	// if (volunteer != null) {
	// ti1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// ti1.setIssuerId(oerk.getId());
	// }
	// ti1.setTimestamp(new Date(System.currentTimeMillis()));
	//
	// classInstanceRepository.save(ti1);
	//
	// Feedback f1 = new Feedback();
	// f1.setId("f1");
	// f1.setName("Firetruck Driver Renewed");
	// f1.setFeedbackType(FeedbackType.KUDOS);
	// if (volunteer != null) {
	// f1.setUserId(volunteer.getId());
	// }
	// if (oerk != null) {
	// f1.setIssuerId(oerk.getId());
	// }
	// f1.setTimestamp(new Date(System.currentTimeMillis()));
	// f1.setFeedbackValue(1);
	// f1.setInUserRepository(false);
	//
	// feedbackRepository.save(f1);
	//
	// f1.setId("f2");
	// f1.setName("Yearly Feedback");
	// f1.setFeedbackType(FeedbackType.STARRATING);
	// if (volunteer != null) {
	// f1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// f1.setIssuerId(ffa.getId());
	// }
	// f1.setTimestamp(new Date(System.currentTimeMillis()));
	// f1.setFeedbackValue(5);
	//
	// feedbackRepository.save(f1);
	//
	// CompetenceClassInstance ci1 = new CompetenceClassInstance();
	// ci1.setId("ci1");
	// ci1.setName("Diligence");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (mvs != null) {
	// ci1.setIssuerId(mvs.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// ci1.setInUserRepository(true);
	//
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci2");
	// ci1.setName("Teamwork");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci3");
	// ci1.setName("Communication Skills");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (efa != null) {
	// ci1.setIssuerId(efa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci4");
	// ci1.setName("Project Management");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// classInstanceRepository.save(ci1);
	//
	// ci1.setId("ci5");
	// ci1.setName("Firetruck Driver");
	// if (volunteer != null) {
	// ci1.setUserId(volunteer.getId());
	// }
	// if (ffa != null) {
	// ci1.setIssuerId(ffa.getId());
	// }
	// ci1.setTimestamp(new Date(System.currentTimeMillis()));
	// ci1.setInUserRepository(false);
	// classInstanceRepository.save(ci1);
	// }
}

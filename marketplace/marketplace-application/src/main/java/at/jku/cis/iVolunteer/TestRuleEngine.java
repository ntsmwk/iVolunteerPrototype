package at.jku.cis.iVolunteer;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.EQCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.GTCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.LTCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleEngineMapper;
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
import at.jku.cis.iVolunteer.model.rule.Action.ActionType;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.test.data.TestDataClasses;
import at.jku.cis.iVolunteer.test.data.TestDataRK;
import javassist.bytecode.Descriptor.Iterator;

@Service
public class TestRuleEngine {
	
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private ContainerRuleEntryRepository containerRuleEntryRepository;
	@Autowired private RuleService ruleService;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassPropertyService classPropertyService;
	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private RuleEngineMapper ruleEngineMapper;
	
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private TestDataClasses testData;
	@Autowired private TestDataRK testDataRK;
	
	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
	private static final String RKWILHERING = "RK Wilhering";
	
	public void executeTestCases(){
		testData.createClassConfigurations();; // XXX vojino, replace with REST-API
		testDataRK.load(); // XXX vojino, replace with REST-API
		containerRuleEntryRepository.deleteAll();
		if (volunteerRepository.findByUsername("KBauer") != null) {
			// create user data
			// createUserData();
			// create test cases
			// testCaseAddDrivingLicense();
			//testCaseAddDrivingCompetenceByRules(coreTenantRestClient.getTenantIdByName(FFEIDENBERG));
			//testCaseAddDrivingCompetenceByRules(coreTenantRestClient.getTenantIdByName(RKWILHERING));
			//testCaseImproveDrivingCompetenceRKL2();
			//testCaseImproveDrivingCompetenceRKL3();
			//testCaseFahrtenspangeBronze();
			//testMapping1();
		
			/*testAddCompetenceDrivingCarNotExists();
			testAddAllCompetencesDriving();
			testAddCompetenceDrivingCar();
			testImproveDrivingSkillsLevel2();
			testImproveDrivingSkillsLevel3();
			testImproveDrivingSkillsLevel4();
			testMappingSeveralConditions();*/
			testANDCondition();
			testORCondition();
			testNOTCondition();
			testNOTSingleCondition();
			testNESTEDCondition();
			// random test cases
			/*testCheckAge();
			testCheckAgeMaturity();*/
			//testImproveDrivingSkills();
		}
	}
	
	public void testAddAllCompetencesDriving() {
		System.out.println("============== add competences for all driving licenses ================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-1"; 
	    String ruleName = "add-competence-driving-all";
	    
	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_CAR);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_TRUCK);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_BUS);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_MOTORCYCLE);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);

	    ClassCondition classCond1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCond1.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId).getId());
 	    dRule.addCondition(classCond1);
	    
 	    ClassCondition classCond2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCond2.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Truck", tenantId).getId());
	    dRule.addCondition(classCond2);
	  
	    ClassCondition classCond3 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCond3.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Bus", tenantId).getId());
	    dRule.addCondition(classCond3);
	    
	    ClassCondition classCond4 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCond4.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Motorcycle", tenantId).getId());
	    dRule.addCondition(classCond4);
	  
	    // now set the action part on the rhs
	    ClassAction classAction1 = new ClassAction(ActionType.NEW);
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
		   
	    classAction1.setClassDefinitionId(compDef.getId());
	    
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    classAction1.addAttribute(attrCondition1);
	    
	    AttributeCondition attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein B");
	    classAction1.addAttribute(attrCondition2);
	    
	    AttributeCondition attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    classAction1.addAttribute(attrCondition3);
	    dRule.addAction(classAction1);
	    
	    ClassAction classAction2 = new ClassAction(ActionType.NEW);
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Truck Driving", tenantId);
	    classAction2.setClassDefinitionId(compDef.getId());
	    
	    attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    classAction2.addAttribute(attrCondition1);
	    
	    attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein C");
	    classAction2.addAttribute(attrCondition2);
	    
	    attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    classAction2.addAttribute(attrCondition3);
	    dRule.addAction(classAction2);
	    
	    ClassAction classAction3 = new ClassAction(ActionType.NEW);
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Bus Driving", tenantId);
	    classAction3.setClassDefinitionId(compDef.getId());
	    
	    attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    classAction3.addAttribute(attrCondition1);
	    
	    attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein D");
	    classAction3.addAttribute(attrCondition2);
	    
	    attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    classAction3.addAttribute(attrCondition3);
	    dRule.addAction(classAction3);
	    
	    ClassAction classAction4 = new ClassAction(ActionType.NEW);
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Motorcycle Driving", tenantId);
	    classAction4.setClassDefinitionId(compDef.getId());
	    
	    attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    classAction4.addAttribute(attrCondition1);
	    
	    attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein A");
	    classAction4.addAttribute(attrCondition2);
	    
	    attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), 
	    		                                  "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    classAction4.addAttribute(attrCondition3);
	    dRule.addAction(classAction4);
	    
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-1.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, dRule.getName(), ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testAddCompetenceDrivingCar() {
		System.out.println("============== add competence for driving license ================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-2"; 
	    String ruleName = "add-competence-driving-car";
	    
	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_CAR);
	    
	    AchievementClassDefinition achievementDef = (AchievementClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition.setClassDefinitionId(achievementDef.getId());
	    dRule.addCondition(classCondition);  
	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.NEW);
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
		ruleAction.setClassDefinitionId(compDef.getId());
	
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    ruleAction.addAttribute(attrCondition1);
	    
	    AttributeCondition attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein B");
	    ruleAction.addAttribute(attrCondition2);
	  
	    AttributeCondition attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    ruleAction.addAttribute(attrCondition3);
	  
	    dRule.addAction(ruleAction);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-2.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, dRule.getName(), ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testAddCompetenceDrivingCarNotExists() {
		System.out.println("============== add competence for driving license if it does not exist already ================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-3"; 
	    String ruleName = "add-competence-driving-car-if-ne";
	    
	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_CAR);
	    
	    AchievementClassDefinition achievementDef = (AchievementClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition.setClassDefinitionId(achievementDef.getId());
	    dRule.addCondition(classCondition);
	    
	    ClassCondition classConditionNE = new ClassCondition(AggregationOperatorType.NOT_EXISTS);
	    classConditionNE.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId(TestDataClasses.COMPETENCE_DRIVING_CAR, tenantId).getId());
	    dRule.addCondition(classConditionNE);
	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.NEW);
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL1);
	    ruleAction.addAttribute(attrCondition1);
	    
	    AttributeCondition attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrCondition2.setValue("Führerschein B");
	    ruleAction.addAttribute(attrCondition2);
	    
	    AttributeCondition attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrCondition3.setValue(LocalDateTime.now());
	    ruleAction.addAttribute(attrCondition3);
	    
	    dRule.addAction(ruleAction);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-3.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, dRule.getName(), ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testCheckAge() {
		System.out.println("==========================  Test check age of volunteer ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-4"; 
	    String ruleName = "check-age";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, "Maturity");
	    
	    CompetenceClassDefinition taskDef = (CompetenceClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Maturity", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition.setClassDefinitionId(taskDef.getId());
	    classCondition.setValue(200);
	    dRule.addCondition(classCondition);
	    
	    // set age between 20 and 70: 20 < age < 70
	    GeneralCondition generalCondition1 = new GeneralCondition("Alter", 20, 
	    		                      ComparisonOperatorType.GT);
	    dRule.addGeneralCondition(generalCondition1);
	    
	    GeneralCondition generalCondition2 = new GeneralCondition("Alter", 70, 
                ComparisonOperatorType.LT);
	    dRule.addGeneralCondition(generalCondition2);
	    //
	    // now set the action part on the rhs
	    ClassAction classAction = new ClassAction(ActionType.NEW);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    classAction.setClassDefinitionId(cd.getId());
	   
	   // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Maturity Level", tenantId).getId());
	    attrCondition1.setValue(30);	 
	    classAction.addAttribute(attrCondition1);
	    dRule.addAction(classAction);
	  
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-4.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}


	public void testCheckAgeMaturity() {
		System.out.println("==========================  Test check age and maturity of volunteer ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-5"; 
	    String ruleName = "check-age-maturity";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	        
	    // set age between 20 and 70: 20 < age < 70
	    GeneralCondition genCond1 = new GeneralCondition("Alter", 20, ComparisonOperatorType.GT);
	    dRule.addGeneralCondition(genCond1);
	    
	    GeneralCondition genCond2 = new GeneralCondition("Alter", 70, ComparisonOperatorType.LT);
	    dRule.addGeneralCondition(genCond2);
	    
	    CompetenceClassDefinition taskDef = (CompetenceClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Maturity", tenantId);
	    
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition.setClassDefinitionId(taskDef.getId());
	    dRule.addCondition(classCondition);
	    
	    AttributeCondition attributeCondition1 = new AttributeCondition();
	    attributeCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "Maturity Level", tenantId).getId());
	    attributeCondition1.setOperatorType(ComparisonOperatorType.GT);
	    attributeCondition1.setValue(30);
	    classCondition.addAttributeCondition(attributeCondition1); 
	    dRule.addCondition(classCondition);
	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.UPDATE);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	  
	    AttributeCondition attributeCondition2 = new AttributeCondition();
	    attributeCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "Maturity Level", tenantId).getId());
	    attributeCondition2.setValue(33);
	    ruleAction.addAttribute(attributeCondition1); 
	    dRule.addAction(ruleAction);
	     	   
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-5.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    	
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}

	
	public void testImproveDrivingSkillsLevel2() {
		System.out.println("==========================  Test improve driving skills ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-6"; 
	    String ruleName = "improve-competence-driving-level2";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Ausfahrt", tenantId);
	   
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition.setClassDefinitionId(taskDef.getId());
	    classCondition.setValue(200);
	    
	    // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId).getId());
	    attrCondition1.setOperatorType(ComparisonOperatorType.EQ);
	    attrCondition1.setValue("Einsatzlenker");
	    classCondition.addAttributeCondition(attrCondition1);
	    //
	    dRule.addCondition(classCondition);
	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.UPDATE);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	   
	    // adding filters for properties
	    AttributeCondition attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrCondition2.setValue(TestDataClasses.DrivingLevel.LEVEL2);
	    ruleAction.addAttribute(attrCondition2);
	    dRule.addAction(ruleAction);

	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-6.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	
	public void testImproveDrivingSkillsLevel3() {
		System.out.println("==========================  Test improve driving skills level 3 ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-7"; 
	    String ruleName = "improve-competence-driving-level3";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_MODUL1, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_MODUL2, tenantId);
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	   
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    dRule.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    dRule.addCondition(classCondition2);
 	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.UPDATE);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    
	    // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL3);
        ruleAction.addAttribute(attrCondition1);
        
        dRule.addAction(ruleAction);
        // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-7.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}

	public void testANDCondition() {
		System.out.println("==========================  Test AND Condition ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-9"; 
	    String ruleName = "test-AND-condition";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_WORKSHOP, tenantId);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.TASK_RK_AUSFAHRT, tenantId);
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    MultipleConditions multiCondition = new MultipleConditions(LogicalOperatorType.AND);
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    multiCondition.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    multiCondition.addCondition(classCondition2);
 	   
	    ClassCondition classCondition3 = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition3.setClassDefinitionId(taskDef.getId());
	    classCondition3.setValue(1000);
	    multiCondition.addCondition(classCondition3);
	    dRule.addCondition(multiCondition);
 	   
	    //
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-10.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}

	public void testORCondition() {
		System.out.println("==========================  Test OR Condition ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-10"; 
	    String ruleName = "test-OR-condition";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_WORKSHOP, tenantId);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.TASK_RK_AUSFAHRT, tenantId);
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    MultipleConditions multiCondition = new MultipleConditions(LogicalOperatorType.OR);
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    multiCondition.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    multiCondition.addCondition(classCondition2);
 	   
	    ClassCondition classCondition3 = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition3.setClassDefinitionId(taskDef.getId());
	    classCondition3.setValue(100);
	    multiCondition.addCondition(classCondition3);
	    dRule.addCondition(multiCondition);
 	   
	    //
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-11.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testNOTCondition() {
		System.out.println("==========================  Test NOT Condition ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-11"; 
	    String ruleName = "test-NOT-condition";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_WORKSHOP, tenantId);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.TASK_RK_AUSFAHRT, tenantId);
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    MultipleConditions multiConditionNOT = new MultipleConditions(LogicalOperatorType.NOT);
	    MultipleConditions multiConditionAND = new MultipleConditions(LogicalOperatorType.AND);
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    multiConditionAND.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    multiConditionAND.addCondition(classCondition2);
 	   
	    ClassCondition classCondition3 = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition3.setClassDefinitionId(taskDef.getId());
	    classCondition3.setValue(1000);
	    multiConditionAND.addCondition(classCondition3);
	    multiConditionNOT.addCondition(multiConditionAND);
	    dRule.addCondition(multiConditionNOT);
 	   
	    //
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-12.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testNOTSingleCondition() {
		System.out.println("==========================  Test NOT on Single Condition ==========================================");
		// Feuerwehr
    	String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
    	Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
    	String containerName = "ivol-test-12"; 
    	String ruleName = "test-NOT-single-condition";

    	ruleService.deleteRule(tenantId, containerName, ruleName);
    
	    deleteInstances(volunteer, tenantId, "Maturity");
	    
	    CompetenceClassDefinition taskDef = (CompetenceClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Maturity", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    MultipleConditions multipleConditions = new MultipleConditions(LogicalOperatorType.NOT);
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition.setClassDefinitionId(taskDef.getId());
	    classCondition.setValue(200);
	    multipleConditions.addCondition(classCondition);
	    dRule.addCondition(multipleConditions);
	    
	    // set age between 20 and 70: 20 < age < 70
	    GeneralCondition generalCondition1 = new GeneralCondition("Alter", 20, 
	    		                      ComparisonOperatorType.GT);
	    dRule.addGeneralCondition(generalCondition1);
	    
	    GeneralCondition generalCondition2 = new GeneralCondition("Alter", 70, 
                ComparisonOperatorType.LT);
	    dRule.addGeneralCondition(generalCondition2);
	    //
	    // now set the action part on the rhs
	    ClassAction classAction = new ClassAction(ActionType.NEW);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    classAction.setClassDefinitionId(cd.getId());
	   
	   // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Maturity Level", tenantId).getId());
	    attrCondition1.setValue(30);	 
	    classAction.addAttribute(attrCondition1);
	    dRule.addAction(classAction);
	  
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-13.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testNESTEDCondition() {
		System.out.println("==========================  Test NESTED Condition ==========================================");
		System.out.println("                    (cond1 AND cond2) OR (cond3 AND cond4 AND cond5)                                    ");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-14"; 
	    String ruleName = "test-NESTED-condition";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_WORKSHOP, tenantId);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.TASK_RK_AUSFAHRT, tenantId);
	    
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Maturity", tenantId);
	    AchievementClassDefinition certSEF1 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_MODUL1, tenantId);
		    AchievementClassDefinition certSEF2 = (AchievementClassDefinition) classDefinitionRepository.
		 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_MODUL2, tenantId);
		    
		ClassCondition classConditionA = new ClassCondition(AggregationOperatorType.EXISTS);
		classConditionA.setClassDefinitionId(certSEF1.getId());
		
	    ClassCondition classConditionB = new ClassCondition(AggregationOperatorType.EXISTS);
	    classConditionB.setClassDefinitionId(compDef.getId());
	    
	    AttributeCondition attributeCondition1 = new AttributeCondition();
	    attributeCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Maturity Level", tenantId).getId());
	    attributeCondition1.setOperatorType(ComparisonOperatorType.GT);
	    attributeCondition1.setValue(30);
	    classConditionB.addAttributeCondition(attributeCondition1); 
	    
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    MultipleConditions multiConditionOR = new MultipleConditions(LogicalOperatorType.OR);
	    MultipleConditions multiConditionAND1 = new MultipleConditions(LogicalOperatorType.AND);
	    MultipleConditions multiConditionAND2 = new MultipleConditions(LogicalOperatorType.AND);
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    multiConditionAND2.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    multiConditionAND2.addCondition(classCondition2);
 	   
	    ClassCondition classCondition3 = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition3.setClassDefinitionId(taskDef.getId());
	    classCondition3.setValue(1000);
	    multiConditionAND2.addCondition(classCondition3);
	    
	    multiConditionAND1.addCondition(classConditionA);
	    multiConditionAND1.addCondition(classConditionB);
	    multiConditionOR.addCondition(multiConditionAND1);
	    multiConditionOR.addCondition(multiConditionAND2);
	    dRule.addCondition(multiConditionOR);
 	   
	    //
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-15.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}


	public void testMappingSeveralConditions() {
		System.out.println("==========================  Test Mapping several conditions =========================================");

		String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "test-mapping-1"; 
	    String ruleName = "test-mapping-several-conditions";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Ausfahrt", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	   
	    // set age between 20 and 70: 20 < age < 70
	    GeneralCondition genCond1 = new GeneralCondition("Alter", 20, ComparisonOperatorType.GT);
	    dRule.addGeneralCondition(genCond1);
	    GeneralCondition genCond2 = new GeneralCondition("Alter", 70, ComparisonOperatorType.LT);
	    dRule.addGeneralCondition(genCond2);
	    
	    ClassCondition classCondition = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition.setClassDefinitionId(taskDef.getId());
	    classCondition.setValue(99);
	    // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId).getId());
	    attrCondition1.setOperatorType(ComparisonOperatorType.EQ);
	    attrCondition1.setValue("Einsatzlenker");
	    classCondition.addAttributeCondition(attrCondition1);
	    
	    AttributeCondition attrCondition2 = new AttributeCondition();
	    attrCondition2.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId).getId());
	    attrCondition2.setOperatorType(ComparisonOperatorType.EQ);
	    attrCondition2.setValue("Wels");
	    classCondition.addAttributeCondition(attrCondition2);

	    AttributeCondition attrCondition3 = new AttributeCondition();
	    attrCondition3.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId).getId());
	    attrCondition3.setOperatorType(ComparisonOperatorType.GT);
	    attrCondition3.setValue("2018-08-04T08:00:00");
	    classCondition.addAttributeCondition(attrCondition3);
	    
	    dRule.addCondition(classCondition);
	    
	    //
	    // now set the action part on the rhs
	    
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-9.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}

	public void testImproveDrivingSkillsLevel4() {
		System.out.println("==========================  Test improve driving skills level 4 ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-8"; 
	    String ruleName = "improve-competence-driving-level4";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    
	    AchievementClassDefinition certDef1 = (AchievementClassDefinition) classDefinitionRepository.
 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_THEORIE_TRAINERAUSBILDUNG, tenantId);
	    AchievementClassDefinition certDef2 = (AchievementClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.CERTIFICATE_SEF_WORKSHOP, tenantId);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.
	 		       findByNameAndTenantId(TestDataRK.TASK_RK_AUSFAHRT, tenantId);
	    
	    if (classInstanceService.getClassInstance(volunteer, certDef1.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef1.getId(), tenantId);
	    if (classInstanceService.getClassInstance(volunteer, certDef2.getId(), tenantId) == null)
	    	classInstanceService.newClassInstance(volunteer, certDef2.getId(), tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassCondition classCondition1 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition1.setClassDefinitionId(certDef1.getId());
	    dRule.addCondition(classCondition1);
	    
	    ClassCondition classCondition2 = new ClassCondition(AggregationOperatorType.EXISTS);
	    classCondition2.setClassDefinitionId(certDef2.getId());
	    dRule.addCondition(classCondition2);
 	   
	    ClassCondition classCondition3 = new ClassCondition(AggregationOperatorType.MIN);
	    classCondition3.setClassDefinitionId(taskDef.getId());
	    classCondition3.setValue(1000);
	    dRule.addCondition(classCondition3);
 	   
	    //
	    // now set the action part on the rhs
	    ClassAction ruleAction = new ClassAction(ActionType.UPDATE);
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    // adding filters for properties
	    AttributeCondition attrCondition1 = new AttributeCondition();
	    attrCondition1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrCondition1.setValue(TestDataClasses.DrivingLevel.LEVEL4);
	    ruleAction.addAttribute(attrCondition1);
	    
	    dRule.addAction(ruleAction);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-8.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, containerName, ruleName, ruleContent);
	    
	    System.out.println("===================================================================================");
	}

	
	    
	
	/*
	public void testMapping1() {
		System.out.println("==========================  Test Mapping ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
				//
	    executeRule(volunteer, tenantId, "ivol-test", "test-mapper", ruleMapper);
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Einsatz", tenantId);
	    TaskClassInstance task = new TaskClassInstance();
	    task.setClassDefinitionId(taskDef.getId());
	    List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propList = taskDef.getProperties();
		propInstList = propList.stream().
		/*		filter(p -> /*p.getName().equals("role") || p.getName().contentEquals("Ort")).
				map(p -> classPropertyToPropertyInstanceMapper.toTarget(p)).
				collect(Collectors.toList());
		task.setProperties(propInstList);
	   //  task.getProperty("role").setValues(Arrays.asList("Einsatzlenker"));
		// task.getProperty("role").setValues(Arrays.asList("Sanitäter"));
		task.getProperty("Ort").setValues(Arrays.asList("Linz"));
	   // System.out.println("role: " + task.getProperty("role").getValues());
	    System.out.println("Ort: " + task.getProperty("Ort").getValues());
	    
	    final ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("properties", match -> match.transform(source -> ((List<PropertyInstance<Object>>) source).iterator().next()).caseSensitive());

        //users = userRepository.findAll(Example.of(task, matcher), pageRequest);
       
        List<TaskClassInstance> tasks = classInstanceRepository.findAll(Example.of(task, matcher));
	   //  List<TaskClassInstance> tasks = classInstanceRepository.findAll(Example.of(task, ExampleMatcher.matchingAll()));
		System.out.println("Ausfahrten gefunden: " + tasks.size());
	    System.out.println("===================================================================================");
	}
	/** 
	 * Car Driving: Level 1 --> Level 2
	 
	public void testCaseImproveDrivingCompetenceRKL2() {
		System.out.println("==============================================================================================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteer = volunteerRepository.findByUsername("KBauer");
		//
		executeRule(volunteer, tenantId, "ivol-test", "competence-driving", ruleCompetenceDriving);
		// reset assets
		CompetenceClassInstance ci = (CompetenceClassInstance) classInstanceService.getClassInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR).get(0);
		// cleanUp(); XXX
	    classInstanceService.setProperty(ci, "Driving Level", TestData.DrivingLevel.LEVEL1.getName());
	    deleteInstances(volunteer, tenantId, CERTIFICATE_SEF_MODUL1);
	    deleteInstances(volunteer, tenantId, CERTIFICATE_SEF_MODUL2);
	    
		cleanUpContainer(tenantId, "ivol-test");
		// 
		assertFalse("Kompetenz noch < Level 2 " + RKWILHERING + ": "+ TestData.COMPETENCE_DRIVING_CAR, ci.getProperty("Driving Level").equals("Level 1")) ; 
		
		// create new assets
		ClassDefinition cd = classDefinitionService.getByName(CERTIFICATE_SEF_MODUL1, tenantId);
		classInstanceService.newClassInstance(volunteer, tenantId, cd.getId());
		cd = classDefinitionService.getByName(CERTIFICATE_SEF_MODUL2, tenantId);
		classInstanceService.newClassInstance(volunteer, tenantId, cd.getId());
		
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		
		// check whether competence level was upgraded to level 2
		// refresh competence
		ci = (CompetenceClassInstance) classInstanceService.getClassInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR).get(0);
		// check properties
		assertEquals("Level 2", ci.getProperty(TestData.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		// check whether modules are saved as evidence for level 2
		// get class instances for certificate SEF-Modul 1 and SEF-Modul 2
		cd = classDefinitionService.getByName(CERTIFICATE_SEF_MODUL1, tenantId);
	    ClassInstance ciSEFM1 = classInstanceService.getClassInstance(volunteer, tenantId, cd.getId());
	    cd = classDefinitionService.getByName(CERTIFICATE_SEF_MODUL2, tenantId);
		ClassInstance ciSEFM2 = classInstanceService.getClassInstance(volunteer, tenantId, cd.getId());
		
		/*assertFalse(ci.getProperty(TestData.PROPERTY_EVIDENCE).getValues() == null);
		assertTrue("Zertifikat SEF-Modul 1 nicht in Evidenz! ", classInstanceService .propertyValuesContain(ci.getProperty(TestData.PROPERTY_EVIDENCE), ciSEFM1));
		assertTrue("Zertifikat SEF-Modul 2 nicht in Evidenz! ", volunteerService.propertyValuesContain(ci.getProperty(TestData.PROPERTY_EVIDENCE), ciSEFM2));
		*/
		/* System.out.println("==============================================================================================");
	}
	
	/** 
	 * Car Driving: Level 2 --> Level 3
	 
	public void testCaseImproveDrivingCompetenceRKL3() {
		System.out.println("==============================================================================================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteer = volunteerRepository.findByUsername("KBauer");
		//
		// prepare the data
		testCaseImproveDrivingCompetenceRKL2();
			    
		cleanUpContainer(tenantId, "ivol-test");
		
		ClassDefinition cd = classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_CAR, tenantId);
		CompetenceClassInstance ci = (CompetenceClassInstance) classInstanceService.getClassInstance(volunteer, tenantId, cd.getId());
		// 
		assertFalse("Kompetenz noch < Level 2 " + RKWILHERING + ": "+ TestData.COMPETENCE_DRIVING_CAR, ci.getProperty("Driving Level").equals("LEVEL2")) ; 
		
		// addRule2Container(tenantId, marketplaceService.getMarketplaceId(), "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-improve", ruleImproveDrivingCompetenceRK);
		
		// check whether competence level was upgraded to level 2
		// refresh competence
		ci = (CompetenceClassInstance) classInstanceService.getClassInstance(volunteer, tenantId, cd.getId());
		// check properties
		ClassProperty<Object> cp = classPropertyService.getClassPropertyByName(cd.getId(), TestData.PROPERTY_DRIVING_LEVEL, tenantId);
		assertEquals("Level 3", ci.getProperty(cp.getId()).getValues().get(0));
		System.out.println("==============================================================================================");
	}


	/** 
	 * 
	 
	public void testCaseFahrtenspangeBronze() {
		System.out.println("==============================================================================================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		volunteer = volunteerRepository.findByUsername("KBauer");
		//
		cleanUpContainer(tenantId, "ivol-test-2");
		
		addRule2Container(tenantId, marketplaceService.getMarketplaceId(), "ivol-test-2", "fahrtenspange", ruleIssueFahrtenspange);
		executeRule(volunteer, tenantId, "ivol-test-2", "fahrtenspange", ruleIssueFahrtenspange);
		
		// check whether competence level was upgraded to level 2
		// refresh competence
		ClassDefinition cd = classDefinitionService.getByName("Fahrtenspange Bronze", tenantId);
		List<ClassInstance> ciList = classInstanceService.getClassInstances(volunteer, tenantId, cd.getId());
		assertTrue("Keine Fahrtenspange Bronze vorhanden!", ciList != null);
		// check properties
		// assertEquals("Level 2", ci.getProperty(TestData.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		// check wheter modules are saved as evidence for level 2
		// get class instances for certificate SEF-Modul 1 and SEF-Modul 
		
		System.out.println("==============================================================================================");
	}
	
	/** 
	 * Add driving license for user.
	
	public void testCaseAddDrivingLicense() {
		System.out.println("=========== test case add driving license =========================");
		String tenantId;
		Volunteer volunteer;
		// Feuerwehr
		tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		volunteer = volunteerRepository.findByUsername("EWagner");
		// delete all instances of driving licenses and competences
		ClassDefinition cd = classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
 
		cd = classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_CAR, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_BUS, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_TRUCK, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);
		cd = classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_MOTORCYCLE, tenantId);
		classInstanceService.deleteClassInstances(volunteer, cd.getId(), tenantId);

		System.out.println("OOOOOOOOOOOOOOOOOOOOOOO " + classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_CAR, tenantId));
		
		assertTrue("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_CAR, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_CAR, tenantId) == null) ; 
		assertTrue("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_BUS, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_BUS, tenantId) == null) ; 
		assertTrue("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_TRUCK, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_TRUCK, tenantId) == null) ; 
		assertTrue("Kompetenz exisitiert bereits für " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_MOTORCYCLE, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_MOTORCYCLE, tenantId) == null) ; 
		
		// create new assets
		classInstanceService.newClassInstance(volunteer, tenantId, 
				           classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId).getId());
		classInstanceService.newClassInstance(volunteer, tenantId, 
						   classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_BUS, tenantId).getId());
		classInstanceService.newClassInstance(volunteer, tenantId, 
				           classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK, tenantId).getId());;
		classInstanceService.newClassInstance(volunteer, tenantId, 
				           classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE, tenantId).getId());
		
		//
		DerivationRule dRule = new DerivationRule();
		dRule.setName("addCompetence-drivingLicense");
		ClassSourceRuleEntry cs = new ClassSourceRuleEntry();
		cs.setClassDefinitionId(classDefinitionService.getByName(TestData.CERTIFICATE_DRIVING_LICENSE_CAR, tenantId).getId());
		cs.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);	
		ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
		ruleAction.setClassDefinitionId(classDefinitionService.getByName(TestData.COMPETENCE_DRIVING_CAR, tenantId).getId());
		ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
		AttributeSourceRuleEntry as = new AttributeSourceRuleEntry();
		//ruleAction.setAttributeSourceRules(attributeSourceRules);
		executeRule(volunteer, tenantId, "ivol-test", "test-comp-driving", ruleDriverLicense);
		
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_CAR, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_CAR, tenantId) != null) ; 
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_BUS, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_BUS, tenantId) != null) ;  
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_TRUCK, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_TRUCK, tenantId) != null) ;  
		assertTrue("Kompetenz exisitiert nicht " + FFEIDENBERG + ": "+ TestData.COMPETENCE_DRIVING_MOTORCYCLE, 
				classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_MOTORCYCLE, tenantId) != null) ;  
		
		// check properties
		CompetenceClassInstance ci = (CompetenceClassInstance) classInstanceService.getClassInstanceByName(volunteer, TestData.COMPETENCE_DRIVING_CAR, tenantId);
		assertEquals(ci.getProperty(TestData.PROPERTY_DRIVING_LEVEL).getValues().get(0), "LEVEL1");
		
		System.out.println("==============================================================================================");
	}
	
	/** 
	 * After adding driving licenses,
	 * driving competence are added automatically by rules
	 *
	public void testCaseAddDrivingCompetenceByRules(String tenantId) {
		System.out.println("================== test cases add competence by rules ============================");
		Tenant tenant = coreTenantRestClient.getTenantById(tenantId);
		Volunteer volunteer;
		// Feuerwehr
		volunteer = volunteerRepository.findByUsername("KBauer");
		//
		cleanUp();
		cleanUpContainer(tenantId, "ivol-test-1");
		// ruleService.printContainers();
		// 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_CAR, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_BUS, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_TRUCK, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR)) ; 
		assertFalse("Kompetenz exisitiert bereits für " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS)) ; 
		
		// create new assets
		volunteerService.addClassInstance(volunteer, tenantId, TestData.CERTIFICATE_DRIVING_LICENSE_CAR);
		volunteerService.addClassInstance(volunteer, tenantId, TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK);
		volunteerService.addClassInstance(volunteer, tenantId, TestData.CERTIFICATE_DRIVING_LICENSE_BUS);
		volunteerService.addClassInstance(volunteer, tenantId, TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE);
		// 
		executeRule(volunteer, tenantId, "ivol-test-1", "test-comp-driving", ruleCompetenceDriving);
		
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_CAR, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_BUS, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_TRUCK, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR)) ; 
		assertTrue("Kompetenz exisitiert nicht " + tenant.getName() + ": "+ TestData.COMPETENCE_DRIVING_MOTORCYCLE, volunteerService.hasClassInstance(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS)) ; 
		
		// check properties
		CompetenceClassInstance ci = (CompetenceClassInstance) volunteerService.getClassInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR).get(0);
		assertEquals(ci.getProperty(TestData.PROPERTY_DRIVING_LEVEL).getValues().get(0), "Level 1");
		// DrivingLevel level = DrivingLevel.valueOf((String)ci.getProperty(VolunteerService.PROPERTY_DRIVING_LEVEL).getValues().get(0));
		// System.out.println("............................................. " + level.getDescription());
		// printAllAssets(volunteer, tenantId);
		
		System.out.println("==============================================================================================");
	}*/
	
	public void executeRule(Volunteer volunteer, String tenantId, String container, String ruleName, String ruleContent) {
		String marketplaceId = marketplaceService.getMarketplaceId();
		addRule2Container(tenantId, marketplaceId, container, ruleName, ruleContent);
		ruleService.refreshContainer();
		ruleService.executeRules(tenantId, container, volunteer.getId());
	}

	public void cleanUp() {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		Volunteer volunteer = volunteerRepository.findByUsername("CVoj");
		
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_MOTORCYCLE);
		
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, TestDataClasses.COMPETENCE_DRIVING_MOTORCYCLE);
	}
	
	private void deleteInstances(Volunteer volunteer, String tenantId, String className) {
		ClassDefinition classComp = classDefinitionService.getByName(className, tenantId); 	
		if (classComp != null) {
			classInstanceService.deleteClassInstances(volunteer, classComp.getId(), tenantId);
		}
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
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
	    	"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule CheckLicenseB\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_CAR) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein B!\");\r\n" + 
			"end\r\n" +
			"rule CheckLicenseC\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein C!\");\r\n" + 
			"end\r\n"+
			"rule CheckLicenseD\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_BUS) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein D!\");\r\n" + 
			"end\r\n"+
			"rule CheckLicenseA\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE) == true)\r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein A!\");\r\n" + 
			"end";
	
	public final static String ruleCompetenceDriving = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;\r\n" + 
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;\r\n"+
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
			"import java.util.Arrays;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule addCompDrivingCar\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_CAR) == true && \r\n" + 
			"      !hasClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR)) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein B und bekommt Kompetenz Driving Car!\");\r\n" + 
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR); \r\n" +
            "  vs.setProperty(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_CAR).get(0));\r\n"+
            "  vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL1.getName());\r\n"+
			"end\r\n" +
			"rule addCompDrivingTruck\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK) == true && \r\n" + 
			"	   !hasClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_TRUCK)) " +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein C und bekommt Kompetenz Drivin Truck!\");\r\n" +
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_TRUCK); \r\n" +
			"  vs.setProperty(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_TRUCK).get(0));\r\n"+ 
			"  vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL1.getName());\r\n"+
			"end\r\n"+
			"rule addCompDrivingBus\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_BUS) == true && \r\n" +
			"	                   !hasClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_BUS)) " + 
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein D und bekommt Kompetenz Driving Bus!\");\r\n" + 
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_BUS); \r\n" +
			"  vs.setProperty(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_BUS).get(0));\r\n" +
			"  vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL1.getName());\r\n"+
			"end\r\n"+
			"rule addCompDrivingMotorcycle\r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE) == true && " +
	        "      !hasClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_MOTORCYCLE)) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat Führerschein A und bekommt Kompetenz Driving Motorcycle!\");\r\n" + 
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_MOTORCYCLE); \r\n" +
			"  vs.setProperty(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(), TestData.CERTIFICATE_DRIVING_LICENSE_MOTORCYCLE).get(0));\r\n"+
			"  vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL1.getName());\r\n"+
			"end";
	
	public final static String ruleImproveDrivingCompetenceRK = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;\r\n"+
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
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
			" vs: VolunteerService(hasClassInstance(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR) == true) \r\n" +
			"then\r\n" + 
			"  insert(vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR).get(0));\r\n" + 
			"end\r\n" +
			"rule improveCompDrivingCarL2 \r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService("+
	//		"          getFilteredInstancesByProperty(vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR), TestData.PROPERTY_DRIVING_LEVEL, \"Level 1\").size() > 0, "+
			"          hasClassInstance(v, t.getId(), \"SEF-Modul 1\") == true, \r\n" + 
			"          hasClassInstance(v, t.getId(), \"SEF-Modul 2\") == true) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat SEF-Modul 1 und 2 verbessert Kompetenz Driving Car!\");\r\n" + 
			"  ClassInstance ci = vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR).get(0); \r\n" +
			 " vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL2.getName());\r\n"+
			 " vs.addPropertyValue(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(), \"SEF-Modul 1\").get(0));\r\n"+
			 " vs.addPropertyValue(ci,TestData.PROPERTY_EVIDENCE, vs.getClassInstances(v, t.getId(),\"SEF-Modul 2\" ).get(0));\r\n"+
            "end\r\n" +
            "rule improveCompDrivingCarL3 \r\n" +  
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  c : ClassInstance() \r\n" +
			"  vs: VolunteerService(  " + 
	//		"          getFilteredInstancesByProperty(vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR), TestData.PROPERTY_DRIVING_LEVEL, \"Level 2\").size() > 0, "+
     		"          getFilteredInstancesByProperty(v, t.getId(), \"Ausfahrt\", \"role\", \"Einsatzlenker\").size() >= 100) \r\n"+
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger hat mehr als 100 Ausfahrten als Fahrer und verbessert Kompetenz Driving Car auf Level 3!\");\r\n" + 
			"  System.out.println(\"Ausfahrten: \" + vs.getFilteredInstancesByProperty(v, t.getId(), \"Ausfahrt\", \"role\", \"Einsatzlenker\").size());\r\n" +
			"  ClassInstance ci = vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR).get(0); \r\n" +
			 " vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL3.getName());\r\n"+
			 " vs.addPropertyValue(ci,TestData.PROPERTY_EVIDENCE, \"Anzahl Ausfahrten >= 100\");\r\n"+
            "end\r\n" +
            "rule improveCompDrivingCarL4 \r\n" +  
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
		    "  vs: VolunteerService("+
			"          getFilteredInstancesByProperty(vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR), TestData.PROPERTY_DRIVING_LEVEL, \"Level 3\").size() > 0, "+
     	    "          hasClassInstance(v, t.getId(), \"SEF Aus- und Fortbildung\") == true, " + 
     	    "		   hasClassInstance(v, t.getId(), \"SEF Workshop\") == true," +
     		"          hasClassInstance(v, t.getId(), \"SEF – Theorie- und Praxistraining Notarzt\") == true, " + 
     	    "          hasClassInstance(v, t.getId(), \"SEF Theorietrainerausbildung\") == true) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"Ausfahrten: \" + vs.getFilteredInstancesByProperty(v, t.getId(), \"Ausfahrt\", \"role\", \"Einsatzlenker\").size());\r\n" +
			"  ClassInstance ci = vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR).get(0); \r\n" +
			 " vs.setProperty(ci,TestData.PROPERTY_DRIVING_LEVEL, TestData.DrivingLevel.LEVEL4.getName());\r\n"+
			"end";

	public final static String ruleIssueFahrtenspange = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" + 
	        "import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" +
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" +
	        "import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n"+
			"import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;\r\n"+
			"import at.jku.cis.iVolunteer.test.data.TestData;\r\n"+
			"import java.util.Date;\r\n" + 
			"import java.util.Arrays;\r\n" +
			"dialect \"mvel\"\r\n" + 
	        "\r\n" + 
			"rule checkFahrtenspange \r\n" + 
			"when\r\n" + 
			"  v : Volunteer() \r\n" +
			"  t : Tenant() \r\n" +
			"  vs: VolunteerService( " + // getClassInstance(v, t.getId(), \"Fahrtenspange Bronze\").size() == 0, " +
			"          getClassInstances(v, t.getId(), \"Ausfahrt\").size() >= 1000) \r\n" +
			"then\r\n" + 
			"  System.out.println(\"********************** Freiwilliger bekommt Fahrtenspange Bronze!\");\r\n" + 
			"  ClassInstance ci = vs.addClassInstance(v, t.getId(), \"Fahrtenspange Bronze\"); \r\n" +
			 " vs.setProperty(ci, \"Verliehen am\", new Date());\r\n"+
            "end";

	public final static String ruleMapper = "package at.jku.cis.iVolunteer.marketplace.rule.engine; \r\n" + 
			"\r\n" + 
			"import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" + 
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" + 
			"import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n" + 
			"\r\n" + 
			"dialect \"mvel\"\r\n" + 
			"\r\n" + 
			"rule \"Test-Claudia\"\r\n" + 
			"when\r\n" + 
			"    v : Volunteer(  )  \r\n" + 
			"    t : Tenant(  )  \r\n" + 
			"    vs : VolunteerService( getClassInstancesById(v, t.getId(), \"5ebabf2808aa933ad0e86216\").size() >= 200 )  \r\n" + 
			"then\r\n" + 
			"System.out.println(\"Volunteer is older than 18\");\r\n" + 
			"\r\n" + 
			"end";
	
	public final static String ruleMapper2 = "package at.jku.cis.iVolunteer.marketplace.rule.engine; \r\n" + 
			"\r\n" + 
			"import at.jku.cis.iVolunteer.model.user.Volunteer;\r\n" + 
			"import at.jku.cis.iVolunteer.marketplace.user.VolunteerService;\r\n" + 
			"import at.jku.cis.iVolunteer.model.core.tenant.Tenant;\r\n" + 
			"\r\n" + 
			"dialect \"mvel\"\r\n" + 
			"\r\n" + 
			"rule \"bootstrap\"\r\n"+
			"salience 1"+
			"no-loop"+
			"when\r\n" +
				" v : Volunteer() \r\n" +
				" t : Tenant() \r\n" +
				" vs: VolunteerService() \r\n" +
				"then\r\n" + 
				"  insert(vs.getClassInstances(v, t.getId(), TestData.COMPETENCE_DRIVING_CAR).get(0));\r\n" + 
			"rule \"Is of valid age\"\r\n" + 
			"when\r\n" + 
			"    v : Volunteer(  )  \r\n" + 
			"    t : Tenant(  )  \r\n" + 
			"    VolunteerService( currentAge(v) >= 18 )  \r\n" + 
			"then\r\n" + 
			"System.out.println(\"Volunteer is older than 18\");\r\n" + 
			"\r\n" + 
			"end";
	
	/*
	 Query query = new Query(Criteria.where("name").is("Ausfahrt"));//.and("classDefinitionId").is("5ebabf2808aa933ad0e86216"));
	   // List<ClassInstance> tasks = classInstanceRepository.getbyQuery(query);
	    /*
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Ausfahrt", tenantId);
	    TaskClassInstance task = new TaskClassInstance();
	    task.setClassDefinitionId(taskDef.getId());
	    List<PropertyInstance<Object>> propInstList = new ArrayList<PropertyInstance<Object>>();
		List<ClassProperty<Object>> propList = taskDef.getProperties();
		// propInstList = propList.stream().
			//	filter(p -> /*p.getName().equals("role") ||*/// p.getName().contentEquals("Ort")).
	//			map(p -> classPropertyToPropertyInstanceMapper.toTarget(p)).
		//		collect(Collectors.toList());
		//task.setProperties(propInstList);
	   //  task.getProperty("role").setValues(Arrays.asList("Einsatzlenker"));
		// task.getProperty("role").setValues(Arrays.asList("Sanitäter"));
		//task.getProperty("Ort").setValues(Arrays.asList("Linz"));
	   // System.out.println("role: " + task.getProperty("role").getValues());
	    // System.out.println("Ort: " + task.getProperty("Ort").getValues());
	    
	    /*final ExampleMatcher matcher = ExampleMatcher.matching()
              .withIgnoreNullValues()
              .withMatcher("properties", match -> match.transform(source -> ((List<PropertyInstance<Object>>) source).iterator().next()).caseSensitive());

      //users = userRepository.findAll(Example.of(task, matcher), pageRequest);
     
      //List<TaskClassInstance> tasks = classInstanceRepository.findAll(Example.of(task, matcher));*/
	   //  List<TaskClassInstance> tasks = classInstanceRepository.findAll(Example.of(task, ExampleMatcher.matchingAll()));
		//System.out.println("Ausfahrten gefunden: " + tasks.size());
	   // System.out.println("===================================================================================");
	//}
	
	/* 
	    PackageDescrBuilder packageDescrBuilder = DescrFactory.newPackage();
	    packageDescrBuilder
	            .name("at.jku.cis.iVolunteer.marketplace.rule.engine;")
	            .newImport()
	            .target("at.jku.cis.iVolunteer.model.user.Volunteer;").end()
	            .newImport().target("at.jku.cis.iVolunteer.marketplace.user.VolunteerService;").end()
	            .newImport().target("at.jku.cis.iVolunteer.model.core.tenant.Tenant;").end()
	            .attribute( "dialect" ).value( "mvel" ).end()
	            .newRule() 
	            .name(dRule.getName()) 
	            .lhs()
	            .pattern().id("v", false).type("Volunteer").end()
	            .pattern().id("t", false).type("Tenant").end()
	            .pattern().id("vs", false).type("VolunteerService").
	                        constraint("getClassInstancesById(v, t.getId(), \"" + cRule.getClassDefinitionId()+"\").size() >= " + cRule.getValue())
	                        .end()
	            .end()
	            .rhs("System.out.println(\"Volunteer is older than 18\");")
	            .end();*/
	/*
	 * db.inventory.find( {
                     qty: { $all: [
                                    { "$elemMatch" : { size: "M", num: { $gt: 50} } },
                                    { "$elemMatch" : { num : 100, color: "green" } }
                                  ] }
                   } )
	*/
	/*
	 * db.getCollection('classInstance').find({"name": "Ausfahrt", "tenantId" : "5e97104b8cbb214434400000", 
	 *          "properties": {$all: [
	 *                     {"$elemMatch": {name: "Start Date", values: {$lte: new Date("2016-07-07T00:00:00.000Z")}}},
	 *                     {"$elemMatch": {name: "role", values:"Einsatzlenker"}}]}})
	 */
}

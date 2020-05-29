package at.jku.cis.iVolunteer;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.DRL5Expressions.type_return;
import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.api.impl.CEDescrBuilderImpl;
import org.drools.compiler.lang.api.impl.PatternDescrBuilderImpl;
import org.drools.compiler.lang.api.impl.RuleDescrBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.EQCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.GTCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.LTCriteria;
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
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassActionRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.ClassRuleActionType;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralAttributeEntry;
import at.jku.cis.iVolunteer.model.rule.GeneralAttributeEntry.Attribute;
import at.jku.cis.iVolunteer.model.rule.MappingOperatorType;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.test.data.TestData;
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
	@Autowired private VolunteerService volunteerService;
	@Autowired private RuleService ruleService;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassPropertyService classPropertyService;
	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private RuleEngineMapper ruleEngineMapper;
	
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private HelpSeekerRepository helpSeekerRepository;
	
	private static final String CERTIFICATE_SEF_MODUL1 = "SEF-Modul 1";
	private static final String CERTIFICATE_SEF_MODUL2 = "SEF-Modul 2";
	
	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
	private static final String RKWILHERING = "RK Wilhering";
	
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
	
	public void executeTestCases(){
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
		testAddCompetenceDrivingCarNotExists();
		testAddAllCompetencesDriving();
		testAddCompetenceDrivingCar();
		testImproveDrivingSkillsLevel2();
		testImproveDrivingSkillsLevel3();
		testImproveDrivingSkillsLevel4();
		// random test cases
		testCheckAge();
		testCheckAgeMaturity();
		//testImproveDrivingSkills();
	}
	
	public void testAddAllCompetencesDriving() {
		System.out.println("============== add competences for all driving licenses ================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-1"; 
	    String ruleName = "add-competence-driving-all";
	    
	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR);
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_TRUCK);
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS);
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_MOTORCYCLE);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);

	    ClassSourceRuleEntry cRuleB = new ClassSourceRuleEntry();
	    cRuleB.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId).getId());
	    cRuleB.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    cRuleB.setAttributeSourceRules(attrRuleList);
	    
	    ClassSourceRuleEntry cRuleC = new ClassSourceRuleEntry();
	    cRuleC.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Truck", tenantId).getId());
	    cRuleC.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    cRuleC.setAttributeSourceRules(attrRuleList);
	    
	    ClassSourceRuleEntry cRuleD = new ClassSourceRuleEntry();
	    cRuleD.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Bus", tenantId).getId());
	    cRuleD.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    cRuleD.setAttributeSourceRules(attrRuleList);
	    
	    ClassSourceRuleEntry cRuleA = new ClassSourceRuleEntry();
	    cRuleA.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Motorcycle", tenantId).getId());
	    cRuleA.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    cRuleA.setAttributeSourceRules(attrRuleList);
	     
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    classRuleList.add(cRuleB);
	    classRuleList.add(cRuleC);
	    classRuleList.add(cRuleD);
	    classRuleList.add(cRuleA);
	    dRule.setLhsClassConditions(classRuleList);
	    
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();   
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    AttributeSourceRuleEntry attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein B");
	    AttributeSourceRuleEntry attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    
	    ruleAction = new ClassActionRuleEntry();
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Truck Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein C");
	    attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    
	    ruleAction = new ClassActionRuleEntry();
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Bus Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein D");
	    attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    
	    ruleAction = new ClassActionRuleEntry();
	    compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Motorcycle Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein A");
	    attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    
	    dRule.setRhsRuleActions(rhsRuleActions);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-1.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR);
	    
	    AchievementClassDefinition achievementDef = (AchievementClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(achievementDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	    cRule.setAttributeSourceRules(attrRuleList);
	    
	    classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    AttributeSourceRuleEntry attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein B");
	    AttributeSourceRuleEntry attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-5.drl");
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
	    deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR);
	    
	    AchievementClassDefinition achievementDef = (AchievementClassDefinition) classDefinitionRepository.
	    		findByNameAndTenantId("Driving License Car", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(achievementDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    
	    ClassSourceRuleEntry cRuleNe = new ClassSourceRuleEntry();
	    cRuleNe.setClassDefinitionId(classDefinitionRepository.
	    		findByNameAndTenantId(TestData.COMPETENCE_DRIVING_CAR, tenantId).getId());
	    cRuleNe.setAggregationOperatorType(ClassAggregationOperatorType.NOT_EXISTS);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	    cRule.setAttributeSourceRules(attrRuleList);
	    
	    classRuleList.add(cRule);
	    classRuleList.add(cRuleNe);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition compDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
		   
	    ruleAction.setClassDefinitionId(compDef.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(compDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL1);
	    AttributeSourceRuleEntry attrDel2 = new AttributeSourceRuleEntry();
	    attrDel2.setClassDefinitionId(compDef.getId());
	    attrDel2.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Evidence", tenantId).getId());
	    attrDel2.setValue("Führerschein B");
	    AttributeSourceRuleEntry attrDel3 = new AttributeSourceRuleEntry();
	    attrDel3.setClassDefinitionId(compDef.getId());
	    attrDel3.setClassPropertyId(classPropertyService.getClassPropertyByName(compDef.getId(), "Issued", tenantId).getId());
	    attrDel3.setValue(LocalDateTime.now());
	    attrDel.add(attrDel1);
	    attrDel.add(attrDel2);
	    attrDel.add(attrDel3);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
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
	
	public void testCheckAge() {
		System.out.println("==========================  Test check age of volunteer ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
	    String containerName = "ivol-test-4"; 
	    String ruleName = "check-age";

	    ruleService.deleteRule(tenantId, containerName, ruleName);
	    deleteInstances(volunteer, tenantId, "Maturity");
	    
	    CompetenceClassDefinition taskDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.MIN);
	    cRule.setValue(200);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<GeneralAttributeEntry> generalAttributes = new ArrayList<GeneralAttributeEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	    
	    // set age between 20 and 70: 20 < age < 70
	    GeneralAttributeEntry genAttr1 = new GeneralAttributeEntry(Attribute.AGE, 20, MappingOperatorType.GT);
	    generalAttributes.add(genAttr1);
	    GeneralAttributeEntry genAttr2 = new GeneralAttributeEntry(Attribute.AGE, 70, MappingOperatorType.LT);
	    generalAttributes.add(genAttr2);
	    dRule.setLhsGeneralConditions(generalAttributes);
	    
	    //classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.NEW);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(taskDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Maturity Level", tenantId).getId());
	    attrDel1.setMappingOperatorType(MappingOperatorType.EQ);
	    attrDel1.setValue(30);

	    attrDel.add(attrDel1);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
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
	    
	    CompetenceClassDefinition taskDef = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<GeneralAttributeEntry> generalAttributes = new ArrayList<GeneralAttributeEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	    
	    // set age between 20 and 70: 20 < age < 70
	    GeneralAttributeEntry genAttr1 = new GeneralAttributeEntry(Attribute.AGE, 20, MappingOperatorType.GT);
	    generalAttributes.add(genAttr1);
	    GeneralAttributeEntry genAttr2 = new GeneralAttributeEntry(Attribute.AGE, 70, MappingOperatorType.LT);
	    generalAttributes.add(genAttr2);
	    dRule.setLhsGeneralConditions(generalAttributes);
	    
	    AttributeSourceRuleEntry attr1 = new AttributeSourceRuleEntry();
	    attr1.setClassDefinitionId(taskDef.getId());
	    attr1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "Maturity Level", tenantId).getId());
	    attr1.setMappingOperatorType(MappingOperatorType.GE);
	    attr1.setValue(30);
	    
	    attrRuleList.add(attr1);
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Maturity", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.UPDATE);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(taskDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Maturity Level", tenantId).getId());
	    attrDel1.setMappingOperatorType(MappingOperatorType.EQ);
	    attrDel1.setValue(33);

	    attrDel.add(attrDel1);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
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
	    
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Ausfahrt", tenantId);
	   
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.MIN);
	    cRule.setValue(200);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	    
	    // adding filters for properties
	    AttributeSourceRuleEntry attr1 = new AttributeSourceRuleEntry();
	    attr1.setClassDefinitionId(taskDef.getId());
	    attr1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId).getId());
	    attr1.setMappingOperatorType(MappingOperatorType.EQ);
	    attr1.setValue("Einsatzlenker");
	    //
	    attrRuleList.add(attr1);
	    cRule.setAttributeSourceRules(attrRuleList);
	    
	    classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.UPDATE);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(taskDef.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL2);

	    attrDel.add(attrDel1);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-6.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	   
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(certDef1.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
	    
	    cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(certDef2.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
 	   
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.UPDATE);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(cd.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL3);

	    attrDel.add(attrDel1);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
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
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<ClassActionRuleEntry>();
	   
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName(ruleName);
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(certDef1.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
	   
	    cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(certDef2.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.EXISTS);
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
 	   
	    cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.MIN);
	    cRule.setAttributeSourceRules(attrRuleList);
	    cRule.setValue(1000);
	    classRuleList.add(cRule);
 	   
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    ClassActionRuleEntry ruleAction = new ClassActionRuleEntry();
	    CompetenceClassDefinition cd = (CompetenceClassDefinition) classDefinitionRepository.findByNameAndTenantId("Car Driving", tenantId);
	    ruleAction.setClassDefinitionId(cd.getId());
	    ruleAction.setClassRuleActionType(ClassRuleActionType.UPDATE);
	    List<AttributeSourceRuleEntry> attrDel = new ArrayList<AttributeSourceRuleEntry>();
	   // adding filters for properties
	    AttributeSourceRuleEntry attrDel1 = new AttributeSourceRuleEntry();
	    attrDel1.setClassDefinitionId(cd.getId());
	    attrDel1.setClassPropertyId(classPropertyService.getClassPropertyByName(cd.getId(), "Driving Level", tenantId).getId());
	    attrDel1.setValue(TestData.DrivingLevel.LEVEL4);

	    attrDel.add(attrDel1);
	    ruleAction.setAttributeSourceRules(attrDel);
	    
	    rhsRuleActions.add(ruleAction);
	    dRule.setRhsRuleActions(rhsRuleActions);
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
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.MIN);
	    cRule.setValue(99);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<GeneralAttributeEntry> generalAttributes = new ArrayList<GeneralAttributeEntry>();
	    List<ClassSourceRuleEntry> rhsRuleActions = new ArrayList<ClassSourceRuleEntry>();
	    
	    // set age between 20 and 70: 20 < age < 70
	    GeneralAttributeEntry genAttr1 = new GeneralAttributeEntry(Attribute.AGE, 20, MappingOperatorType.GT);
	    generalAttributes.add(genAttr1);
	    GeneralAttributeEntry genAttr2 = new GeneralAttributeEntry(Attribute.AGE, 70, MappingOperatorType.LT);
	    generalAttributes.add(genAttr2);
	    dRule.setLhsGeneralConditions(generalAttributes);
	    
	    // adding filters for properties
	    AttributeSourceRuleEntry attr1 = new AttributeSourceRuleEntry();
	    attr1.setClassDefinitionId(taskDef.getId());
	    attr1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId).getId());
	    attr1.setMappingOperatorType(MappingOperatorType.EQ);
	    attr1.setValue("Einsatzlenker");
	    
	    
	    AttributeSourceRuleEntry attr2 = new AttributeSourceRuleEntry();
	    attr2.setClassDefinitionId(taskDef.getId());
	    attr2.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "Ort", tenantId).getId());
	    attr2.setMappingOperatorType(MappingOperatorType.EQ);
	    attr2.setValue("Wels");

	    AttributeSourceRuleEntry attr3 = new AttributeSourceRuleEntry();
	    attr3.setClassDefinitionId(taskDef.getId());
	    attr3.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "End Date", tenantId).getId());
	    attr3.setMappingOperatorType(MappingOperatorType.GT);
	    String strDate = "2018-08-04T08:00:00";
	    LocalDateTime aLD = LocalDateTime.parse(strDate);
	    attr3.setValue(aLD);
	    //
	    attrRuleList.add(attr1);
	    attrRuleList.add(attr2);
	    attrRuleList.add(attr3);
	    cRule.setAttributeSourceRules(attrRuleList);
	    
	    classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    //
	    // now set the action part on the rhs
	    
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    
	    /*ClassInstance ci = classInstanceService.getClassInstances(volunteer, "5ebabf2808aa933ad0e86216", tenantId).get(0);
	   
	    List<PropertyInstance<Object>> properties = ci.getProperties();
	    for (PropertyInstance<Object> p: properties) {
	    	System.out.println("Name: " + p.getName() + ", id: " + p.getId());
	    	System.out.println(" ..... " + ci.getProperty(attr1.getClassPropertyId()).getValues().get(0));
	    }*/
	    
	    System.out.println("-------> " + classInstanceService.getClassInstances(volunteer, "5ebabf2808aa933ad0e86216", tenantId).size());
	    
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test-2.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		executeRule(volunteer, tenantId, "ivol-test", "test-mapper-2", ruleContent);
	    
	    System.out.println("===================================================================================");
	}
	
	public void testMapping2() {
		System.out.println("==========================  Test Mapping ==========================================");
		// Feuerwehr
	    String tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
	    Volunteer volunteer = volunteerRepository.findByUsername("KBauer");
				//
	   // executeRule(volunteer, tenantId, "ivol-test", "test-mapper", ruleMapper);
	    
	    TaskClassDefinition taskDef = (TaskClassDefinition) classDefinitionRepository.findByNameAndTenantId("Ausfahrt", tenantId);
	    List<PropertyDefinition<Object>> pd1 = propertyDefinitionRepository.getByNameAndTenantId("role", tenantId);
	    
	    DerivationRule dRule = new DerivationRule();
	    dRule.setName("Test-Claudia");
	    ClassSourceRuleEntry cRule = new ClassSourceRuleEntry();
	    cRule.setClassDefinitionId(taskDef.getId());
	    cRule.setAggregationOperatorType(ClassAggregationOperatorType.COUNT);
	    cRule.setValue(200);
	    
	    List<ClassSourceRuleEntry> classRuleList = new ArrayList<ClassSourceRuleEntry>();
	    List<AttributeSourceRuleEntry> attrRuleList = new ArrayList<AttributeSourceRuleEntry>();
	    List<GeneralAttributeEntry> generalAttributes = new ArrayList<GeneralAttributeEntry>();
	    
	    GeneralAttributeEntry genAttr = new GeneralAttributeEntry(Attribute.AGE, 18, MappingOperatorType.GE);
	    generalAttributes.add(genAttr);
	    dRule.setLhsGeneralConditions(generalAttributes);
	    
	    AttributeSourceRuleEntry attr1 = new AttributeSourceRuleEntry();
	    attr1.setClassDefinitionId(taskDef.getId());
	    attr1.setClassPropertyId(classPropertyService.getClassPropertyByName(taskDef.getId(), "role", tenantId).getId());
	    attr1.setMappingOperatorType(MappingOperatorType.EQ);
	    attr1.setValue("Einsatzlenker");
	    attrRuleList.add(attr1);
	    System.out.println(" .... attribute rule list: " + attrRuleList.size());
	    cRule.setAttributeSourceRules(attrRuleList);
	    classRuleList.add(cRule);
	    dRule.setLhsClassConditions(classRuleList);
	    
	    // now map from Derivation Rule to Drools 
	    // dRule
	    String ruleContent = ruleEngineMapper.generateDroolsRuleFrom(dRule);
	    executeRule(volunteer, tenantId, "ivol-test", "test-mapper-1", ruleContent);
	    		
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
	            .end();
	    
	            
	            for (ClassSourceRuleEntry ruleEntry: dRule.getClassSourceRules()) {
	            }
	    // packageDescrBuilder.newRule("test claudia")

	    String rules = new DrlDumper().dump(packageDescrBuilder.getDescr());*/
	    FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:/data/rule-test.drl");
			myWriter.write(ruleContent);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    
	    
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
	    System.out.println("===================================================================================");
	}
	
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
				filter(p -> /*p.getName().equals("role") ||*/ p.getName().contentEquals("Ort")).
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
	 */
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
	 */
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
	 */
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
		
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_MOTORCYCLE);
		
		tenantId = coreTenantRestClient.getTenantIdByName(RKWILHERING);
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_CAR); 	
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_BUS);
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_TRUCK); 	
		deleteInstances(volunteer, tenantId, TestData.COMPETENCE_DRIVING_MOTORCYCLE);
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
	
	
}

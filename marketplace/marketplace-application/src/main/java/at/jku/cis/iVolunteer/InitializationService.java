package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToReset;
import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToResetRepository;
import at.jku.cis.iVolunteer.marketplace.feedback.FeedbackRepository;
import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.marketplace.user.HelpSeekerRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingRepository;
import at.jku.cis.iVolunteer.model.feedback.Feedback;
import at.jku.cis.iVolunteer.model.feedback.FeedbackType;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.rule.AttributeAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.MappingOperatorType;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class InitializationService {

	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionsRepository;
	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private FinalizationService finalizationService;
	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private HelpSeekerRepository helpSeekerRepository;
	@Autowired private FeedbackRepository feedbackRepository;
	@Autowired private UserMappingRepository userMappingRepository;
	@Autowired private Environment environment;

	@PostConstruct
	public void init() {
//		finalizationService.destroy(configuratorRepository, classDefinitionRepository, classInstanceRepository,
//				relationshipRepository, propertyDefinitionRepository, derivationRuleRepository);

//		if(environment.acceptsProfiles("dev")) {}
//		addTestConfigClasses();
//		addConfigurators();
//
//		addiVolunteerAPIClassDefinition();
//		addTestDerivationRule();
//		this.addTestClassInstances();

		this.addStandardPropertyDefinitions();
		this.addFlexProdConfigClassesConsumer();
		this.addFlexProdConfigClassesProducer();

	}

	private void addTestDerivationRule() {
		DerivationRule rule = new DerivationRule();
		rule.setName("myrule");

		AttributeSourceRuleEntry source = new AttributeSourceRuleEntry();
		source.setClassDefinitionId(classDefinitionRepository.findByName("PersonBadge").getId());
		source.setClassPropertyId(classDefinitionRepository.findByName("PersonBadge").getProperties().get(0).getId());
		source.setMappingOperatorType(MappingOperatorType.GE);
		source.setAggregationOperatorType(AttributeAggregationOperatorType.SUM);
		source.setValue("102");
		rule.setAttributeSourceRules(Lists.asList(source, new AttributeSourceRuleEntry[0]));

		ClassSourceRuleEntry cSource = new ClassSourceRuleEntry();
		cSource.setClassDefinitionId(classDefinitionRepository.findByName("PersonBadge").getId());
		cSource.setMappingOperatorType(MappingOperatorType.GE);
		cSource.setAggregationOperatorType(ClassAggregationOperatorType.COUNT);
		cSource.setValue("102");
		rule.setClassSourceRules(Lists.asList(cSource, new ClassSourceRuleEntry[0]));

		rule.setTarget(classDefinitionRepository.findByName("PersonCertificate").getId());
		rule.setMarketplaceId(marketplaceService.getMarketplaceId());
		derivationRuleRepository.save(rule);
	}

	private void addiVolunteerAPIClassDefinition() {
		ClassDefinition findByName = classDefinitionRepository.findByName("PersonRole");
		if (findByName == null) {
			createiVolunteerAPIPersonRoleClassDefinition();
			createiVolunteerAPIPersonBadgeClassDefinition();
			createiVolunteerAPIPersonCertificateClassDefinition();
			createiVolunteerAPIPersonTaskClassDefinition();
		}

	}

	private void createiVolunteerAPIPersonRoleClassDefinition() {
		FunctionClassDefinition functionDefinition = new FunctionClassDefinition();
		functionDefinition.setClassArchetype(ClassArchetype.FUNCTION);
		functionDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		functionDefinition.setRoot(true);
		functionDefinition.setName("PersonRole");
		functionDefinition.setTimestamp(new Date());
		List<PropertyDefinition<Object>> properties = addPropertyDefinitions();
		functionDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonRoleProperties(properties)));
		classDefinitionRepository.save(functionDefinition);
	}

	private void createiVolunteerAPIPersonBadgeClassDefinition() {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonBadge");
		achievementDefinition.setTimestamp(new Date());
		List<PropertyDefinition<Object>> properties = addPropertyDefinitions();
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonBadgeProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonCertificateClassDefinition() {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonCertificate");
		achievementDefinition.setTimestamp(new Date());
		List<PropertyDefinition<Object>> properties = addPropertyDefinitions();
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonCertificateProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonTaskClassDefinition() {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.TASK);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonTask");
		achievementDefinition.setTimestamp(new Date());
		List<PropertyDefinition<Object>> properties = addPropertyDefinitions();
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonTaskProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private List<PropertyDefinition<Object>> filterPersonRoleProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("roleID") || p.getName().equals("roleType")
						|| p.getName().equals("roleName") || p.getName().equals("roleDescription")
						|| p.getName().equals("organisationID") || p.getName().equals("organisationName")
						|| p.getName().equals("organisationType") || p.getName().equals("dateFrom")
						|| p.getName().equals("dateTo") || p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonBadgeProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("badgeID") || p.getName().equals("badgeName")
						|| p.getName().equals("badgeDescription") || p.getName().equals("badgeIssuedOn")
						|| p.getName().equals("badgeIcon") || p.getName().equals("iVolunteerUUID")
						|| p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonCertificateProperties(
			List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("certificateID") || p.getName().equals("certificateName")
						|| p.getName().equals("certificateDescription") || p.getName().equals("certificateIssuedOn")
						|| p.getName().equals("certificateValidUntil") || p.getName().equals("certificateIcon")
						|| p.getName().equals("iVolunteerUUID") || p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonTaskProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream().filter(p -> p.getName().equals("taskId") || p.getName().equals("taskName")
				|| p.getName().equals("taskType1") || p.getName().equals("taskType2") || p.getName().equals("taskType3")
				|| p.getName().equals("taskType4") || p.getName().equals("taskDescription")
				|| p.getName().equals("Zweck") || p.getName().equals("Rolle") || p.getName().equals("Rang")
				|| p.getName().equals("Phase") || p.getName().equals("Arbeitsteilung") || p.getName().equals("Ebene")
				|| p.getName().equals("taskCountAll") || p.getName().equals("taskDateFrom")
				|| p.getName().equals("taskDateTo") || p.getName().equals("taskDuration")
				|| p.getName().equals("taskLocation") || p.getName().equals("taskGeoInformation")
				|| p.getName().equals("iVolunteerUUID") || p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> addPropertyDefinitions() {
		List<PropertyDefinition<Object>> propertyDefinitions = new ArrayList<>();
		addCrossCuttingProperties(propertyDefinitions);
		addPersonRoleProperties(propertyDefinitions);
		addPersonBadgeProperties(propertyDefinitions);
		addPersonCertificateProperties(propertyDefinitions);
		addPersonTaskProperties(propertyDefinitions);
		return propertyDefinitionsRepository.save(propertyDefinitions);
	}

	private void addCrossCuttingProperties(List<PropertyDefinition<Object>> propertyDefinitions) {
		propertyDefinitions.add(new PropertyDefinition<Object>("iVolunteerUUID", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("iVolunteerSource", PropertyType.TEXT));
	}

	private void addPersonRoleProperties(List<PropertyDefinition<Object>> propertyDefinitions) {
		propertyDefinitions.add(new PropertyDefinition<Object>("roleID", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("roleType", PropertyType.TEXT));
//		propertyDefinitions.add(new PropertyDefinition<Object>("roleName", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("roleDescription", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationID", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationName", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationType", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("dateFrom", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("dateTo", PropertyType.DATE));
	}

	private void addPersonBadgeProperties(List<PropertyDefinition<Object>> propertyDefinitions) {
		propertyDefinitions.add(new PropertyDefinition<Object>("badgeID", PropertyType.TEXT));
//		propertyDefinitions.add(new PropertyDefinition<Object>("badgeName", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("badgeDescription", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("badgeIssuedOn", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("badgeIcon", PropertyType.TEXT));
	}

	private void addPersonCertificateProperties(List<PropertyDefinition<Object>> propertyDefinitions) {
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateID", PropertyType.TEXT));
//		propertyDefinitions.add(new PropertyDefinition<Object>("certificateName", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateDescription", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateIssuedOn", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateValidUntil", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateIcon", PropertyType.TEXT));
	}

	private void addPersonTaskProperties(List<PropertyDefinition<Object>> propertyDefinitions) {
		propertyDefinitions.add(new PropertyDefinition<Object>("taskId", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskName", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType1", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType2", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType3", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType4", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskDescription", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Zweck", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Rolle", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Rang", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Phase", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Arbeitsteilung", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("Ebene", PropertyType.TEXT));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskDateFrom", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskDateTo", PropertyType.DATE));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskDuration", PropertyType.FLOAT_NUMBER));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskLocation", PropertyType.TEXT));
//			TODO task geoinformation to geo object
		propertyDefinitions.add(new PropertyDefinition<Object>("taskGeoInformation", PropertyType.TEXT));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addTestConfigClasses() {

		CompetenceClassDefinition c1 = new CompetenceClassDefinition();
		c1.setId("test1");
		c1.setName("Class 1");
		c1.setProperties(new ArrayList<ClassProperty<Object>>());
		c1.setRoot(true);

		PropertyDefinition npd = new StandardPropertyDefinitions.NameProperty();
		ClassProperty<Object> ncp = propertyDefinitionToClassPropertyMapper.toTarget(npd);
		c1.getProperties().add(ncp);

		PropertyDefinition sdpd = new StandardPropertyDefinitions.StartDateProperty();
		ClassProperty<Object> sdcp = propertyDefinitionToClassPropertyMapper.toTarget(sdpd);
		c1.getProperties().add(sdcp);

		PropertyDefinition dpd = new StandardPropertyDefinitions.DescriptionProperty();
		ClassProperty<Object> dcp = propertyDefinitionToClassPropertyMapper.toTarget(dpd);
		c1.getProperties().add(dcp);

		c1.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c2 = new CompetenceClassDefinition();
		c2.setId("test2");
		c2.setName("Class 2");
		c2.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c3 = new CompetenceClassDefinition();
		c3.setId("test3");
		c3.setName("Class 3");
		c3.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c4 = new CompetenceClassDefinition();
		c4.setId("test4");
		c4.setName("Class 4");
		c4.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c5 = new CompetenceClassDefinition();
		c5.setId("test5");
		c5.setName("Class 5");
		c5.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c6 = new CompetenceClassDefinition();
		c6.setId("test6");
		c6.setName("Class 6");
		c6.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c7 = new CompetenceClassDefinition();
		c7.setId("test7");
		c7.setName("Class 7");
		c7.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c8 = new CompetenceClassDefinition();
		c8.setId("test8");
		c8.setName("Class 8");
		c8.setClassArchetype(ClassArchetype.COMPETENCE);

		CompetenceClassDefinition c9 = new CompetenceClassDefinition();
		c9.setId("test9");
		c9.setName("Class 9");
		c9.setClassArchetype(ClassArchetype.COMPETENCE);

//		{from: 1, to: 3},
//		  {from: 1, to: 2},
//		  {from: 2, to: 4},
//		  {from: 2, to: 5},
//		  {from: 3, to: 3},
//		  {from: 6, to: 6},

		Inheritance i1 = new Inheritance(c1.getId(), c3.getId(), c1.getId());
		i1.setId("test_i1");
		Inheritance i2 = new Inheritance(c1.getId(), c2.getId(), c1.getId());
		i2.setId("test_i2");
		Inheritance i3 = new Inheritance(c2.getId(), c4.getId(), c2.getId());
		i3.setId("test_i3");
		Inheritance i4 = new Inheritance(c2.getId(), c5.getId(), c2.getId());
		i4.setId("test_i4");
//		Inheritance i5 = new Inheritance(c3.getId(), c3.getId(), c3.getId());
//		i5.setId("test_i5");
//		Inheritance i6 = new Inheritance(c6.getId(), c6.getId(), c6.getId());
//		i6.setId("test_i6");

		Inheritance i7 = new Inheritance(c5.getId(), c7.getId(), c5.getId());
		i7.setId("test_i7");
		Inheritance i8 = new Inheritance(c5.getId(), c8.getId(), c5.getId());
		i8.setId("test_i8");
		Inheritance i9 = new Inheritance(c4.getId(), c9.getId(), c4.getId());
		i9.setId("test_i9");

		if (!relationshipRepository.exists(i1.getId())) {
			relationshipRepository.save(i1);
		}
		if (!relationshipRepository.exists(i2.getId())) {
			relationshipRepository.save(i2);
		}
		if (!relationshipRepository.exists(i3.getId())) {
			relationshipRepository.save(i3);
		}
		if (!relationshipRepository.exists(i4.getId())) {
			relationshipRepository.save(i4);
		}
//		if (!relationshipRepository.exists(i5.getId())) {
//			relationshipRepository.save(i5);
//		}

		if (!relationshipRepository.exists(i7.getId())) {
			relationshipRepository.save(i7);
		}
		if (!relationshipRepository.exists(i8.getId())) {
			relationshipRepository.save(i8);
		}
		if (!relationshipRepository.exists(i9.getId())) {
			relationshipRepository.save(i9);
		}

		if (!classDefinitionRepository.exists(c1.getId())) {
			classDefinitionRepository.save(c1);
		}

		if (!classDefinitionRepository.exists(c2.getId())) {
			classDefinitionRepository.save(c2);
		}

		if (!classDefinitionRepository.exists(c3.getId())) {
			classDefinitionRepository.save(c3);
		}

		if (!classDefinitionRepository.exists(c4.getId())) {
			classDefinitionRepository.save(c4);
		}

		if (!classDefinitionRepository.exists(c5.getId())) {
			classDefinitionRepository.save(c5);
		}

		if (!classDefinitionRepository.exists(c6.getId())) {
			classDefinitionRepository.save(c6);
		}

		if (!classDefinitionRepository.exists(c7.getId())) {
			classDefinitionRepository.save(c7);
		}

		if (!classDefinitionRepository.exists(c8.getId())) {
			classDefinitionRepository.save(c8);
		}

		if (!classDefinitionRepository.exists(c9.getId())) {
			classDefinitionRepository.save(c9);
		}
	}

	private void addConfigurators() {
		Configurator c1 = new Configurator();
		c1.setName("Slot1");
		c1.setId("slot1");
		c1.setDate(new Date());

		c1.setRelationshipIds(new ArrayList<>());
		c1.getRelationshipIds().add("test_i1");
		c1.getRelationshipIds().add("test_i2");
		c1.getRelationshipIds().add("test_i3");
		c1.getRelationshipIds().add("test_i4");
//		c1.getRelationshipIds().add("test_i5");
//		c1.getRelationshipIds().add("test_i6");
		c1.getRelationshipIds().add("test_i7");
		c1.getRelationshipIds().add("test_i8");
		c1.getRelationshipIds().add("test_i9");

		c1.setClassDefinitionIds(new ArrayList<>());
		c1.getClassDefinitionIds().add("test1");
		c1.getClassDefinitionIds().add("test2");
		c1.getClassDefinitionIds().add("test3");
		c1.getClassDefinitionIds().add("test4");
		c1.getClassDefinitionIds().add("test5");
		c1.getClassDefinitionIds().add("test6");
		c1.getClassDefinitionIds().add("test7");
		c1.getClassDefinitionIds().add("test8");
		c1.getClassDefinitionIds().add("test9");

		Configurator c2 = new Configurator();
		c2.setName("Feuerwehr Konfiguration");
		c2.setId("slot2");
		c2.setDate(new Date());
		c2.setUserId("FFA");

		Configurator c3 = new Configurator();
		c3.setName("Musiverein Modell");
		c3.setId("slot3");

		c3.setDate(new Date());
		c3.setUserId("MVS");

		Configurator c4 = new Configurator();
		c4.setName("Slot4");
		c4.setId("slot4");

		c4.setDate(new Date());

		Configurator c5 = new Configurator();
		c5.setName("Slot5");
		c5.setId("slot5");
		c5.setDate(new Date());

		if (!configuratorRepository.exists(c1.getId())) {
			configuratorRepository.save(c1);
		}

		if (!configuratorRepository.exists(c2.getId())) {
			configuratorRepository.save(c2);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c3.getId())) {
			configuratorRepository.save(c3);
		}

		if (!configuratorRepository.exists(c4.getId())) {
			configuratorRepository.save(c4);
		}
		if (!configuratorRepository.exists(c5.getId())) {
			configuratorRepository.save(c5);
		}
	}

	private void addTestClassInstances() {
		TaskClassInstance ti1 = new TaskClassInstance();
		ti1.setId("ti1");
		ti1.setName("Shopping Elementaries");
		Volunteer volunteer = volunteerRepository.findByUsername("mweissenbek");
		HelpSeeker oerk = helpSeekerRepository.findByUsername("OERK");
		HelpSeeker mvs = helpSeekerRepository.findByUsername("MVS");
		HelpSeeker ffa = helpSeekerRepository.findByUsername("FFA");
		HelpSeeker efa = helpSeekerRepository.findByUsername("EFA");

		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (oerk != null) {
			ti1.setIssuerId(oerk.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));
		ti1.setInUserRepository(true);

		classInstanceRepository.save(ti1);

		ti1.setId("ti2");
		ti1.setName("Equipment Service");
		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (mvs != null) {
			ti1.setIssuerId(mvs.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));

		classInstanceRepository.save(ti1);

		ti1.setId("ti3");
		ti1.setName("Shopping Elementaries");
		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (efa != null) {
			ti1.setIssuerId(efa.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));

		classInstanceRepository.save(ti1);

		ti1.setId("ti4");
		ti1.setName("Shopping Elementaries");
		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (oerk != null) {
			ti1.setIssuerId(oerk.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));

		classInstanceRepository.save(ti1);

		ti1.setId("ti5");
		ti1.setName("Donation Collection");
		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (efa != null) {
			ti1.setIssuerId(efa.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));

		classInstanceRepository.save(ti1);

		ti1.setId("ti6");
		ti1.setName("Medical Care Transport");
		if (volunteer != null) {
			ti1.setUserId(volunteer.getId());
		}
		if (oerk != null) {
			ti1.setIssuerId(oerk.getId());
		}
		ti1.setTimestamp(new Date(System.currentTimeMillis()));

		classInstanceRepository.save(ti1);

		Feedback f1 = new Feedback();
		f1.setId("f1");
		f1.setName("Firetruck Driver Renewed");
		f1.setFeedbackType(FeedbackType.KUDOS);
		if (volunteer != null) {
			f1.setUserId(volunteer.getId());
		}
		if (oerk != null) {
			f1.setIssuerId(oerk.getId());
		}
		f1.setTimestamp(new Date(System.currentTimeMillis()));
		f1.setFeedbackValue(1);
		f1.setInUserRepository(false);

		feedbackRepository.save(f1);

		f1.setId("f2");
		f1.setName("Yearly Feedback");
		f1.setFeedbackType(FeedbackType.STARRATING);
		if (volunteer != null) {
			f1.setUserId(volunteer.getId());
		}
		if (ffa != null) {
			f1.setIssuerId(ffa.getId());
		}
		f1.setTimestamp(new Date(System.currentTimeMillis()));
		f1.setFeedbackValue(5);

		feedbackRepository.save(f1);

		CompetenceClassInstance ci1 = new CompetenceClassInstance();
		ci1.setId("ci1");
		ci1.setName("Diligence");
		if (volunteer != null) {
			ci1.setUserId(volunteer.getId());
		}
		if (mvs != null) {
			ci1.setIssuerId(mvs.getId());
		}
		ci1.setTimestamp(new Date(System.currentTimeMillis()));
		ci1.setInUserRepository(true);

		classInstanceRepository.save(ci1);

		ci1.setId("ci2");
		ci1.setName("Teamwork");
		if (volunteer != null) {
			ci1.setUserId(volunteer.getId());
		}
		if (ffa != null) {
			ci1.setIssuerId(ffa.getId());
		}
		ci1.setTimestamp(new Date(System.currentTimeMillis()));
		classInstanceRepository.save(ci1);

		ci1.setId("ci3");
		ci1.setName("Communication Skills");
		if (volunteer != null) {
			ci1.setUserId(volunteer.getId());
		}
		if (efa != null) {
			ci1.setIssuerId(efa.getId());
		}
		ci1.setTimestamp(new Date(System.currentTimeMillis()));
		classInstanceRepository.save(ci1);

		ci1.setId("ci4");
		ci1.setName("Project Management");
		if (volunteer != null) {
			ci1.setUserId(volunteer.getId());
		}
		if (ffa != null) {
			ci1.setIssuerId(ffa.getId());
		}
		ci1.setTimestamp(new Date(System.currentTimeMillis()));
		classInstanceRepository.save(ci1);

		ci1.setId("ci5");
		ci1.setName("Firetruck Driver");
		if (volunteer != null) {
			ci1.setUserId(volunteer.getId());
		}
		if (ffa != null) {
			ci1.setIssuerId(ffa.getId());
		}
		ci1.setTimestamp(new Date(System.currentTimeMillis()));
		ci1.setInUserRepository(false);
		classInstanceRepository.save(ci1);
	}

	private void addStandardPropertyDefinitions() {
		StandardPropertyDefinitions sp = new StandardPropertyDefinitions();
		List<PropertyDefinition<Object>> props = sp.getAll();
//		List<PropertyDefinition<T>> props = sp.getAllFlexProdProperties();

		for (PropertyDefinition p : props) {
			if (!propertyDefinitionRepository.exists(p.getId())) {
				propertyDefinitionRepository.save(p);
			}
		}

	}

	private void addFlexProdConfigClassesProducer() {
		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		List<Relationship> relationships = new ArrayList<Relationship>();

		ClassDefinition technischeBeschreibung = new ClassDefinition();
		technischeBeschreibung.setId("technische_beschreibung_producer");
		technischeBeschreibung.setName("Technische\nBeschreibung");
		technischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		technischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(technischeBeschreibung);

		ClassDefinition ofen = new ClassDefinition();
		ofen.setId("ofen_producer");
		ofen.setName("Ofen");
		ofen.setProperties(new ArrayList<ClassProperty<Object>>());
		ofen.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofen);

		Inheritance i1 = new Inheritance(technischeBeschreibung.getId(), ofen.getId(), technischeBeschreibung.getId());
		i1.setId("i1_producer");
		relationships.add(i1);

		ClassDefinition ofenTechnischeEigenschaften = new ClassDefinition();
		ofenTechnischeEigenschaften.setId("ofenTechnischeEigenschaften_producer");
		ofenTechnischeEigenschaften.setName("Technische\nEigenschaften");
		ofenTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofenTechnischeEigenschaften);

		Inheritance i11 = new Inheritance(ofen.getId(), ofenTechnischeEigenschaften.getId(), ofen.getId());
		i11.setId("i11_producer");

		relationships.add(i11);

		ClassDefinition oteAllgemein = new ClassDefinition();
		oteAllgemein.setId("oteAllgemein_producer");
		oteAllgemein.setName("Allgemein");
		oteAllgemein.setProperties(new ArrayList<ClassProperty<Object>>());
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxgluehtemperatur")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verfuegbaresschutzgas")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bauart")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("temperaturhomogenitaet")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalztesmaterialzulaessig")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalztesmaterialzulaessig")));
		oteAllgemein.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteAllgemein);

		Inheritance i111 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteAllgemein.getId(),
				ofenTechnischeEigenschaften.getId());
		i111.setId("i111_producer");
		relationships.add(i111);

		ClassDefinition oteMoeglicheVorbehandlung = new ClassDefinition();
		oteMoeglicheVorbehandlung.setId("oteMoeglicheVorbehandlung_producer");
		oteMoeglicheVorbehandlung.setName("Mögliche\nVorbehandlung");
		oteMoeglicheVorbehandlung.setProperties(new ArrayList<ClassProperty<Object>>());
		oteMoeglicheVorbehandlung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bundentfetten")));
		oteMoeglicheVorbehandlung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteMoeglicheVorbehandlung);

		Inheritance i112 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteMoeglicheVorbehandlung.getId(),
				ofenTechnischeEigenschaften.getId());
		i112.setId("i112_producer");
		relationships.add(i112);

		ClassDefinition oteChargierhilfe = new ClassDefinition();
		oteChargierhilfe.setId("oteChargierhilfe_producer");
		oteChargierhilfe.setName("Chargierhilfe");
		oteChargierhilfe.setProperties(new ArrayList<ClassProperty<Object>>());
		oteChargierhilfe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteChargierhilfe);

		Inheritance i113 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteChargierhilfe.getId(),
				ofenTechnischeEigenschaften.getId());
		i113.setId("i113_producer");
		relationships.add(i113);

		ClassDefinition otecKonvektoren = new ClassDefinition();
		otecKonvektoren.setId("otecKonvektoren_producer");
		otecKonvektoren.setName("Konvektoren");
		otecKonvektoren.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKonvektoren.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKonvektoren);

		Inheritance i1131 = new Inheritance(oteChargierhilfe.getId(), otecKonvektoren.getId(),
				oteChargierhilfe.getId());
		i1131.setId("i1131_producer");
		relationships.add(i1131);

		ClassDefinition otecTragerahmen = new ClassDefinition();
		otecTragerahmen.setId("otecTragerahmen_producer");
		otecTragerahmen.setName("Tragerahmen");
		otecTragerahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecTragerahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecTragerahmen);

		Inheritance i1132 = new Inheritance(oteChargierhilfe.getId(), otecTragerahmen.getId(),
				oteChargierhilfe.getId());
		i1132.setId("i1132_producer");
		relationships.add(i1132);

		ClassDefinition otecZwischenrahmen = new ClassDefinition();
		otecZwischenrahmen.setId("otecZwischenrahmen_producer");
		otecZwischenrahmen.setName("Zwischenrahmen");
		otecZwischenrahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecZwischenrahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecZwischenrahmen);

		Inheritance i1133 = new Inheritance(oteChargierhilfe.getId(), otecZwischenrahmen.getId(),
				oteChargierhilfe.getId());
		i1133.setId("i1133_producer");
		relationships.add(i1133);

		ClassDefinition otecKronenstoecke = new ClassDefinition();
		otecKronenstoecke.setId("otecKronenstoecke_producer");
		otecKronenstoecke.setName("Kronenstöcke");
		otecKronenstoecke.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKronenstoecke.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKronenstoecke);

		Inheritance i1134 = new Inheritance(oteChargierhilfe.getId(), otecKronenstoecke.getId(),
				oteChargierhilfe.getId());
		i1134.setId("i1134_producer");
		relationships.add(i1134);

		ClassDefinition otecChargierkoerbe = new ClassDefinition();
		otecChargierkoerbe.setId("otecChargierkoerbe_producer");
		otecChargierkoerbe.setName("Chargierkörbe");
		otecChargierkoerbe.setProperties(new ArrayList<ClassProperty<Object>>());

		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecChargierkoerbe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecChargierkoerbe);

		Inheritance i1135 = new Inheritance(oteChargierhilfe.getId(), otecChargierkoerbe.getId(),
				oteChargierhilfe.getId());
		i1135.setId("i1135_producer");
		relationships.add(i1135);

		ClassDefinition ofenBetrieblicheEigenschaften = new ClassDefinition();
		ofenBetrieblicheEigenschaften.setId("ofenBetrieblicheEigenschaften_producer");
		ofenBetrieblicheEigenschaften.setName("Betriebliche\nEigenschaften");
		ofenBetrieblicheEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gluehzeit")));
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("durchsatz")));
		ofenBetrieblicheEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenBetrieblicheEigenschaften);

		Inheritance i12 = new Inheritance(ofen.getId(), ofenBetrieblicheEigenschaften.getId(), ofen.getId());
		i12.setId("i12_producer");
		relationships.add(i12);

		ClassDefinition ofenGeometrischeEigenschaften = new ClassDefinition();
		ofenGeometrischeEigenschaften.setId("ofenGeometrischeEigenschaften_producer");
		ofenGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		ofenGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenGeometrischeEigenschaften);

		Inheritance i13 = new Inheritance(ofen.getId(), ofenGeometrischeEigenschaften.getId(), ofen.getId());
		i13.setId("i13_producer");
		relationships.add(i13);

		ClassDefinition ogeBaugroesse = new ClassDefinition();
		ogeBaugroesse.setId("ogeBaugroesse_producer");
		ogeBaugroesse.setName("Baugröße");
		ogeBaugroesse.setProperties(new ArrayList<ClassProperty<Object>>());
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("moeglicheinnendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxaussendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxchargierhoehe")));
		ogeBaugroesse.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ogeBaugroesse);

		Inheritance i131 = new Inheritance(ofenGeometrischeEigenschaften.getId(), ogeBaugroesse.getId(),
				ofenGeometrischeEigenschaften.getId());
		i131.setId("i131_producer");
		relationships.add(i131);

		ClassDefinition ofenQualitativeEigenschaften = new ClassDefinition();
		ofenQualitativeEigenschaften.setId("ofenQualitativeEigenschaften_producer");
		ofenQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		ofenQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenQualitativeEigenschaften);

		Inheritance i14 = new Inheritance(ofen.getId(), ofenQualitativeEigenschaften.getId(), ofen.getId());
		i14.setId("i14_producer");
		relationships.add(i14);

		ClassDefinition oqeQualitätsnormen = new ClassDefinition();
		oqeQualitätsnormen.setId("oqeQualitätsnormen_producer");
		oqeQualitätsnormen.setName("Qualitätsnormen");
		oqeQualitätsnormen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("cqi9")));
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("tus")));
		oqeQualitätsnormen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oqeQualitätsnormen);

		Inheritance i141 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeQualitätsnormen.getId(),
				ofenQualitativeEigenschaften.getId());
		i141.setId("i141_producer");
		relationships.add(i141);

		ClassDefinition oqeWartungen = new ClassDefinition();
		oqeWartungen.setId("oqeWartungen_producer");
		oqeWartungen.setName("Wartungen");
		oqeWartungen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("letztewartung")));
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("wartungsintervall")));
		oqeWartungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		
		classDefinitions.add(oqeWartungen);

		Inheritance i142 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeWartungen.getId(),
				ofenQualitativeEigenschaften.getId());
		i142.setId("i142_producer");
		relationships.add(i142);

		// =================================================================================

		ClassDefinition input = new ClassDefinition();
		input.setId("input_producer");
		input.setName("Input");
		input.setProperties(new ArrayList<ClassProperty<Object>>());
		input.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(input);

		Inheritance i2 = new Inheritance(technischeBeschreibung.getId(), input.getId(), technischeBeschreibung.getId());
		i2.setId("i2_producer");
		relationships.add(i2);

		ClassDefinition inGeometrischeEigenschaften = new ClassDefinition();
		inGeometrischeEigenschaften.setId("inGeometrischeEigenschaften_producer");
		inGeometrischeEigenschaften.setName("Geometrsiche\nEigenschaften");
		inGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inGeometrischeEigenschaften);

		Inheritance i21 = new Inheritance(input.getId(), inGeometrischeEigenschaften.getId(), input.getId());
		i21.setId("i21_producer");
		relationships.add(i21);

		ClassDefinition ingeBundabmessungen = new ClassDefinition();
		ingeBundabmessungen.setId("ingeBundabmessungen_producer");
		ingeBundabmessungen.setName("Bundabmessungen");
		ingeBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		ingeBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ingeBundabmessungen);

		Inheritance i211 = new Inheritance(inGeometrischeEigenschaften.getId(), ingeBundabmessungen.getId(),
				inGeometrischeEigenschaften.getId());
		i211.setId("i211_producer");
		relationships.add(i211);

		ClassDefinition inQualitativeEigenschaften = new ClassDefinition();
		inQualitativeEigenschaften.setId("inQualitativeEigenschaften_producer");
		inQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		inQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inQualitativeEigenschaften);

		Inheritance i22 = new Inheritance(input.getId(), inQualitativeEigenschaften.getId(), input.getId());
		i22.setId("i22_producer");
		relationships.add(i22);

		ClassDefinition inqeMaterialart = new ClassDefinition();
		inqeMaterialart.setId("inqeMaterialart_producer");
		inqeMaterialart.setName("Materialart");
		inqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		inqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inqeMaterialart);

		Inheritance i221 = new Inheritance(inQualitativeEigenschaften.getId(), inqeMaterialart.getId(),
				inQualitativeEigenschaften.getId());
		i221.setId("i221_producer");
		relationships.add(i221);

		// =================================================================================

		ClassDefinition output = new ClassDefinition();
		output.setId("output_producer");
		output.setName("Output");
		output.setProperties(new ArrayList<ClassProperty<Object>>());
		output.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(output);

		Inheritance i3 = new Inheritance(technischeBeschreibung.getId(), output.getId(),
				technischeBeschreibung.getId());
		i3.setId("i3_producer");
		relationships.add(i3);

		ClassDefinition outTechnischeEigenschaften = new ClassDefinition();
		outTechnischeEigenschaften.setId("outTechnischeEigenschaften");
		outTechnischeEigenschaften.setName("Technische\nEigenschaften");
		outTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outTechnischeEigenschaften);

		Inheritance i31 = new Inheritance(output.getId(), outTechnischeEigenschaften.getId(), output.getId());
		i31.setId("i31_producer");
		relationships.add(i31);

		ClassDefinition outteMechanischeEigenschaften = new ClassDefinition();
		outteMechanischeEigenschaften.setId("outteMechanischeEigenschaften_producer");
		outteMechanischeEigenschaften.setName("Mechanische\nEigenschaften");
		outteMechanischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("streckgrenze")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zugfestigkeit")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("dehnung")));
		outteMechanischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteMechanischeEigenschaften);

		Inheritance i311 = new Inheritance(outTechnischeEigenschaften.getId(), outteMechanischeEigenschaften.getId(),
				outTechnischeEigenschaften.getId());
		i311.setId("i311_producer");
		relationships.add(i311);

		ClassDefinition outteGefuege = new ClassDefinition();
		outteGefuege.setId("outteGefuege_producer");
		outteGefuege.setName("Gefüge");
		outteGefuege.setProperties(new ArrayList<ClassProperty<Object>>());
		outteGefuege.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gefuege")));
		outteGefuege.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteGefuege);

		Inheritance i312 = new Inheritance(outTechnischeEigenschaften.getId(), outteGefuege.getId(),
				outTechnischeEigenschaften.getId());
		i312.setId("i312_producer");
		relationships.add(i312);

		ClassDefinition outGeometrischeEigenschaften = new ClassDefinition();
		outGeometrischeEigenschaften.setId("outGeometrischeEigenschaften_producer");
		outGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		outGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outGeometrischeEigenschaften);

		Inheritance i32 = new Inheritance(output.getId(), outGeometrischeEigenschaften.getId(), output.getId());
		i32.setId("i32_producer");
		relationships.add(i32);

		ClassDefinition outgeMoeglicheBundabmessungen = new ClassDefinition();
		outgeMoeglicheBundabmessungen.setId("outgeMoeglicheBundabmessungen_producer");
		outgeMoeglicheBundabmessungen.setName("Mögliche\nBundabmessungen");
		outgeMoeglicheBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		outgeMoeglicheBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outgeMoeglicheBundabmessungen);

		Inheritance i321 = new Inheritance(outGeometrischeEigenschaften.getId(), outgeMoeglicheBundabmessungen.getId(),
				outGeometrischeEigenschaften.getId());
		i321.setId("i321_producer");
		relationships.add(i321);

		ClassDefinition outQualitativeEigenschaften = new ClassDefinition();
		outQualitativeEigenschaften.setId("outQualitativeEigenschaften_producer");
		outQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		outQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outQualitativeEigenschaften);

		Inheritance i33 = new Inheritance(output.getId(), outQualitativeEigenschaften.getId(), output.getId());
		i33.setId("i33_producer");
		relationships.add(i33);

		ClassDefinition outqeMaterialart = new ClassDefinition();
		outqeMaterialart.setId("outqeMaterialart_producer");
		outqeMaterialart.setName("Materialart");
		outqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		outqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outqeMaterialart);

		Inheritance i331 = new Inheritance(outQualitativeEigenschaften.getId(), outqeMaterialart.getId(),
				outQualitativeEigenschaften.getId());
		i331.setId("i331_producer");
		relationships.add(i331);

		// =========================================================

		ClassDefinition logistischeBeschreibung = new ClassDefinition();
		logistischeBeschreibung.setId("logistischeBeschreibung_producer");
		logistischeBeschreibung.setName("Logistische\nBeschreibung");
		logistischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("materialbereitgestellt")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferort")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verpackung")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("transportart")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("menge")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferdatum")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("incoterms")));
		logistischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(logistischeBeschreibung);

		ClassDefinition preislicheBeschreibung = new ClassDefinition();
		preislicheBeschreibung.setId("preislicheBeschreibung_producer");
		preislicheBeschreibung.setName("Preisliche\nBeschreibung");
		preislicheBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		preislicheBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zahlungsbedingungen")));
		preislicheBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(preislicheBeschreibung);

		ClassDefinition root = new ClassDefinition();
		root.setId("root_producer");
		root.setName("Haubenofen");
		root.setProperties(new ArrayList<ClassProperty<Object>>());
		root.setRoot(true);
		root.setClassArchetype(ClassArchetype.ROOT);
		classDefinitions.add(root);

		Inheritance r1 = new Inheritance(root.getId(), technischeBeschreibung.getId(), root.getId());
		r1.setId("r1_producer");
		Inheritance r2 = new Inheritance(root.getId(), logistischeBeschreibung.getId(), root.getId());
		r2.setId("r2_producer");
		Inheritance r3 = new Inheritance(root.getId(), preislicheBeschreibung.getId(), root.getId());
		r3.setId("r3_producer");

		relationships.add(r1);
		relationships.add(r2);
		relationships.add(r3);
		
		Configurator configurator = new Configurator();
		configurator.setId("slot1");
		configurator.setName("Produzent");
		configurator.setRelationshipIds(new ArrayList<String>());
		configurator.setClassDefinitionIds(new ArrayList<String>());

		for (Relationship r : relationships) {
			if (!relationshipRepository.exists(r.getId())) {
				relationshipRepository.save(r);
			}
			
			configurator.getRelationshipIds().add(r.getId());
		}

		for (ClassDefinition cd : classDefinitions) {
			if (!classDefinitionRepository.exists(cd.getId())) {
				classDefinitionRepository.save(cd);
			}
			
			configurator.getClassDefinitionIds().add(cd.getId());
		}
		
		configuratorRepository.save(configurator);
	}
	
	private void addFlexProdConfigClassesConsumer() {
		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		List<Relationship> relationships = new ArrayList<Relationship>();

		ClassDefinition technischeBeschreibung = new ClassDefinition();
		technischeBeschreibung.setId("technische_beschreibung_consumer");
		technischeBeschreibung.setName("Technische\nBeschreibung");
		technischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		technischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(technischeBeschreibung);

		ClassDefinition ofen = new ClassDefinition();
		ofen.setId("ofen_consumer");
		ofen.setName("Ofen");
		ofen.setProperties(new ArrayList<ClassProperty<Object>>());
		ofen.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofen);

		Inheritance i1 = new Inheritance(technischeBeschreibung.getId(), ofen.getId(), technischeBeschreibung.getId());
		i1.setId("i1_consumer");
		relationships.add(i1);

		ClassDefinition ofenTechnischeEigenschaften = new ClassDefinition();
		ofenTechnischeEigenschaften.setId("ofenTechnischeEigenschaften_consumer");
		ofenTechnischeEigenschaften.setName("Technische\nEigenschaften");
		ofenTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(ofenTechnischeEigenschaften);

		Inheritance i11 = new Inheritance(ofen.getId(), ofenTechnischeEigenschaften.getId(), ofen.getId());
		i11.setId("i11_consumer");

		relationships.add(i11);

		ClassDefinition oteAllgemein = new ClassDefinition();
		oteAllgemein.setId("oteAllgemein_consumer");
		oteAllgemein.setName("Allgemein");
		oteAllgemein.setProperties(new ArrayList<ClassProperty<Object>>());
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxgluehtemperatur")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verfuegbaresschutzgas")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bauart")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("temperaturhomogenitaet")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalztesmaterialzulaessig")));
		oteAllgemein.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalztesmaterialzulaessig")));
		oteAllgemein.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteAllgemein);

		Inheritance i111 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteAllgemein.getId(),
				ofenTechnischeEigenschaften.getId());
		i111.setId("i111_consumer");
		relationships.add(i111);

		ClassDefinition oteMoeglicheVorbehandlung = new ClassDefinition();
		oteMoeglicheVorbehandlung.setId("oteMoeglicheVorbehandlung_consumer");
		oteMoeglicheVorbehandlung.setName("Mögliche\nVorbehandlung");
		oteMoeglicheVorbehandlung.setProperties(new ArrayList<ClassProperty<Object>>());
		oteMoeglicheVorbehandlung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bundentfetten")));
		oteMoeglicheVorbehandlung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteMoeglicheVorbehandlung);

		Inheritance i112 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteMoeglicheVorbehandlung.getId(),
				ofenTechnischeEigenschaften.getId());
		i112.setId("i112_consumer");
		relationships.add(i112);

		ClassDefinition oteChargierhilfe = new ClassDefinition();
		oteChargierhilfe.setId("oteChargierhilfe_consumer");
		oteChargierhilfe.setName("Chargierhilfe");
		oteChargierhilfe.setProperties(new ArrayList<ClassProperty<Object>>());
		oteChargierhilfe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oteChargierhilfe);

		Inheritance i113 = new Inheritance(ofenTechnischeEigenschaften.getId(), oteChargierhilfe.getId(),
				ofenTechnischeEigenschaften.getId());
		i113.setId("i113_consumer");
		relationships.add(i113);

		ClassDefinition otecKonvektoren = new ClassDefinition();
		otecKonvektoren.setId("otecKonvektoren_consumer");
		otecKonvektoren.setName("Konvektoren");
		otecKonvektoren.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKonvektoren.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKonvektoren.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKonvektoren);

		Inheritance i1131 = new Inheritance(oteChargierhilfe.getId(), otecKonvektoren.getId(),
				oteChargierhilfe.getId());
		i1131.setId("i1131_consumer");
		relationships.add(i1131);

		ClassDefinition otecTragerahmen = new ClassDefinition();
		otecTragerahmen.setId("otecTragerahmen_consumer");
		otecTragerahmen.setName("Tragerahmen");
		otecTragerahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecTragerahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecTragerahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecTragerahmen);

		Inheritance i1132 = new Inheritance(oteChargierhilfe.getId(), otecTragerahmen.getId(),
				oteChargierhilfe.getId());
		i1132.setId("i1132_consumer");
		relationships.add(i1132);

		ClassDefinition otecZwischenrahmen = new ClassDefinition();
		otecZwischenrahmen.setId("otecZwischenrahmen_consumer");
		otecZwischenrahmen.setName("Zwischenrahmen");
		otecZwischenrahmen.setProperties(new ArrayList<ClassProperty<Object>>());
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecZwischenrahmen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecZwischenrahmen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecZwischenrahmen);

		Inheritance i1133 = new Inheritance(oteChargierhilfe.getId(), otecZwischenrahmen.getId(),
				oteChargierhilfe.getId());
		i1133.setId("i1133_consumer");
		relationships.add(i1133);

		ClassDefinition otecKronenstoecke = new ClassDefinition();
		otecKronenstoecke.setId("otecKronenstoecke_consumer");
		otecKronenstoecke.setName("Kronenstöcke");
		otecKronenstoecke.setProperties(new ArrayList<ClassProperty<Object>>());
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecKronenstoecke.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecKronenstoecke.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecKronenstoecke);

		Inheritance i1134 = new Inheritance(oteChargierhilfe.getId(), otecKronenstoecke.getId(),
				oteChargierhilfe.getId());
		i1134.setId("i1134_consumer");
		relationships.add(i1134);

		ClassDefinition otecChargierkoerbe = new ClassDefinition();
		otecChargierkoerbe.setId("otecChargierkoerbe_consumer");
		otecChargierkoerbe.setName("Chargierkörbe");
		otecChargierkoerbe.setProperties(new ArrayList<ClassProperty<Object>>());

		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		otecChargierkoerbe.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("hoehe")));
		otecChargierkoerbe.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(otecChargierkoerbe);

		Inheritance i1135 = new Inheritance(oteChargierhilfe.getId(), otecChargierkoerbe.getId(),
				oteChargierhilfe.getId());
		i1135.setId("i1135_consumer");
		relationships.add(i1135);

		ClassDefinition ofenBetrieblicheEigenschaften = new ClassDefinition();
		ofenBetrieblicheEigenschaften.setId("ofenBetrieblicheEigenschaften_consumer");
		ofenBetrieblicheEigenschaften.setName("Betriebliche\nEigenschaften");
		ofenBetrieblicheEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gluehzeit")));
		ofenBetrieblicheEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("durchsatz")));
		ofenBetrieblicheEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenBetrieblicheEigenschaften);

		Inheritance i12 = new Inheritance(ofen.getId(), ofenBetrieblicheEigenschaften.getId(), ofen.getId());
		i12.setId("i12_consumer");
		relationships.add(i12);

		ClassDefinition ofenGeometrischeEigenschaften = new ClassDefinition();
		ofenGeometrischeEigenschaften.setId("ofenGeometrischeEigenschaften_consumer");
		ofenGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		ofenGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenGeometrischeEigenschaften);

		Inheritance i13 = new Inheritance(ofen.getId(), ofenGeometrischeEigenschaften.getId(), ofen.getId());
		i13.setId("i13_consumer");
		relationships.add(i13);

		ClassDefinition ogeBaugroesse = new ClassDefinition();
		ogeBaugroesse.setId("ogeBaugroesse_consumer");
		ogeBaugroesse.setName("Baugröße");
		ogeBaugroesse.setProperties(new ArrayList<ClassProperty<Object>>());
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("moeglicheinnendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxaussendurchmesser")));
		ogeBaugroesse.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("maxchargierhoehe")));
		ogeBaugroesse.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ogeBaugroesse);

		Inheritance i131 = new Inheritance(ofenGeometrischeEigenschaften.getId(), ogeBaugroesse.getId(),
				ofenGeometrischeEigenschaften.getId());
		i131.setId("i131_consumer");
		relationships.add(i131);

		ClassDefinition ofenQualitativeEigenschaften = new ClassDefinition();
		ofenQualitativeEigenschaften.setId("ofenQualitativeEigenschaften_consumer");
		ofenQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		ofenQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		ofenQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ofenQualitativeEigenschaften);

		Inheritance i14 = new Inheritance(ofen.getId(), ofenQualitativeEigenschaften.getId(), ofen.getId());
		i14.setId("i14_consumer");
		relationships.add(i14);

		ClassDefinition oqeQualitätsnormen = new ClassDefinition();
		oqeQualitätsnormen.setId("oqeQualitätsnormen_consumer");
		oqeQualitätsnormen.setName("Qualitätsnormen");
		oqeQualitätsnormen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("cqi9")));
		oqeQualitätsnormen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("tus")));
		oqeQualitätsnormen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(oqeQualitätsnormen);

		Inheritance i141 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeQualitätsnormen.getId(),
				ofenQualitativeEigenschaften.getId());
		i141.setId("i141_consumer");
		relationships.add(i141);

		ClassDefinition oqeWartungen = new ClassDefinition();
		oqeWartungen.setId("oqeWartungen_consumer");
		oqeWartungen.setName("Wartungen");
		oqeWartungen.setProperties(new ArrayList<ClassProperty<Object>>());
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("letztewartung")));
		oqeWartungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("wartungsintervall")));
		oqeWartungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		
		classDefinitions.add(oqeWartungen);

		Inheritance i142 = new Inheritance(ofenQualitativeEigenschaften.getId(), oqeWartungen.getId(),
				ofenQualitativeEigenschaften.getId());
		i142.setId("i142_consumer");
		relationships.add(i142);

		// =================================================================================

		ClassDefinition input = new ClassDefinition();
		input.setId("input_consumer");
		input.setName("Input");
		input.setProperties(new ArrayList<ClassProperty<Object>>());
		input.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(input);

		Inheritance i2 = new Inheritance(technischeBeschreibung.getId(), input.getId(), technischeBeschreibung.getId());
		i2.setId("i2_consumer");
		relationships.add(i2);

		ClassDefinition inGeometrischeEigenschaften = new ClassDefinition();
		inGeometrischeEigenschaften.setId("inGeometrischeEigenschaften_consumer");
		inGeometrischeEigenschaften.setName("Geometrsiche\nEigenschaften");
		inGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inGeometrischeEigenschaften);

		Inheritance i21 = new Inheritance(input.getId(), inGeometrischeEigenschaften.getId(), input.getId());
		i21.setId("i21_consumer");
		relationships.add(i21);

		ClassDefinition ingeBundabmessungen = new ClassDefinition();
		ingeBundabmessungen.setId("ingeBundabmessungen_consumer");
		ingeBundabmessungen.setName("Bundabmessungen");
		ingeBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		ingeBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		ingeBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(ingeBundabmessungen);

		Inheritance i211 = new Inheritance(inGeometrischeEigenschaften.getId(), ingeBundabmessungen.getId(),
				inGeometrischeEigenschaften.getId());
		i211.setId("i211_consumer");
		relationships.add(i211);

		ClassDefinition inQualitativeEigenschaften = new ClassDefinition();
		inQualitativeEigenschaften.setId("inQualitativeEigenschaften_consumer");
		inQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		inQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		inQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inQualitativeEigenschaften);

		Inheritance i22 = new Inheritance(input.getId(), inQualitativeEigenschaften.getId(), input.getId());
		i22.setId("i22_consumer");
		relationships.add(i22);

		ClassDefinition inqeMaterialart = new ClassDefinition();
		inqeMaterialart.setId("inqeMaterialart_consumer");
		inqeMaterialart.setName("Materialart");
		inqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		inqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		inqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(inqeMaterialart);

		Inheritance i221 = new Inheritance(inQualitativeEigenschaften.getId(), inqeMaterialart.getId(),
				inQualitativeEigenschaften.getId());
		i221.setId("i221_consumer");
		relationships.add(i221);

		// =================================================================================

		ClassDefinition output = new ClassDefinition();
		output.setId("output_consumer");
		output.setName("Output");
		output.setProperties(new ArrayList<ClassProperty<Object>>());
		output.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(output);

		Inheritance i3 = new Inheritance(technischeBeschreibung.getId(), output.getId(),
				technischeBeschreibung.getId());
		i3.setId("i3_consumer");
		relationships.add(i3);

		ClassDefinition outTechnischeEigenschaften = new ClassDefinition();
		outTechnischeEigenschaften.setId("outTechnischeEigenschaften_consumer");
		outTechnischeEigenschaften.setName("Technische\nEigenschaften");
		outTechnischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outTechnischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outTechnischeEigenschaften);

		Inheritance i31 = new Inheritance(output.getId(), outTechnischeEigenschaften.getId(), output.getId());
		i31.setId("i31_consumer");
		relationships.add(i31);

		ClassDefinition outteMechanischeEigenschaften = new ClassDefinition();
		outteMechanischeEigenschaften.setId("outteMechanischeEigenschaften_consumer");
		outteMechanischeEigenschaften.setName("Mechanische\nEigenschaften");
		outteMechanischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("streckgrenze")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zugfestigkeit")));
		outteMechanischeEigenschaften.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("dehnung")));
		outteMechanischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteMechanischeEigenschaften);

		Inheritance i311 = new Inheritance(outTechnischeEigenschaften.getId(), outteMechanischeEigenschaften.getId(),
				outTechnischeEigenschaften.getId());
		i311.setId("i311_consumer");
		relationships.add(i311);

		ClassDefinition outteGefuege = new ClassDefinition();
		outteGefuege.setId("outteGefuege_consumer");
		outteGefuege.setName("Gefüge");
		outteGefuege.setProperties(new ArrayList<ClassProperty<Object>>());
		outteGefuege.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("gefuege")));
		outteGefuege.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outteGefuege);

		Inheritance i312 = new Inheritance(outTechnischeEigenschaften.getId(), outteGefuege.getId(),
				outTechnischeEigenschaften.getId());
		i312.setId("i312_consumer");
		relationships.add(i312);

		ClassDefinition outGeometrischeEigenschaften = new ClassDefinition();
		outGeometrischeEigenschaften.setId("outGeometrischeEigenschaften_consumer");
		outGeometrischeEigenschaften.setName("Geometrische\nEigenschaften");
		outGeometrischeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outGeometrischeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outGeometrischeEigenschaften);

		Inheritance i32 = new Inheritance(output.getId(), outGeometrischeEigenschaften.getId(), output.getId());
		i32.setId("i32_consumer");
		relationships.add(i32);

		ClassDefinition outgeMoeglicheBundabmessungen = new ClassDefinition();
		outgeMoeglicheBundabmessungen.setId("outgeMoeglicheBundabmessungen_consumer");
		outgeMoeglicheBundabmessungen.setName("Mögliche\nBundabmessungen");
		outgeMoeglicheBundabmessungen.setProperties(new ArrayList<ClassProperty<Object>>());
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("aussendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("innendurchmesser")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandbreite")));
		outgeMoeglicheBundabmessungen.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("bandstaerke")));
		outgeMoeglicheBundabmessungen.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outgeMoeglicheBundabmessungen);

		Inheritance i321 = new Inheritance(outGeometrischeEigenschaften.getId(), outgeMoeglicheBundabmessungen.getId(),
				outGeometrischeEigenschaften.getId());
		i321.setId("i321_consumer");
		relationships.add(i321);

		ClassDefinition outQualitativeEigenschaften = new ClassDefinition();
		outQualitativeEigenschaften.setId("outQualitativeEigenschaften_consumer");
		outQualitativeEigenschaften.setName("Qualitative\nEigenschaften");
		outQualitativeEigenschaften.setProperties(new ArrayList<ClassProperty<Object>>());
		outQualitativeEigenschaften.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outQualitativeEigenschaften);

		Inheritance i33 = new Inheritance(output.getId(), outQualitativeEigenschaften.getId(), output.getId());
		i33.setId("i33_consumer");
		relationships.add(i33);

		ClassDefinition outqeMaterialart = new ClassDefinition();
		outqeMaterialart.setId("outqeMaterialart_consumer");
		outqeMaterialart.setName("Materialart");
		outqeMaterialart.setProperties(new ArrayList<ClassProperty<Object>>());
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("warmgewalzt")));
		outqeMaterialart.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("kaltgewalzt")));
		outqeMaterialart.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(outqeMaterialart);

		Inheritance i331 = new Inheritance(outQualitativeEigenschaften.getId(), outqeMaterialart.getId(),
				outQualitativeEigenschaften.getId());
		i331.setId("i331_consumer");
		relationships.add(i331);

		// =========================================================

		ClassDefinition logistischeBeschreibung = new ClassDefinition();
		logistischeBeschreibung.setId("logistischeBeschreibung_consumer");
		logistischeBeschreibung.setName("Logistische\nBeschreibung");
		logistischeBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("materialbereitgestellt")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferort")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("verpackung")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("transportart")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("menge")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("lieferdatum")));
		logistischeBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("incoterms")));
		logistischeBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		
		classDefinitions.add(logistischeBeschreibung);

		ClassDefinition preislicheBeschreibung = new ClassDefinition();
		preislicheBeschreibung.setId("preislicheBeschreibung_consumer");
		preislicheBeschreibung.setName("Preisliche\nBeschreibung");
		preislicheBeschreibung.setProperties(new ArrayList<ClassProperty<Object>>());
		preislicheBeschreibung.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(propertyDefinitionRepository.findOne("zahlungsbedingungen")));
		preislicheBeschreibung.setClassArchetype(ClassArchetype.FLEXPROD);
		classDefinitions.add(preislicheBeschreibung);

		ClassDefinition root = new ClassDefinition();
		root.setId("root_consumer");
		root.setName("Haubenofen");
		root.setProperties(new ArrayList<ClassProperty<Object>>());
		root.setRoot(true);
		root.setClassArchetype(ClassArchetype.ROOT);
		classDefinitions.add(root);

		Inheritance r1 = new Inheritance(root.getId(), technischeBeschreibung.getId(), root.getId());
		r1.setId("r1_consumer");
		Inheritance r2 = new Inheritance(root.getId(), logistischeBeschreibung.getId(), root.getId());
		r2.setId("r2_consumer");
		Inheritance r3 = new Inheritance(root.getId(), preislicheBeschreibung.getId(), root.getId());
		r3.setId("r3_consumer");

		relationships.add(r1);
		relationships.add(r2);
		relationships.add(r3);
		
		Configurator configurator = new Configurator();
		configurator.setId("slot2");
		configurator.setName("Konsument");
		configurator.setRelationshipIds(new ArrayList<String>());
		configurator.setClassDefinitionIds(new ArrayList<String>());

		for (Relationship r : relationships) {
			if (!relationshipRepository.exists(r.getId())) {
				relationshipRepository.save(r);
			}
			
			configurator.getRelationshipIds().add(r.getId());
		}

		for (ClassDefinition cd : classDefinitions) {
			if (!classDefinitionRepository.exists(cd.getId())) {
				classDefinitionRepository.save(cd);
			}
			
			configurator.getClassDefinitionIds().add(cd.getId());
		}
		
		configuratorRepository.save(configurator);
	}
	
	
}
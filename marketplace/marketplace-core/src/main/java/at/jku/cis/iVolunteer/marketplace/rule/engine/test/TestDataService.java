//package at.jku.cis.iVolunteer.marketplace.rule.engine.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
//import at.jku.cis.iVolunteer.marketplace._mapper.property.ClassPropertyToPropertyInstanceMapper;
//import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
//import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
//import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
//import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
//import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;
//import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleEngineMapper;
//import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
//import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
//
//@Service
//public class TestDataService {
//
//	@Autowired private ClassDefinitionRepository classDefinitionRepository;
//	@Autowired private ClassInstanceRepository classInstanceRepository;
//	@Autowired private RelationshipRepository relationshipRepository;
//	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
//	@Autowired private ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;
//	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
//	@Autowired private MarketplaceService marketplaceService;
//	@Autowired private ContainerRuleEntryRepository containerRuleEntryRepository;
//	@Autowired private RuleService ruleService;
//	@Autowired private ClassDefinitionService classDefinitionService;
//	@Autowired private ClassPropertyService classPropertyService;
//	@Autowired private ClassInstanceService classInstanceService;
//	@Autowired private RuleEngineMapper ruleEngineMapper;
//
//	@Autowired private CoreTenantRestClient coreTenantRestClient;
//	@Autowired private UserRepository userRepository;
////	@Autowired private TestDataClasses testDataClasses;
////	@Autowired private TestDataInstances testDataInstances;
//
//	private static final String FFEIDENBERG = "FF Eidenberg";
//	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
//	private static final String RKWILHERING = "RK Wilhering";
//
//}

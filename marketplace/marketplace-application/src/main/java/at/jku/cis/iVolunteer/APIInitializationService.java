package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataClasses;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataInstances;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Service
public class APIInitializationService {

	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	
	private List<Tenant> getTenants(){
		List<Tenant> tenants = new ArrayList<>();		
		tenants = coreTenantRestClient.getAllTenants();
		return tenants;
	}
	
	public void addiVolunteerAPIClassDefinition() {
		List<Tenant> tenants = this.getTenants();

		tenants.forEach(tenant -> {
			addPropertyDefinitions(tenant.getId());
			ClassDefinition cdPersonRole = classDefinitionRepository.findByNameAndTenantId("PersonRole", tenant.getId());
			if (cdPersonRole == null) {
				createiVolunteerAPIPersonRoleClassDefinition(tenant.getId());
				createiVolunteerAPIPersonBadgeClassDefinition(tenant.getId());
				createiVolunteerAPIPersonCertificateClassDefinition(tenant.getId());
				createiVolunteerAPIPersonTaskClassDefinition(tenant.getId());
			}
		});
	}

	private void createiVolunteerAPIPersonRoleClassDefinition(String tenantId) {
		FunctionClassDefinition functionDefinition = new FunctionClassDefinition();
		functionDefinition.setClassArchetype(ClassArchetype.FUNCTION);
		functionDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		functionDefinition.setRoot(true);
		functionDefinition.setName("PersonRole");
		functionDefinition.setTimestamp(new Date());
		functionDefinition.setTenantId(tenantId);
		List<PropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		functionDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonRoleProperties(properties)));
		classDefinitionRepository.save(functionDefinition);
	}

	private void createiVolunteerAPIPersonBadgeClassDefinition(String tenantId) {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonBadge");
		achievementDefinition.setTimestamp(new Date());
		achievementDefinition.setTenantId(tenantId);
		List<PropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonBadgeProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonCertificateClassDefinition(String tenantId) {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonCertificate");
		achievementDefinition.setTimestamp(new Date());
		achievementDefinition.setTenantId(tenantId);
		List<PropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonCertificateProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonTaskClassDefinition(String tenantId) {
		AchievementClassDefinition achievementDefinition = new AchievementClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.TASK);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonTask");
		achievementDefinition.setTimestamp(new Date());
		achievementDefinition.setTenantId(tenantId);
		List<PropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonTaskProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private List<PropertyDefinition<Object>> filterPersonRoleProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("roleID") || p.getName().equals("purpose") || p.getName().equals("name")
						|| p.getName().equals("Description") || p.getName().equals("organisationID")
						|| p.getName().equals("organisationName") || p.getName().equals("organisationType")
						|| p.getName().equals("Starting Date") || p.getName().equals("End Date")
						|| p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonBadgeProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("badgeID") || p.getName().equals("name")
						|| p.getName().equals("Description") || p.getName().equals("issuedOn")
						|| p.getName().equals("icon") || p.getName().equals("iVolunteerUUID")
						|| p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonCertificateProperties(
			List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("certificateID") || p.getName().equals("name")
						|| p.getName().equals("Description") || p.getName().equals("issuedOn")
						|| p.getName().equals("End Date") || p.getName().equals("icon")
						|| p.getName().equals("iVolunteerUUID") || p.getName().equals("iVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<PropertyDefinition<Object>> filterPersonTaskProperties(List<PropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream().filter(p -> p.getName().equals("taskId") || p.getName().equals("name")
				|| p.getName().equals("taskType1") || p.getName().equals("taskType2") || p.getName().equals("taskType3")
				|| p.getName().equals("taskType4") || p.getName().equals("Description") || p.getName().equals("purpose")
				|| p.getName().equals("role") || p.getName().equals("rank") || p.getName().equals("phase")
				|| p.getName().equals("unit") || p.getName().equals("level") || p.getName().equals("taskCountAll")
				|| p.getName().equals("Starting Date") || p.getName().equals("End Date")
				|| p.getName().equals("duration") || p.getName().equals("Location")
				|| p.getName().equals("geoInformation") || p.getName().equals("iVolunteerUUID")
				|| p.getName().equals("iVolunteerSource")).collect(Collectors.toList());
		// @formatter:on
	}

	private void addPropertyDefinitions(String tenantId) {
		List<PropertyDefinition<Object>> propertyDefinitions = new ArrayList<>();
		addCrossCuttingProperties(propertyDefinitions, tenantId);
		addPersonRoleProperties(propertyDefinitions, tenantId);
		addPersonBadgeProperties(propertyDefinitions, tenantId);
		addPersonCertificateProperties(propertyDefinitions, tenantId);
		addPersonTaskProperties(propertyDefinitions, tenantId);

		propertyDefinitions.forEach(pd -> {
			if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenantId).size() == 0) {
				propertyDefinitionRepository.save(pd);
			}
		});
	}

	private void addCrossCuttingProperties(List<PropertyDefinition<Object>> propertyDefinitions, String tenantId) {
		propertyDefinitions.add(new PropertyDefinition<Object>("iVolunteerUUID", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("iVolunteerSource", PropertyType.TEXT, tenantId));

		propertyDefinitions.add(new PropertyDefinition<Object>("issuedOn", PropertyType.DATE, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("icon", PropertyType.TEXT, tenantId));

		propertyDefinitions.add(new PropertyDefinition<Object>("purpose", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("role", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("rank", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("phase", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("unit", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("level", PropertyType.TEXT, tenantId));

		propertyDefinitions.add(new PropertyDefinition<Object>("duration", PropertyType.FLOAT_NUMBER, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("geoInformation", PropertyType.TEXT, tenantId));

	}

	private void addPersonRoleProperties(List<PropertyDefinition<Object>> propertyDefinitions, String tenantId) {
		propertyDefinitions.add(new PropertyDefinition<Object>("roleID", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationID", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationName", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("organisationType", PropertyType.TEXT, tenantId));
	}

	private void addPersonBadgeProperties(List<PropertyDefinition<Object>> propertyDefinitions, String tenantId) {
		propertyDefinitions.add(new PropertyDefinition<Object>("badgeID", PropertyType.TEXT, tenantId));
	}

	private void addPersonCertificateProperties(List<PropertyDefinition<Object>> propertyDefinitions, String tenantId) {
		propertyDefinitions.add(new PropertyDefinition<Object>("certificateID", PropertyType.TEXT, tenantId));
	}

	private void addPersonTaskProperties(List<PropertyDefinition<Object>> propertyDefinitions, String tenantId) {
		propertyDefinitions.add(new PropertyDefinition<Object>("taskId", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType1", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType2", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType3", PropertyType.TEXT, tenantId));
		propertyDefinitions.add(new PropertyDefinition<Object>("taskType4", PropertyType.TEXT, tenantId));
	}

}
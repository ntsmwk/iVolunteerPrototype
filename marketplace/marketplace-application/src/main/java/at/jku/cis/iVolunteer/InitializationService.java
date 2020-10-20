package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
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
	@Autowired protected MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;

	@Autowired private CoreTenantRestClient coreTenantRestClient;

	@Autowired public StandardPropertyDefinitions standardPropertyDefinitions;

	@Autowired private ClassConfigurationController classConfigurationController;

	@Autowired protected TestDataClasses testDataClasses;
	@Autowired protected TestDataInstances testDataInstances;

	@PostConstruct
	public void init() {
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
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions
					.getAllFlexProdProperties(tenant.getId())) {
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

}

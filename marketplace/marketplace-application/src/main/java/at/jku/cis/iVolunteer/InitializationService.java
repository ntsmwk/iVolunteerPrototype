package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurator.api.ConfiguratorRestClient;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataClasses;
import at.jku.cis.iVolunteer.marketplace.rule.engine.test.TestDataInstances;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

@Service
public class InitializationService {

	@Autowired
	protected ClassDefinitionRepository classDefinitionRepository;
	@Autowired
	protected RelationshipRepository relationshipRepository;
	@Autowired
	protected FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired
	protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired
	protected ClassConfigurationRepository classConfigurationRepository;
	@Autowired
	protected MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired
	protected MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired
	private CoreTenantRestClient coreTenantRestClient;

	@Autowired
	public StandardPropertyDefinitions standardPropertyDefinitions;

	@Autowired
	private ClassConfigurationController classConfigurationController;

	@Autowired
	protected TestDataClasses testDataClasses;
	@Autowired
	protected TestDataInstances testDataInstances;
	
	@Autowired ConfiguratorRestClient configuratorRestClient;
	
	@PostConstruct
	public void init() {
	}

	private List<Tenant> getTenants() {
		List<Tenant> tenants = new ArrayList<>();
		tenants = coreTenantRestClient.getAllTenants();

		return tenants;
	}

	private Tenant getFlexProdTenant() {
		List<Tenant> tenants = new ArrayList<>();
		tenants = coreTenantRestClient.getAllTenants();

		return tenants.stream().filter(t -> t.getName().equals("FlexProd")).findFirst().orElse(null);
	}

	public void addiVolunteerPropertyDefinitions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> fpd : standardPropertyDefinitions.getAlliVolunteer(tenant.getId())) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(fpd.getName(), fpd.getTenantId())
						.size() == 0) {
					flatPropertyDefinitionRepository.save(fpd);
				}
			}

			for (TreePropertyDefinition tpd : standardPropertyDefinitions.getAllTreeProperties(tenant.getId())) {
				if (treePropertyDefinitionRepository.getByNameAndTenantId(tpd.getName(), tpd.getTenantId()) == null) {
					treePropertyDefinitionRepository.save(tpd);
				}
			}

		});
	}

	public void addTestPropertyDefinitions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllTest(tenant.getId())) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addFlexProdPropertyDefinitions() {
		Tenant tenant = getFlexProdTenant();
		for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllFlexProdProperties(tenant.getId())) {
			if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
				flatPropertyDefinitionRepository.save(pd);
			}
		}

	}

	public void addGenericPropertyDefintions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric(tenant.getId())) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addHeaderPropertyDefintions() {
		List<Tenant> tenants = getTenants();
		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader(tenant.getId())) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addClassConfigurations(int noOfConfigurations) {
		List<Tenant> tenants = getTenants();

		for (Tenant t : tenants) {
			for (int i = 1; i <= noOfConfigurations; i++) {
				this.classConfigurationController
						.createNewClassConfiguration(new String[] { t.getId(), "Standardkonfiguration" + i, "" });
			}
		}
	}

	public void addFlexProdClassDefinitionsAndConfigurations(String tenantId) {
		this.classConfigurationController.createAndSaveFlexProdClassConfigurations(tenantId);
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
	
	public void initConfigurator() {
		List<Tenant> tenants = getTenants();
		
		List<String> tenantIds = tenants.stream().map(t -> t.getId()).collect(Collectors.toList());
		
		configuratorRestClient.initConfigurator(tenantIds);
	}

}

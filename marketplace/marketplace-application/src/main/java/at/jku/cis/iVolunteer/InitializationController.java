package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.badge.XBadgeCertificateNotificationRepository;
import at.jku.cis.iVolunteer.marketplace.badge.XBadgeCertificateRepository;
import at.jku.cis.iVolunteer.marketplace.badge.XBadgeTemplateRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.task.XTaskInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingRepository;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.usermapping.UserMapping;

@RestController
public class InitializationController {

	@Autowired
	private UserMappingRepository userMappingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClassInstanceRepository classInstanceRepository;
	@Autowired
	private MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired
	private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired
	private XTaskInstanceRepository xTaskInstanceRepository;
	@Autowired
	private InitializationService initializationService;
	@Autowired
	private APIInitializationService apiInitializationService;
	@Autowired
	private XBadgeTemplateRepository badgeTemplateRepository;
	@Autowired
	private XBadgeCertificateRepository badgeCertificateRepository;
	@Autowired
	private XBadgeCertificateNotificationRepository badgeCertificateNotificationRepository;

	@PutMapping("/init/add-test-data/{key}")
	public void addTestData(@PathVariable("key") String key) {
		addFireBrigadeUserMapping();
		initializationService.initConfigurator(key);

		addAPIClassDefinitions();

	}
	//
	// @PutMapping("/init/add-rule-test-data")
	// public void addRuleTestData() {
	// addRuleTestConfiguration();
	// addRuleUserData();
	// }

	// @PutMapping("/init/flexprod")
	// public void addFlexProdData() {
	// addFlexProdProperties();
	//
	// String tenantId = coreTenantRestClient.getTenantIdByName("FlexProd");
	// initializationService.addFlexProdClassDefinitionsAndConfigurations(tenantId);
	// }

	/**
	 * Properties
	 */

	@PutMapping("/init/add-properties/test")
	public void addAllTestProperties() {
		initializationService.addTestPropertyDefinitions();
	}

	@PutMapping("/init/add-properties/iVolunteer")
	public void addAllProperties() {
		initializationService.addiVolunteerPropertyDefinitions();
	}

	@PutMapping("/init/delete-properties")
	public void deleteProperties() {
		initializationService.flatPropertyDefinitionRepository.deleteAll();
	}

	@PutMapping("/init/add-properties/header")
	public void addHeaderProperties() {
		initializationService.addHeaderPropertyDefintions();
	}

	@PutMapping("/init/add-properties/generic")
	public void addGenericProperties() {
		initializationService.addGenericPropertyDefintions();
	}

	@PutMapping("/init/add-properties/flexprod")
	public void addFlexProdProperties() {
		initializationService.addFlexProdPropertyDefinitions();
	}

	/**
	 * Enum-Definitions
	 */

	@PutMapping("/init/delete-enumdefinitions")
	public void deleteEnumDefinitions() {
		initializationService.treePropertyDefinitionRepository.deleteAll();
	}

	/**
	 * Class-Definitions and Configurations
	 */

	@PutMapping("/init/add-api-classdefinitions")
	public void addAPIClassDefinitions() {
		apiInitializationService.addiVolunteerAPIClassDefinition();
	}

	// @PutMapping("/init/add-configurator-test-configurations")
	// public void addClassConfigurations() {
	// initializationService.addClassConfigurations(1);
	// }

	@PutMapping("/init/delete-class-definitions")
	public void deleteClassDefinitions() {
		initializationService.classDefinitionRepository.deleteAll();
	}

	@PutMapping("/init/delete-relationships")
	public void deleteRelationships() {
		initializationService.relationshipRepository.deleteAll();
	}

	@PutMapping("/init/delete-class-configurations")
	public void deleteClassConfigurations() {
		initializationService.classConfigurationRepository.deleteAll();
	}

	@PutMapping("/init/delete-class-instances")
	public void deleteClassInstances() {
		classInstanceRepository.deleteAll();
	}

	/**
	 * Rules
	 */

	// @PutMapping("/init/add-rule-test-configuration")
	// public void addRuleTestConfiguration() {
	// initializationService.testDataClasses.createClassConfigurations();
	// }
	//
	// @PutMapping("/init/add-rule-user-data")
	// public void addRuleUserData() {
	// initializationService.testDataInstances.createUserData();
	// }

	/**
	 * Matching
	 */

	@PutMapping("/init/delete-matching-collector-configurations")
	public void deleteMatchingCollectorConfigurations() {
		initializationService.matchingCollectorConfigurationRepository.deleteAll();
	}

	/**
	 * Users
	 */

	@PutMapping("/init/delete-marketplace-users")
	public void deleteMarketplaceUsers() {
		userRepository.deleteAll();
	}

	@PutMapping("/init/usermapping")
	public void addFireBrigadeUserMapping() {
		UserMapping userMapping = userMappingRepository.findByExternalUserId("5dc2a215399af");

		if (userMapping != null) {
			userMappingRepository.deleteAll();
		}

		User volunteer = userRepository.findByUsername("mweixlbaumer");
		if (volunteer != null) {
			UserMapping mapping = new UserMapping();
			mapping.setiVolunteerUserId(volunteer.getId());
			mapping.setExternalUserId("5dc2a215399af");
			userMappingRepository.save(mapping);
		}
	}

	@PutMapping("/init/usermapping/delete")
	public void deleteUserMappings() {
		userMappingRepository.deleteAll();
	}

	@PutMapping("/init/matching/delete")
	public void deleteMatchingConfigurations() {
		matchingConfigurationRepository.deleteAll();
		matchingOperatorRelationshipRepository.deleteAll();
	}

	@PutMapping("/init/taskinstance/delete")
	public void deleteTaskInstances() {
		xTaskInstanceRepository.deleteAll();
	}

	@PutMapping("/init/badgetemplates/delete")
	public void deleteBadgeTemplates() {
		badgeTemplateRepository.deleteAll();
	}

	@PutMapping("/init/badgecertificates/delete")
	public void deleteBadgeCertificates() {
		badgeCertificateRepository.deleteAll();
	}

	@PutMapping("/init/badgecertificatenotifications/delete")
	public void deleteBadgeCertificatenotifications() {
		badgeCertificateNotificationRepository.deleteAll();
	}

	@PutMapping("/init/wipe-marketplace")
	public void wipeMarketplace() {
		deleteClassConfigurations();
		deleteClassDefinitions();
		deleteRelationships();
		deleteClassInstances();
		deleteProperties();
		deleteMarketplaceUsers();
		deleteMatchingCollectorConfigurations();
		deleteEnumDefinitions();
		deleteUserMappings();
		deleteMatchingConfigurations();
		deleteTaskInstances();
		deleteBadgeTemplates();
		deleteBadgeCertificates();
		deleteBadgeCertificatenotifications();
	}

}

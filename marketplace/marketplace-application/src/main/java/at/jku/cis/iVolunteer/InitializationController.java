package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingRepository;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.usermapping.UserMapping;

@RestController
public class InitializationController {

	@Autowired private UserMappingRepository userMappingRepository;
	@Autowired private UserRepository userRepository;
	
	@Autowired private InitializationService initializationService;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	
	/**
	 * Properties
	 */
	
	@PutMapping("/init/add-properties/iVolunteer")
	public void addAllProperties() {
		this.initializationService.addiVolunteerPropertyDefinitions();
	}
	
	@DeleteMapping("/init/delete-properties")
	public void deleteProperties() {
		propertyDefinitionRepository.deleteAll();
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
	 * Class-Definitions and Configurations
	 */
	
	@PutMapping("/init/add-api-classdefinitions")
	public void addAPIClassDefinitions() {
		
	}
	
	@PutMapping("/init/add-configurator-test-configurations") 
	public void addClassConfigurations() {
		
	}
	
	@DeleteMapping("/init/delete-class-definitions")
	public void deleteClassDefinitions() {
		
	}
	
	@DeleteMapping("/init/delete-relationships")
	public void deleteRelationships() {
		
	}
	
	@DeleteMapping("/init/delete-class-configurations")
	public void deleteClassConfigurations() {
		
	}
	
	@DeleteMapping("/init/delete-class-instances")
	public void deleteClassInstances() {
		
	}
	
	/**
	 * Rules
	 */

	@PutMapping("/init/add-rule-test-configuration") 
	public void addRuleTestConfiguration() {
		
	}
	
	@PutMapping("/init/add-rule-user-data")
	public void addRuleUserData() {
		
	}
	
	/**
	 * Matching
	 */
	
	
	
	/**
	 * Users
	 */
	
	
	@DeleteMapping("/init/delete-marketplace-users")
	public void deleteClassInstanceUsers() {
		
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

}

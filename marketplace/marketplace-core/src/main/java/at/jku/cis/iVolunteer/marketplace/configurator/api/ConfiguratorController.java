package at.jku.cis.iVolunteer.marketplace.configurator.api;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationService;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipController;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceController;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionController;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipService;
import at.jku.cis.iVolunteer.model._httprequests.configurator.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model._httprequests.configurator.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model._httprequests.configurator.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model._httprequests.configurator.PropertyConfiguratorRequestBody;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

@RestController
public class ConfiguratorController {

	@Autowired private ClassInstanceController classInstanceController;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassConfigurationService classConfigurationController;
	@Autowired private RelationshipService relationshipController;
	@Autowired private MatchingConfigurationService matchingConfigurationService;
	@Autowired private MatchingOperatorRelationshipController matchingOperatorRelationshipController;
	@Autowired private MatchingConfigurationController matchingConfigurationController;
	@Autowired private FlatPropertyDefinitionService flatPropertyDefinitionService;
	@Autowired private TreePropertyDefinitionController treePropertyDefintionService;

	@PostMapping("/response/class-instance-configurator")
	private ClassInstance classInstanceConfiguratorResponse(
			@RequestBody ClassInstanceConfiguratorResponseRequestBody req) {

		return classInstanceController.createNewClassInstances(Collections.singletonList(req.getClassInstance()))
				.get(0);
	}

	@PostMapping("/response/class-configurator")
	private ResponseEntity<Void> classConfiguratorResponseSave(@RequestBody ClassConfiguratorResponseRequestBody req) {
		if (req == null) {
			System.out.println("null");
			return ResponseEntity.badRequest().build();
		}

		boolean retValue = false;
		if (req.getAction().equals("save")) {
			retValue = saveClassConfiguration(req);

		} else if (req.getAction().equals("delete")) {
			retValue = deleteClassConfiguration(req);
		}

		return retValue ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}

	private boolean saveClassConfiguration(ClassConfiguratorResponseRequestBody req) {
		if (req == null || req.getClassConfiguration() == null || req.getClassDefinitions() == null
				|| req.getRelationships() == null) {
			return false;
		}

		ClassConfiguration oldClassConfiguration = classConfigurationController
				.getClassConfigurationById(req.getClassConfiguration().getId());
		if (oldClassConfiguration != null) {
			classDefinitionService.deleteClassDefinition(oldClassConfiguration.getClassDefinitionIds());
			relationshipController.deleteRelationship(oldClassConfiguration.getRelationshipIds());
		}

		classDefinitionService.addOrUpdateClassDefinitions(req.getClassDefinitions());
		relationshipController.addOrUpdateRelationships(req.getRelationships());
		classConfigurationController.saveClassConfiguration(req.getClassConfiguration());
//		
//		flatPropertyDefinitionService.addPropertyDefinition(req.getFlatPropertyDefinitions());
//		treePropertyDefintionService.addTreePropertyDefinition(req.getTreePropertyDefinitions());

		return true;
	}

	private boolean deleteClassConfiguration(ClassConfiguratorResponseRequestBody req) {
		if (req == null || req.getIdsToDelete() == null) {
			return false;
		}

		classConfigurationController.deleteMultipleClassConfigurations(req.getIdsToDelete());
		return true;
	}

	@PostMapping("/response/matching-configurator")
	private ResponseEntity<Void> matchingConfiguratorResponse(@RequestBody MatchingConfiguratorResponseRequestBody req) {
		if (req == null) {
			return ResponseEntity.badRequest().build();
		}

		boolean retValue = false;
		if (req.getAction().equals("save")) {
			retValue = saveMatchingConfiguration(req);
		} else if (req.getAction().equals("delete")) {
			retValue = deleteMatchingConfiguration(req);
		}

		return retValue ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();	
	}
	
	private boolean saveMatchingConfiguration(MatchingConfiguratorResponseRequestBody req) {
		if (req == null || req.getMatchingConfiguration() == null || req.getMatchingRelationships() == null) {
			return false;
		}

		MatchingConfiguration matchingConfiguration = matchingConfigurationService
				.saveMatchingConfiguration(req.getMatchingConfiguration());
		
		matchingOperatorRelationshipController.saveMatchingOperatorRelationshipByMatchingConfiguration(
				matchingConfiguration.getId(), req.getMatchingRelationships());

		return true;
	}
	
	private boolean deleteMatchingConfiguration(MatchingConfiguratorResponseRequestBody req) {
		if (req == null || req.getIdsToDelete() == null) {
			return false;
		}
		
		req.getIdsToDelete().forEach(matchingConfigurationController::deleteMatchingConfiguration);
		
		return true;
	}

	
	@PostMapping("/response/property-configurator")
	private ResponseEntity<Void> matchingConfiguratorResponse(@RequestBody PropertyConfiguratorRequestBody req) {
		if (req == null) {
			return ResponseEntity.badRequest().build();
		}

		boolean retValue = false;
		if (req.getAction().equals("save")) {
			retValue = saveProperties(req);
		} else if (req.getAction().equals("delete")) {
			retValue = deleteProperties(req);
		}

		return retValue ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();	
	}
	
	private boolean saveProperties(PropertyConfiguratorRequestBody req) {
		
		flatPropertyDefinitionService.addPropertyDefinition(req.getFlatPropertyDefinitions());
		treePropertyDefintionService.addTreePropertyDefinition(req.getTreePropertyDefinitions());

		return true;
	}
	
	private boolean deleteProperties(PropertyConfiguratorRequestBody req) {
		if (req.getFlatPropertyDefinitions() != null) {
			for(FlatPropertyDefinition<Object> propertyDefinition: req.getFlatPropertyDefinitions()) {
				flatPropertyDefinitionService.deletePropertyDefinition(propertyDefinition.getId(), false);
			}
		}
		if (req.getTreePropertyDefinitions() != null) {
			for(TreePropertyDefinition propertyDefinition: req.getTreePropertyDefinitions()) {
				treePropertyDefintionService.deleteTreePropertyDefinition(propertyDefinition.getId(), false);
			}
		}
		return true;
	}
}

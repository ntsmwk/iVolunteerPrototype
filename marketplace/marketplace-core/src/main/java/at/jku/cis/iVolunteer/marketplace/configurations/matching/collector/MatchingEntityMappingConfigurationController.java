package at.jku.cis.iVolunteer.marketplace.configurations.matching.collector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.marketplace.user.UserController;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingDataRequestDTO;
import at.jku.cis.iVolunteer.model.matching.MatchingEntityMappings;
import at.jku.cis.iVolunteer.model.matching.MatchingMappingEntry;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.user.User;

@RestController
public class MatchingEntityMappingConfigurationController {

	@Autowired CollectionService collectionService;
	@Autowired MatchingEntityMappingConfigurationRepository matchingEntityMappingConfigurationRepository;
	@Autowired MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired ClassConfigurationRepository classConfigurationRepository;
	@Autowired UserController userController;
	
// TODO alex change to support user 
	@PutMapping("matching-entity-data")
	private MatchingDataRequestDTO getMatchingEntityData(@RequestBody MatchingConfiguration mc) {
		
		MatchingEntityMappingConfiguration leftMapping = null;
		MatchingEntityMappingConfiguration rightMapping = null;
		
		if (!mc.isLeftIsUser()) {
			leftMapping = matchingEntityMappingConfigurationRepository.findOne(mc.getLeftSideId());
		} else {
			MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
			matchingCollectorConfiguration.setId(mc.getLeftSideId());
			matchingCollectorConfiguration.setClassConfigurationId(mc.getLeftSideId());
			
			MatchingEntityMappings mappings = collectionService.collectUserClassDefinitionWithPropertiesAsMatchingEntityMappings();
			matchingCollectorConfiguration.setMappings(mappings);
			
			leftMapping = matchingCollectorConfiguration;

		}
		
		if (!mc.isRightIsUser()) {
			rightMapping = matchingEntityMappingConfigurationRepository.findOne(mc.getRightSideId());
		} else {
			MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
			matchingCollectorConfiguration.setId(mc.getRightSideId());
			matchingCollectorConfiguration.setClassConfigurationId(mc.getRightSideId());
			MatchingEntityMappings mappings = collectionService.collectUserClassDefinitionWithPropertiesAsMatchingEntityMappings();
			matchingCollectorConfiguration.setMappings(mappings);
			rightMapping = matchingCollectorConfiguration;
		}
		
		List<MatchingOperatorRelationship> matchingOperatorRelationships = new ArrayList<MatchingOperatorRelationship>();
		matchingOperatorRelationshipRepository.findByMatchingConfigurationId(mc.getId()).forEach(matchingOperatorRelationships::add);
		
		MatchingDataRequestDTO dto = new MatchingDataRequestDTO();
		dto.setLeftMappingConfigurations(leftMapping);
		dto.setRightMappingConfigurations(rightMapping);
		dto.setMatchingConfiguration(mc);
		dto.setRelationships(matchingOperatorRelationships);
		dto.setPathDelimiter(CollectionService.PATH_DELIMITER);
		
		return dto;
	}
	
}

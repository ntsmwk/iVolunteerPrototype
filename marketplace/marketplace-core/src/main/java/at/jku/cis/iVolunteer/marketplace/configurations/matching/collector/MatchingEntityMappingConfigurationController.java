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
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingDataRequestDTO;
import at.jku.cis.iVolunteer.model.matching.MatchingEntityMappings;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@RestController
public class MatchingEntityMappingConfigurationController {

	@Autowired CollectionService collectionService;
	@Autowired MatchingEntityMappingConfigurationRepository matchingEntityMappingConfigurationRepository;
	@Autowired MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired ClassConfigurationRepository classConfigurationRepository;

//	@GetMapping("matching-collector-configuration/{slotId}/aggregate-in-single")
//	private List<ClassDefinition> aggregateInSingleMatchingCollectorConfiguration(
//			@PathVariable("slotId") String slotId) {
//		return collectionService.collectAllClassDefinitionsWithPropertiesAsList(slotId);
//	}
//
//	@GetMapping("matching-collector-configuration/{slotId}/aggregate-in-collections")
//	private List<MatchingEntityMappings> aggregateInMultipleCollectorsConfiguration(@PathVariable("slotId") String slotId) {
//		return collectionService.collectAllClassDefinitionsWithPropertiesAsMatchingCollectors(slotId);
//	}
	
	@GetMapping("matching-entity-data/{id1}/{id2}")
	private MatchingDataRequestDTO getMatchingEntityData(@PathVariable("id1") String idLeft, @PathVariable("id2") String idRight) {
		
		MatchingEntityMappingConfiguration leftMappings = matchingEntityMappingConfigurationRepository.findOne(idLeft);
		MatchingEntityMappingConfiguration rightMappings = matchingEntityMappingConfigurationRepository.findOne(idRight);

		MatchingConfiguration matchingConfiguration = matchingConfigurationRepository.findByLeftClassConfigurationIdAndRightClassConfigurationId(idLeft, idRight);
		
		
	
		List<MatchingOperatorRelationship> matchingOperatorRelationships = new ArrayList<MatchingOperatorRelationship>();
		matchingOperatorRelationshipRepository.findByMatchingConfigurationId(matchingConfiguration.getId()).forEach(matchingOperatorRelationships::add);
		
		MatchingDataRequestDTO dto = new MatchingDataRequestDTO();
		dto.setLeftMappingConfigurations(leftMappings);
		dto.setRightMappingConfigurations(rightMappings);
		dto.setMatchingConfiguration(matchingConfiguration);
		dto.setRelationships(matchingOperatorRelationships);
		dto.setPathDelimiter(CollectionService.PATH_DELIMITER);
		
		
		return dto;
	}
	
//	@GetMapping("matching-collector-configuration/{id}/saved-configuration")
//	private MatchingEntityMappingConfiguration getSavedMatchingCollectorConfiguration(@PathVariable("id") String id) {
//		return matchingEntityMappingConfigurationRepository.findOne(id);
//	}
//
//	@PostMapping("matching-collector-configuration/new")
//	private MatchingEntityMappingConfiguration createMatchingCollectorConfiguration(@RequestBody MatchingEntityMappingConfiguration configuration) {
//		return updateMatchingCollectorConfiguration(configuration);
//	}
//
//	@PutMapping("matching-collector-configuration/update")
//	private MatchingEntityMappingConfiguration updateMatchingCollectorConfiguration(@RequestBody MatchingEntityMappingConfiguration configuration) {
//		return matchingEntityMappingConfigurationRepository.save(configuration);
//	}
//	
//	@DeleteMapping("matching-collector-configuration/{id}/delete")
//	private void deleteMatchingCollectorConfiguration(@PathVariable("id") String id) {
//		matchingEntityMappingConfigurationRepository.delete(id);
//	}
	
	
	
	
	
}

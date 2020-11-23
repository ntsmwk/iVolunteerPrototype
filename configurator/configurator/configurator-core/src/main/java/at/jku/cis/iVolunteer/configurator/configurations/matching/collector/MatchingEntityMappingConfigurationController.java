package at.jku.cis.iVolunteer.configurator.configurations.matching.collector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.matching.MatchingDataRequestDTO;
import at.jku.cis.iVolunteer.configurator.model.matching.MatchingEntityMappings;

@RestController
public class MatchingEntityMappingConfigurationController {

	@Autowired CollectionService collectionService;
	@Autowired MatchingEntityMappingConfigurationRepository matchingEntityMappingConfigurationRepository;
	@Autowired MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired ClassConfigurationRepository classConfigurationRepository;

	@PutMapping("matching-entity-data")
	private MatchingDataRequestDTO getMatchingEntityData(@RequestBody MatchingConfiguration mc) {

		MatchingEntityMappingConfiguration leftMapping = null;
		MatchingEntityMappingConfiguration rightMapping = null;

		if (!mc.isLeftIsUser()) {
			leftMapping = matchingEntityMappingConfigurationRepository.findOne(mc.getLeftSideId());
		} else {
			leftMapping = getMatchingEntityMappingConfiguration(mc.getLeftSideId());
		}

		if (!mc.isRightIsUser()) {
			rightMapping = matchingEntityMappingConfigurationRepository.findOne(mc.getRightSideId());
		} else {
			rightMapping = getMatchingEntityMappingConfiguration(mc.getRightSideId());
		}

		List<MatchingOperatorRelationship> matchingOperatorRelationships = new ArrayList<MatchingOperatorRelationship>();
		matchingOperatorRelationshipRepository.findByMatchingConfigurationId(mc.getId())
				.forEach(matchingOperatorRelationships::add);

		MatchingDataRequestDTO dto = new MatchingDataRequestDTO();
		dto.setLeftMappingConfigurations(leftMapping);
		dto.setRightMappingConfigurations(rightMapping);
		dto.setMatchingConfiguration(mc);
		dto.setRelationships(matchingOperatorRelationships);
		dto.setPathDelimiter(CollectionService.PATH_DELIMITER);

		return dto;
	}

	public MatchingEntityMappingConfiguration getMatchingEntityMappingConfiguration(String id) {
		MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
		matchingCollectorConfiguration.setId(id);
		matchingCollectorConfiguration.setClassConfigurationId(id);
		MatchingEntityMappings mappings = collectionService
				.collectUserClassDefinitionWithPropertiesAsMatchingEntityMappings();
		matchingCollectorConfiguration.setMappings(mappings);
		return matchingCollectorConfiguration;
	}

}

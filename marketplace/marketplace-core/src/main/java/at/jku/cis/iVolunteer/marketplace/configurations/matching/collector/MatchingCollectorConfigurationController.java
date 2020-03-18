package at.jku.cis.iVolunteer.marketplace.configurations.matching.collector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingCollectorConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@RestController
public class MatchingCollectorConfigurationController {

	@Autowired CollectionService collectionService;
	@Autowired MatchingCollectorConfigurationRepository matchingCollectorConfigurationRepository;

	@GetMapping("matching-collector-configuration/{slotId}/aggregate-in-single")
	private List<ClassDefinition> aggregateInSingleMatchingCollectorConfiguration(
			@PathVariable("slotId") String slotId) {
		return collectionService.collectAllClassDefinitionsWithPropertiesAsSingleCollection(slotId);
	}

	@GetMapping("matching-collector-configuration/{slotId}/aggregate-in-collections")
	private List<MatchingCollector> aggregateInMultipleCollectorsConfiguration(@PathVariable("slotId") String slotId) {
		return collectionService.collectAllClassDefinitionsWithPropertiesAsMultipleCollections(slotId);
	}

	@GetMapping("matching-collector-configuration/{id}/saved-configuration")
	private MatchingCollectorConfiguration getSavedMatchingCollectorConfiguration(@PathVariable("id") String id) {
		return matchingCollectorConfigurationRepository.findOne(id);
	}

	@PostMapping("matching-collector-configuration/new")
	private MatchingCollectorConfiguration createMatchingCollectorConfiguration(@RequestBody MatchingCollectorConfiguration configuration) {
		return updateMatchingCollectorConfiguration(configuration);
	}

	@PutMapping("matching-collector-configuration/update")
	private MatchingCollectorConfiguration updateMatchingCollectorConfiguration(@RequestBody MatchingCollectorConfiguration configuration) {
		return matchingCollectorConfigurationRepository.save(configuration);
	}
	
	@DeleteMapping("matching-collector-configuration/{id}/delete")
	private void deleteMatchingCollectorConfiguration(@PathVariable("id") String id) {
		matchingCollectorConfigurationRepository.delete(id);
	}
	
	
	
	
	
}

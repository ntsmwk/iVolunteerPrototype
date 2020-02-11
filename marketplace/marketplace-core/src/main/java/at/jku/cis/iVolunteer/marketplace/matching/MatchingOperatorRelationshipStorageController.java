package at.jku.cis.iVolunteer.marketplace.matching;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.fake.IsSunburstFakeDocument;
import at.jku.cis.iVolunteer.marketplace.fake.IsSunburstFakeRepository;
import at.jku.cis.iVolunteer.model.matching.MatchingOperatorRelationshipStorage;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;

@RestController
public class MatchingOperatorRelationshipStorageController {

	@Autowired private MatchingOperatorRelationshipStorageRepository matchingOperatorRelationshipStorageRepository;

	@GetMapping("matching/operator-relationship/all")
	List<MatchingOperatorRelationshipStorage> getAllMatchingOperatorRelationships() {

		return matchingOperatorRelationshipStorageRepository.findAll();
	}
	
	@GetMapping("matching/operator-relationship/{id}")
	MatchingOperatorRelationshipStorage getAllMatchingOperatorRelationshipById(@PathVariable("id") String id) {

		return matchingOperatorRelationshipStorageRepository.findOne(id);
	}

	@GetMapping("matching/operator-relationship/by-configurators/{producerConfiguratorId}/{consumerConfiguratorId}")
	MatchingOperatorRelationshipStorage getMatchingOperatorRelationshipByConfiguratorIds(
			@PathVariable("producerConfiguratorId") String producerConfiguratorId,
			@PathVariable("consumerConfiguratorId") String consumerConfiguratorId) {

		return matchingOperatorRelationshipStorageRepository
				.findByProducerConfiguratorIdAndConsumerConfiguratorId(producerConfiguratorId, consumerConfiguratorId);
	}
	
	@PostMapping("matching/operator-relationship/save")
	MatchingOperatorRelationshipStorage saveMatchingOperatorRelationships(@RequestBody MatchingOperatorRelationshipStorage storage) {
		return matchingOperatorRelationshipStorageRepository.save(storage);
	}
	
	@DeleteMapping("matching/operator-relationship/{id}/delete")
	private void deleteMatchingOperatorRelationships(@PathVariable("id") String id) {
		
		matchingOperatorRelationshipStorageRepository.delete(id);
	}

}

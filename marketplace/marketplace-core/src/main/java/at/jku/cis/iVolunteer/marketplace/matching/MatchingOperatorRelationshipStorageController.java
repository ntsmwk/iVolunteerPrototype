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
import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.model.matching.MatchingOperatorRelationshipStorage;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;

@RestController
public class MatchingOperatorRelationshipStorageController {

	@Autowired private MatchingOperatorRelationshipStorageRepository matchingOperatorRelationshipStorageRepository;
	@Autowired private ConfiguratorRepository configuratorRepository;

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
	
	@GetMapping("matching/operator-relationship/by-configurators/{configuratorId1}/{configuratorId2}/unordered")
	MatchingOperatorRelationshipStorage getMatchingOperatorRelationshipByConfiguratorIdsUnordered(
			@PathVariable("configuratorId1") String configuratorId1,
			@PathVariable("configuratorId2") String configuratorId2) {
		
		String id = createRelationshipStorageHash(configuratorId1, configuratorId2);

		return matchingOperatorRelationshipStorageRepository.findOne(id);
	}
	
	@PostMapping("matching/operator-relationship/save")
	MatchingOperatorRelationshipStorage saveMatchingOperatorRelationships(@RequestBody MatchingOperatorRelationshipStorage storage) {
		if (storage.getId() == null) {
			storage.setId(createRelationshipStorageHash(storage.getProducerConfiguratorId(), storage.getConsumerConfiguratorId()));
			
			Configurator producer = configuratorRepository.findOne(storage.getProducerConfiguratorId());
			Configurator consumer = configuratorRepository.findOne(storage.getConsumerConfiguratorId());
			
			storage.setName(producer.getName() + " --> " + consumer.getName());
			
			
		}
		return matchingOperatorRelationshipStorageRepository.save(storage);
	}
	
	private String createRelationshipStorageHash(String id1, String id2) {
		String hash = String.valueOf(id1.hashCode() ^ id2.hashCode());
		return hash;
	}
	
	@DeleteMapping("matching/operator-relationship/{id}/delete")
	private void deleteMatchingOperatorRelationships(@PathVariable("id") String id) {
		
		matchingOperatorRelationshipStorageRepository.delete(id);
	}

}

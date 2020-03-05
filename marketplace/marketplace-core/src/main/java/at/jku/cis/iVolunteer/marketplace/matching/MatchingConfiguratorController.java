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
import at.jku.cis.iVolunteer.model.matching.MatchingConfigurator;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;

@RestController
public class MatchingConfiguratorController {

	@Autowired private MatchingConfiguratorRepository matchingConfiguratorRepository;
	@Autowired private ConfiguratorRepository configuratorRepository;

	@GetMapping("matching/operator-relationship/all")
	List<MatchingConfigurator> getAllMatchingConfigurators() {

		return matchingConfiguratorRepository.findAll();
	}

	@GetMapping("matching/operator-relationship/{id}")
	MatchingConfigurator getAllMatchingConfiguratorsById(@PathVariable("id") String id) {

		return matchingConfiguratorRepository.findOne(id);
	}

	@GetMapping("matching/operator-relationship/by-configurators/{producerClassConfiguratorId}/{consumerClassConfiguratorId}")
	MatchingConfigurator getMatchingConfiguratorByClassConfiguratorIds(
			@PathVariable("producerClassConfiguratorId") String producerClassConfiguratorId,
			@PathVariable("consumerClassConfiguratorId") String consumerClassConfiguratorId) {

		return matchingConfiguratorRepository
				.findByProducerClassConfiguratorIdAndConsumerClassConfiguratorId(producerClassConfiguratorId, consumerClassConfiguratorId);
	}
	
	@GetMapping("matching/operator-relationship/by-configurators/{configuratorId1}/{configuratorId2}/unordered")
	MatchingConfigurator getMatchingConfiguratorByClassConfiguratorIdsUnordered(
			@PathVariable("configuratorId1") String configuratorId1,
			@PathVariable("configuratorId2") String configuratorId2) {

		String id = createHashFromClassConfiguratorIds(configuratorId1, configuratorId2);

		return matchingConfiguratorRepository.findOne(id);
	}

	@PostMapping("matching/operator-relationship/save")
	MatchingConfigurator saveMatchingConfigurator(
			@RequestBody MatchingConfigurator matchingConfigurator) {
		if (matchingConfigurator.getId() == null) {
			matchingConfigurator.setId(createHashFromClassConfiguratorIds(matchingConfigurator.getProducerClassConfiguratorId(),
					matchingConfigurator.getConsumerClassConfiguratorId()));

			Configurator producer = configuratorRepository.findOne(matchingConfigurator.getProducerClassConfiguratorId());
			Configurator consumer = configuratorRepository.findOne(matchingConfigurator.getConsumerClassConfiguratorId());

			matchingConfigurator.setProducerClassConfiguratorName(producer.getName());
			matchingConfigurator.setConsumerClassConfiguratorName(consumer.getName());
			
			if (matchingConfigurator.getName() == null) {
				matchingConfigurator.setName(producer.getName() + " --> " + consumer.getName());
			}

		}
		return matchingConfiguratorRepository.save(matchingConfigurator);
	}

	private String createHashFromClassConfiguratorIds(String id1, String id2) {
		String hash = String.valueOf(id1.hashCode() ^ id2.hashCode());
		return hash;
	}

	@DeleteMapping("matching/operator-relationship/{id}/delete")
	private void deleteMatchingConfigurator(@PathVariable("id") String id) {

		matchingConfiguratorRepository.delete(id);
	}

}

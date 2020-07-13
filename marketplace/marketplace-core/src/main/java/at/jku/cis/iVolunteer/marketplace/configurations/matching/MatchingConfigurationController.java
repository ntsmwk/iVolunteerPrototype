package at.jku.cis.iVolunteer.marketplace.configurations.matching;

import java.util.Date;
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
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

@RestController
public class MatchingConfigurationController {

	@Autowired private MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired private ClassConfigurationRepository configuratorRepository;

	@GetMapping("matching-configuration/all")
	List<MatchingConfiguration> getAllMatchingConfigurations() {

		return matchingConfigurationRepository.findAll();
	}

	@GetMapping("matching-configuration/{id}")
	MatchingConfiguration getAllMatchingConfigurationsById(@PathVariable("id") String id) {

		return matchingConfigurationRepository.findOne(id);
	}

	@GetMapping("matching-configuration/by-class-configurators/{producerClassConfigurationId}/{consumerClassConfigurationId}")
	MatchingConfiguration getMatchingConfiguratorByClassConfigurationIds(
			@PathVariable("producerClassConfigurationId") String producerClassConfigurationId,
			@PathVariable("consumerClassConfigurationId") String consumerClassConfigurationId) {

		return matchingConfigurationRepository.findByProducerClassConfigurationIdAndConsumerClassConfigurationId(
				producerClassConfigurationId, consumerClassConfigurationId);
	}

	@GetMapping("matching-configuration/by-class-configurators/{classConfigurationId1}/{classConfigurationId2}/unordered")
	MatchingConfiguration getMatchingConfiguratorByClassConfigurationIdsUnordered(
			@PathVariable("classConfigurationId1") String classConfigurationId1,
			@PathVariable("classConfigurationId2") String classConfigurationId2) {

		String id = createHashFromClassConfigurationIds(classConfigurationId1, classConfigurationId2);

		return matchingConfigurationRepository.findOne(id);
	}

	@PostMapping("matching-configuration/save")
	MatchingConfiguration saveMatchingConfiguration(@RequestBody MatchingConfiguration matchingConfiguration) {
		if (matchingConfiguration.getId() == null) {
			matchingConfiguration
					.setId(createHashFromClassConfigurationIds(matchingConfiguration.getLeftClassConfigurationId(),
							matchingConfiguration.getRightClassConfigurationId()));

			ClassConfiguration leftConfiguration = configuratorRepository
					.findOne(matchingConfiguration.getLeftClassConfigurationId());
			ClassConfiguration rightConfiguration = configuratorRepository
					.findOne(matchingConfiguration.getRightClassConfigurationId());

			matchingConfiguration.setLeftClassConfigurationName(leftConfiguration.getName());
			matchingConfiguration.setRightClassConfigurationName(rightConfiguration.getName());

			if (matchingConfiguration.getName() == null) {
				matchingConfiguration.setName(leftConfiguration.getName() + " --> " + rightConfiguration.getName());
			}

		}

		matchingConfiguration.setTimestamp(new Date());

		return matchingConfigurationRepository.save(matchingConfiguration);
	}

	private String createHashFromClassConfigurationIds(String id1, String id2) {
		String hash = String.valueOf(id1.hashCode() ^ id2.hashCode());
		return hash;
	}

	@DeleteMapping("matching-configuration/{id}/delete")
	private void deleteMatchingConfiguration(@PathVariable("id") String id) {

		matchingConfigurationRepository.delete(id);
	}

	@PutMapping("matching-configuration/delete-multiple")
	private List<MatchingConfiguration> deleteMultipleMatchingConfigurations(@RequestBody() List<String> ids) {
//		List<MatchingConfiguration> matchingConfigurations = new ArrayList<>();
//		this.matchingConfigurationRepository.findAll(ids).forEach(matchingConfigurations::add);;
//		this.matchingConfigurationRepository.delete(matchingConfigurations);

		ids.forEach(this.matchingConfigurationRepository::delete);
		return this.matchingConfigurationRepository.findAll();
	}

}

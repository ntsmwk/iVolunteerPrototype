package at.jku.cis.iVolunteer.marketplace.configurations.matching;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

@Service
public class MatchingConfigurationService {

	@Autowired private ClassConfigurationRepository configuratorRepository;
	@Autowired private MatchingConfigurationRepository matchingConfigurationRepository;

	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIds(String producerClassConfigurationId,
			String consumerClassConfigurationId) {
		return matchingConfigurationRepository.findByProducerClassConfigurationIdAndConsumerClassConfigurationId(
				producerClassConfigurationId, consumerClassConfigurationId);
	}

	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIdsUnordered(String classConfigurationId1,
			String classConfigurationId2) {
		String id = createHashFromClassConfigurationIds(classConfigurationId1, classConfigurationId2);
		return matchingConfigurationRepository.findOne(id);
	}

	public MatchingConfiguration saveMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
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
}

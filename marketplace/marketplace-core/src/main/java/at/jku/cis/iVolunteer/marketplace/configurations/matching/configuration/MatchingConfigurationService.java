package at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

@Service
public class MatchingConfigurationService {

	@Autowired private MatchingConfigurationRepository matchingConfigurationRepository;
	
	public MatchingConfiguration getMatchingConfigurationById(String id) {
		return matchingConfigurationRepository.findOne(id);
	}

	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIds(String leftSideId,
			String rightSideId) {
		return matchingConfigurationRepository.findByLeftSideIdAndRightSideId(leftSideId, rightSideId);
	}

	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIdsUnordered(String classConfigurationId1,
			String classConfigurationId2) {
		String hash = createHashFromClassConfigurationIds(classConfigurationId1, classConfigurationId2);
		if (classConfigurationId1.equals(classConfigurationId2)) {
			return getMatchingConfiguratorByClassConfigurationIds(classConfigurationId1, classConfigurationId2);
		} else {
			List<MatchingConfiguration> matchingConfigurations = matchingConfigurationRepository.findByHash(hash);
			if (matchingConfigurations.size() == 1) {
				return matchingConfigurations.get(0);
			}
		}
		
		return null;
	}
	
	public MatchingConfiguration saveMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
		if (matchingConfiguration == null) {
			return null;
		}
		if (matchingConfiguration.getId() == null) {
			String leftClassConfigurationId = matchingConfiguration.getLeftSideId();
			String rightClassConfigurationId = matchingConfiguration.getRightSideId();
			String hash = createHashFromClassConfigurationIds(leftClassConfigurationId, rightClassConfigurationId);
			matchingConfiguration.setHash(hash);

		}

		matchingConfiguration.setTimestamp(new Date());
		return matchingConfigurationRepository.save(matchingConfiguration);
	}

	private String createHashFromClassConfigurationIds(String id1, String id2) {
		String hash = String.valueOf(id1.hashCode() ^ id2.hashCode());
		return hash;
	}
}

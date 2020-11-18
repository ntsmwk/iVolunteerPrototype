package at.jku.cis.iVolunteer.configurator.configurations.matching.configuration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipController;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipService;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;

@Service
public class MatchingConfigurationService {

	@Autowired private MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	
	public MatchingConfiguration getMatchingConfigurationById(String id) {
		if (id == null) {
			return null;
		}
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
		if (matchingConfiguration.getId() == null) {
			String leftClassConfigurationId = matchingConfiguration.getLeftSideId();
			String rightClassConfigurationId = matchingConfiguration.getRightSideId();
			String hash = createHashFromClassConfigurationIds(leftClassConfigurationId, rightClassConfigurationId);
			matchingConfiguration.setHash(hash);
		}

		matchingConfiguration.setTimestamp(new Date());
		return matchingConfigurationRepository.save(matchingConfiguration);
	}

	public String createHashFromClassConfigurationIds(String id1, String id2) {
		String hash = String.valueOf(id1.hashCode() ^ id2.hashCode());
		return hash;
	}
	
	public boolean deleteMatchingConfiguration(String id) {
		if (id == null) {
			return false;
		}
		
		MatchingConfiguration config = getMatchingConfigurationById(id);
		
		List<MatchingOperatorRelationship> relationships = matchingOperatorRelationshipRepository.findByMatchingConfigurationId(id);
		relationships.forEach(matchingOperatorRelationshipRepository::delete);
		
		matchingConfigurationRepository.delete(id);
		return true;

	}
}

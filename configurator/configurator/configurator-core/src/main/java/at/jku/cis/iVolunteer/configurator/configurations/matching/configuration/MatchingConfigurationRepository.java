package at.jku.cis.iVolunteer.configurator.configurations.matching.configuration;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;

public interface MatchingConfigurationRepository extends MongoRepository<MatchingConfiguration, String> {

	public MatchingConfiguration findByLeftSideIdAndRightSideId(
			String leftSideId, String rightSideId);
	
	public List<MatchingConfiguration> findByTenantId(String tenantId);
	
	public List<MatchingConfiguration> findByHash(String hash);
	
}

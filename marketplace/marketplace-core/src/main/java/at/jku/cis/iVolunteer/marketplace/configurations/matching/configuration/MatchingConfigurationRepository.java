package at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration;

import java.util.List;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

public interface MatchingConfigurationRepository extends HasTenantRepository<MatchingConfiguration, String> {

	public MatchingConfiguration findByLeftSideIdAndRightSideId(
			String leftSideId, String rightSideId);
	
	public List<MatchingConfiguration> findByHash(String hash);
	
}

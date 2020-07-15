package at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

public interface MatchingConfigurationRepository extends MongoRepository<MatchingConfiguration, String> {

	public MatchingConfiguration findByLeftClassConfigurationIdAndRightClassConfigurationId(String leftClassConfigurationId, String rightClassConfigurationId);
}

package at.jku.cis.iVolunteer.marketplace.matching;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configuration.matching.MatchingConfiguration;

public interface MatchingConfigurationRepository extends MongoRepository<MatchingConfiguration, String> {

	public MatchingConfiguration findByProducerClassConfigurationIdAndConsumerClassConfigurationId(String producerClassConfigurationId, String consumerClassConfigurationId);
}

package at.jku.cis.iVolunteer.marketplace.configurations.matching.collector;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingCollectorConfiguration;

public interface MatchingCollectorConfigurationRepository
		extends MongoRepository<MatchingCollectorConfiguration, String> {

}

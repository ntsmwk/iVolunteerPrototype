package at.jku.cis.iVolunteer.marketplace.configurations.matching.collector;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingEntityMappingConfiguration;

public interface MatchingEntityMappingConfigurationRepository
		extends MongoRepository<MatchingEntityMappingConfiguration, String> {

}

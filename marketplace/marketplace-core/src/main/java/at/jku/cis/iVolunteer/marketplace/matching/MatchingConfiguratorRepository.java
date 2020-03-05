package at.jku.cis.iVolunteer.marketplace.matching;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.matching.MatchingConfigurator;

public interface MatchingConfiguratorRepository extends MongoRepository<MatchingConfigurator, String> {

	public MatchingConfigurator findByProducerClassConfiguratorIdAndConsumerClassConfiguratorId(String producerClassConfiguratorId, String consumerClassConfiguratorId);
}

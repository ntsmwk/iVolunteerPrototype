package at.jku.cis.iVolunteer.marketplace.matching;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.matching.MatchingOperatorRelationshipStorage;

public interface MatchingOperatorRelationshipStorageRepository extends MongoRepository<MatchingOperatorRelationshipStorage, String> {

	public MatchingOperatorRelationshipStorage findByProducerConfiguratorIdAndConsumerConfiguratorId(String producerConfiguratorId, String consumerConfiguratorId);
}

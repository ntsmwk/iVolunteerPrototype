package at.jku.cis.iVolunteer.configurator.configurations.matching.relationships;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;

public interface MatchingOperatorRelationshipRepository extends MongoRepository<MatchingOperatorRelationship, String> {

	List<MatchingOperatorRelationship> findByMatchingConfigurationId(String matchingConfigurationId);

}

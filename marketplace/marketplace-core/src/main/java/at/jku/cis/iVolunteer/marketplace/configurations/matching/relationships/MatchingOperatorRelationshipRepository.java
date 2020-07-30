package at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships;

import java.util.List;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;

public interface MatchingOperatorRelationshipRepository
		extends HasTenantRepository<MatchingOperatorRelationship, String> {

	List<MatchingOperatorRelationship> findByMatchingConfigurationId(String matchingConfigurationId);

}

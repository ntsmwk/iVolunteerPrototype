package at.jku.cis.iVolunteer.marketplace.rule;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.rule.DerivationRule;

@Repository
public interface DerivationRuleRepository extends MongoRepository<DerivationRule, String> {

	
}

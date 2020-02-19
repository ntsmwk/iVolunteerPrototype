package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;

import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;

@Repository
public interface DerivationRuleRepository extends HasTenantRepository<DerivationRule, String> {
	
	List<DerivationRule> getByTenantId(String tenantId);
	
	DerivationRule getByIdAndTenantId(String id, String tenantId);

	
}

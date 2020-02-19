package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;

@Service
public class DerivationRuleService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private DerivationRuleMapper derivationRuleMapper;

	public List<DerivationRuleDTO> getRules(String tenantId) {
		List<DerivationRule> all = derivationRuleRepository.getByTenantId(tenantId);
		return derivationRuleMapper.toTargets(all);
	}
	
	public DerivationRuleDTO getRule(String id, String tenantId) {
		return derivationRuleMapper.toTarget(derivationRuleRepository.getByIdAndTenantId(id, tenantId));
	}

	public void createRule(DerivationRuleDTO derivationRule) {
		derivationRuleRepository.save(derivationRuleMapper.toSource(derivationRule));
	}

	public void updateRule(String id, DerivationRuleDTO derivationRule) {
		derivationRuleRepository.save(derivationRuleMapper.toSource(derivationRule));
	}

}
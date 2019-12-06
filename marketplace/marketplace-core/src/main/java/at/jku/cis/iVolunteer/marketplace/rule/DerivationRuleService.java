package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.rule.DerivationRule;

@Service
public class DerivationRuleService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;

	public List<DerivationRule> getRules() {
		return derivationRuleRepository.findAll();
	}

	public void createRule(DerivationRule derivationRule) {
		derivationRuleRepository.save(derivationRule);
	}

	public void updateRule(String id, DerivationRule derivationRule) {
		derivationRuleRepository.save(derivationRule);
	}

	
}

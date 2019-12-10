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

	public List<DerivationRuleDTO> getRules() {

		List<DerivationRule> findAll = derivationRuleRepository.findAll();
		return derivationRuleMapper.toTargets(findAll);
	}

	public void createRule(DerivationRuleDTO derivationRule) {
		derivationRuleRepository.save(derivationRuleMapper.toSource(derivationRule));
	}

	public void updateRule(String id, DerivationRuleDTO derivationRule) {
		derivationRuleRepository.save(derivationRuleMapper.toSource(derivationRule));
	}

	public DerivationRuleDTO getRule(String id) {
		return derivationRuleMapper.toTarget(derivationRuleRepository.findOne(id));
	}
}
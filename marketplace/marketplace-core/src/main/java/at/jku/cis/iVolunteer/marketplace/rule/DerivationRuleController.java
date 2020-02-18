package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;

@RestController
@RequestMapping("/rule")
public class DerivationRuleController {

	@Autowired private DerivationRuleService derivationRuleService;

	@GetMapping("tenant/{tenantId}")
	public List<DerivationRuleDTO> getRules(@PathVariable String tenantId) {
		return derivationRuleService.getRules(tenantId);
	}

	@GetMapping("/{ruleId}/tenant/{tenantId}")
	public DerivationRuleDTO getRule(@PathVariable String ruleId, @PathVariable String tenantId) {
		return derivationRuleService.getRule(ruleId, tenantId);
	}

	@PostMapping
	public void createDerivationRule(@RequestBody DerivationRuleDTO derivationRule) {
		derivationRuleService.createRule(derivationRule);
	}

	@PutMapping("/{ruleId}")
	public void updateRule(@PathVariable String ruleId, @RequestBody DerivationRuleDTO derivationRule) {
		derivationRuleService.updateRule(ruleId, derivationRule);
	}
}
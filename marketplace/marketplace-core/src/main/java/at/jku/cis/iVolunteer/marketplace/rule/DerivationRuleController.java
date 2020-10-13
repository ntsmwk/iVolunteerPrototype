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

import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
import at.jku.cis.iVolunteer.model.rule.entities.AttributeConditionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;

@RestController
@RequestMapping("/rule")
public class DerivationRuleController {

	@Autowired private DerivationRuleService derivationRuleService;

	@GetMapping("tenant/{tenantId}")
	public List<DerivationRuleDTO> getRules(@PathVariable String tenantId) {
		return derivationRuleService.getRules(tenantId);
	}

	@GetMapping("/{ruleId}")
	public DerivationRuleDTO getRule(@PathVariable String ruleId) {
		return derivationRuleService.getRule(ruleId);
	}

	@GetMapping("/tenant/{tenantId}/container/{container}/rule/{ruleName}")
	public DerivationRuleDTO getRule(@PathVariable String tenantId, @PathVariable String container,
			@PathVariable String ruleName) {
		DerivationRuleDTO dto = derivationRuleService.getRuleByContainerAndName(tenantId, container, ruleName);
		return dto;
	}

	@PostMapping
	public DerivationRuleDTO createDerivationRule(@RequestBody DerivationRuleDTO derivationRule) {
		return derivationRuleService.createRule(derivationRule);
	}

	@PostMapping(path = "/test")
	public List<RuleExecution> testDerivationRule(@RequestBody DerivationRuleDTO derivationRule) {
		return derivationRuleService.testRule(derivationRule);
	}

	@PutMapping("/{ruleId}")
	public void updateRule(@PathVariable String ruleId, @RequestBody DerivationRuleDTO derivationRule) {
		derivationRuleService.updateRule(ruleId, derivationRule);
	}

	@GetMapping("/tenant/{tenantId}/general/properties")
	public List<FlatPropertyDefinition<Object>> getGeneralProperties(@PathVariable String tenantId) {
		return derivationRuleService.getGeneralProperties(tenantId);
	}
}
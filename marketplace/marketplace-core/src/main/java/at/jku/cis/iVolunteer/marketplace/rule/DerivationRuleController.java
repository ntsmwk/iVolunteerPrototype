package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
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
		System.out.println(" get rule by id");
		return derivationRuleService.getRule(ruleId);
	}

	@GetMapping("/tenant/{tenantId}/container/{container}/rule/{ruleName}")
	public DerivationRuleDTO getRule(@PathVariable String tenantId, @PathVariable String container, @PathVariable String ruleName) {
		System.out.println("getRule - tenantId: " + tenantId + ", container: " + container + ", ruleName: " + ruleName);
		DerivationRuleDTO dto = derivationRuleService.getRuleByContainerAndName(tenantId, container, ruleName);
		System.out.println(" -------------------------------------------------------------------");
		System.out.println(" ruleId: " + dto.getId());
		return dto;
	}

	@PostMapping
	public void createDerivationRule(@RequestBody DerivationRuleDTO derivationRule) {
		derivationRuleService.createRule(derivationRule);
	}
	
	@PostMapping(path= "/test")
	public List<RuleExecution> testDerivationRule(@RequestBody DerivationRuleDTO derivationRule) {
		return derivationRuleService.testRule(derivationRule);
	}

	@PutMapping("/{ruleId}")
	public void updateRule(@PathVariable String ruleId, @RequestBody DerivationRuleDTO derivationRule) {
		System.out.println("update rule???????");
		derivationRuleService.updateRule(ruleId, derivationRule);
	}
	
	@GetMapping("/tenant/{tenantId}/general/properties")
	public List<PropertyDefinition<Object>> getGeneralProperties(@PathVariable String tenantId){
		return derivationRuleService.getGeneralProperties(tenantId);
	}
}
package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.rule.DerivationRule;

@RestController
@RequestMapping("/rule")
public class DerivationRuleController {

	@Autowired private DerivationRuleService derivationRuleService;

	@GetMapping
	public List<DerivationRule> getRules() {
		return derivationRuleService.getRules();
	}

	@PostMapping
	public void createDerivationRule(@RequestBody DerivationRule derivationRule) {
		derivationRuleService.createRule(derivationRule);
	}

	@PutMapping
	public void updateRule(@RequestParam String id, @RequestBody DerivationRule derivationRule) {
		derivationRuleService.updateRule(id, derivationRule);
	}

}

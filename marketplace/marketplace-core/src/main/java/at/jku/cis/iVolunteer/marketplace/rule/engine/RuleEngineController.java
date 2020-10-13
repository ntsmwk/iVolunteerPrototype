package at.jku.cis.iVolunteer.marketplace.rule.engine;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule/engine")
public class RuleEngineController {
	
	@Autowired private RuleService ruleService;
	
	@PutMapping("/tenant/load/all")
	public void loadAllContainers() {
	    ruleService.refreshContainer();
		ruleService.printContainers();
	}
	
	@PutMapping("/tenant/{tenantId}/refresh")
	public void loadContainers(@PathVariable String tenantId) {
		ruleService.refreshContainer(tenantId);
	}
	
	@PutMapping("/tenant/{tenantId}/{container}/refresh")
	public void loadContainer(@PathVariable String tenantId, @PathVariable String container) {
		ruleService.refreshContainer(tenantId, container);
	}
	
	// Check
	@GetMapping("/tenant/{tenantId}/{container}/session")
	public KieSession getKieSession(@PathVariable String tenantId, @PathVariable String ruleSetName) {
		return ruleService.getKieSession(tenantId, ruleSetName);
	}

	@PutMapping("/tenant/{tenantId}/{container}/execute")
	public void executeRules(@PathVariable String tenantId, @PathVariable String container) {
		switch (container) {
		case "general":
			ruleService.executeHelloWorld(tenantId, container);
			break;
		case "math":
			ruleService.executeFibonacci(tenantId, container);
			break;
		} 
	}
	
	@PutMapping("/tenant/{tenantId}/{container}/{volunteerId}/execute")
	public void executeRules(@PathVariable String tenantId, @PathVariable String container, @PathVariable String volunteerId) {
		ruleService.executeRules(tenantId, container, volunteerId);
	}
	
}
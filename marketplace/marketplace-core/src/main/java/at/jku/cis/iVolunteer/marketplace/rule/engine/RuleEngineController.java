package at.jku.cis.iVolunteer.marketplace.rule.engine;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;

@RestController
@RequestMapping("/rule/engine")
public class RuleEngineController {
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private MarketplaceService marketplaceService;
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
		if (container.equals("general"))
		   ruleService.executeRules(tenantId, container);
		if (container.equals("math"))
			ruleService.executeFibonacci(tenantId, container);
	}
	
	@PostMapping("/init")
	public void initTestData() {
		String tenantId = coreTenantRestClient.getTenantIdByName("FF_Eidenberg");
		ruleService.initTestData(tenantId);
		tenantId = coreTenantRestClient.getTenantIdByName("Musikverein_Schwertberg");
		ruleService.initTestData(tenantId);
	}
}
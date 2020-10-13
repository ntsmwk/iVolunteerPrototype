package at.jku.cis.iVolunteer.marketplace.rule.engine.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule/engine/test")
public class TestDataController {

	@PutMapping("/data/user/all")
	public void loadAllContainers() {

	}

	@PutMapping("/tenant/{tenantId}/refresh")
	public void loadContainers(@PathVariable String tenantId) {

	}

	@PutMapping("/tenant/{tenantId}/{container}/refresh")
	public void loadContainer(@PathVariable String tenantId, @PathVariable String container) {

	}

	@PutMapping("/tenant/{tenantId}/{container}/execute")
	public void executeRules(@PathVariable String tenantId, @PathVariable String container) {

	}

}
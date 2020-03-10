package at.jku.cis.iVolunteer.core.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired TenantRepository tenantRepository;

	@GetMapping("/name/{tenantName}")
	public String getTenantByName(@PathVariable String tenantName) {
		return tenantRepository.findByName(tenantName).getId();
	}

	@GetMapping("/{tenantId}")
	public Tenant getTenantById(@PathVariable String tenantId) {
		return tenantRepository.findOne(tenantId);
	}

}
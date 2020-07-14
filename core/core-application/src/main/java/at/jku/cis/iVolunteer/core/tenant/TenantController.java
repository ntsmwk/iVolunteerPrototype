package at.jku.cis.iVolunteer.core.tenant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	private TenantService tenantService;

	@GetMapping
	public List<Tenant> getAllTenants() {
		// TODO MWE tenant restrictions - public...
		return tenantService.getAllTenants();
	}

	@GetMapping("/name/{tenantName}")
	public String getTenantByName(@PathVariable String tenantName) {
		return tenantService.getTenantIdByName(tenantName);
	}

	@GetMapping("/{tenantId}")
	public Tenant getTenantById(@PathVariable String tenantId) {
		return tenantService.getTenantById(tenantId);
	}

	@GetMapping("/volunteer/{volunteerId}")
	public List<Tenant> getTenantsByVolunteer(@PathVariable String volunteerId) {
		return tenantService.getTenantsByUser(volunteerId);
	}

	@GetMapping("/marketplace/{marketplaceId}")
	public List<Tenant> getTenantsByMarketplaceIds(@PathVariable String marketplaceId) {
		return tenantService.getTenantsByMarketplaceIds(marketplaceId);
	}

	@PostMapping
	public Tenant createTenant(@RequestBody Tenant tenant) {
		return tenantService.createTenant(tenant);
	}

	@PutMapping("{tenantId}")
	public Tenant updateTenant(@PathVariable("tenantId") String tenantId, @RequestBody Tenant tenant) {
		return tenantService.updateTenant(tenantId, tenant);
	}
}
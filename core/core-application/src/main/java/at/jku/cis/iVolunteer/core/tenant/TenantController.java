package at.jku.cis.iVolunteer.core.tenant;

import java.util.List;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

//TODO xnet

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired private TenantService tenantService;
	@Autowired private LoginService loginService;
	@Autowired private TenantRepository tenantRepository;

//	 TODO /all
	@GetMapping
	public List<Tenant> getAllTenants() {
		return tenantService.getAllTenants();
	}

	@GetMapping("/subscribed")
	public List<Tenant> getSubscribedTenants() {
		List<String> tenantIds = loginService.getLoggedInUser().getSubscribedTenants().stream()
				.map(t -> t.getTenantId()).collect(Collectors.toList());
		return StreamSupport.stream(tenantRepository.findAll(tenantIds).spliterator(), false)
				.collect(Collectors.toList());
	}

	@GetMapping("/name/{tenantName}")
	public String getTenantByName(@PathVariable String tenantName) {
		return tenantService.getTenantIdByName(tenantName);
	}

	@GetMapping("/{tenantId}")
	public Tenant getTenantById(@PathVariable String tenantId) {
		return tenantService.getTenantById(tenantId);
	}

	@GetMapping("/user/{userId}")
	public List<Tenant> getTenantsByUserId(@PathVariable("userId") String userId) {
		return tenantService.getTenantsByUser(userId).stream().distinct().collect(Collectors.toList());
	}

	@GetMapping("/marketplace/{marketplaceId}")
	public List<Tenant> getTenantsByMarketplaceIds(@PathVariable String marketplaceId) {
		return tenantService.getTenantsByMarketplaceIds(marketplaceId);
	}

	// /new
	@PostMapping
	public Tenant createTenant(@RequestBody Tenant tenant) {
		return tenantService.createTenant(tenant);
	}

	// /update
	@PutMapping
	public Tenant updateTenant(@RequestBody Tenant tenant) {
		return tenantService.updateTenant(tenant);
	}
}
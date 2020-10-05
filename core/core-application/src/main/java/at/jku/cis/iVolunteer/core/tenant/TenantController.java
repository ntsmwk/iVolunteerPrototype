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
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

//TODO xnet

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired private TenantService tenantService;
	@Autowired private LoginService loginService;
	@Autowired private TenantRepository tenantRepository;
	
	/** Da core/userinfo keine Volunteers enthalten soll, sollten mit den Folgeden calls nur die Volunteers zurückgegeben werden
	 * 
	 * get core/tenant/{tid}/subscribe
	 * 	ret (200 - if sub successful; 400 if unsuccessful)
	 * 
	 * get core/tenant/{tid}/unsubscribe
	 * 	ret (200 - if unsub successful; 400 if unsuccessful)

	 * get core/tenant/subscribed
	 * ret all subscribed tenants for current user

	 * get core/tenant/unsubscribed
	 * ret all currently not subscribed tenants for current user

	 * get core/tenant/all
	 * return all tenants
	 * 
	 */

//	 TODO xnet mapping /all
	@GetMapping
	public List<Tenant> getAllTenants() {
		return tenantService.getAllTenants();
	}

	@GetMapping("/subscribed/all")
	public List<Tenant> getAllSubscribedTenants() {
		CoreUser user = loginService.getLoggedInUser();
		
		List<String> tenantIds = user.getSubscribedTenants().stream()
				.map(t -> t.getTenantId()).collect(Collectors.toList());
		
		return StreamSupport.stream(tenantRepository.findAll(tenantIds).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/unsubscribed")
	List<Tenant> getUnsubscribedTenants() {
		CoreUser user = loginService.getLoggedInUser();
		
		List<String> tenantIds = user.getSubscribedTenants().stream()
				.map(t -> t.getTenantId()).collect(Collectors.toList());
		
		return null;
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
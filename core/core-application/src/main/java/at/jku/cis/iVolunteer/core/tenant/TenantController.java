package at.jku.cis.iVolunteer.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

//TODO xnet done - test

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired private TenantService tenantService;
	@Autowired private LoginService loginService;
	@Autowired private TenantRepository tenantRepository;
	@Autowired private CoreUserService coreUserService;
	@Autowired private MarketplaceService marketplaceService;
	
	/** Da core/userinfo keine Volunteers enthalten soll, sollten mit den Folgeden calls nur die Volunteers zurückgegeben werden
	 * 
	 * put core/tenant/{tid}/subscribe
	 * 	ret (200 - if sub successful; 400 if unsuccessful)
	 * 
	 * put core/tenant/{tid}/unsubscribe
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

	@GetMapping("/subscribed")
	public List<Tenant> getSubscribedTenants() {
		CoreUser user = loginService.getLoggedInUser();
		
		List<String> tenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER))
				.map(t -> t.getTenantId()).collect(Collectors.toList());
		
		List<Tenant> ret = new ArrayList<>();
		tenantRepository.findAll(tenantIds).forEach(ret::add);
		return ret;
	}
	
	@GetMapping("/unsubscribed")
	List<Tenant> getUnsubscribedTenants() {
		CoreUser user = loginService.getLoggedInUser();
		
		List<String> subscribedTenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER))
				.map(t -> t.getTenantId()).collect(Collectors.toList());
		
		List<Tenant> allTenants = getAllTenants();
		
		List<Tenant> ret = allTenants.stream()
				.filter(t -> subscribedTenantIds.stream()
						.noneMatch(id -> t.getId().equals(id)))
				.collect(Collectors.toList());
		
		return ret;
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
	
//	 * put core/tenant/{tid}/subscribe
//	 * 	ret (200 - if sub successful; 400 if unsuccessful)
	@PutMapping("/{tenantId}/subscribe")
	public ResponseEntity<Object> subscribeToTenant(@PathVariable String tenantId, @RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);
		
		if (tenant == null) {
			return new ResponseEntity<Object>("No such tenant", HttpStatus.NOT_ACCEPTABLE);

		}
		user = coreUserService.subscribeUserToTenant(user.getId(), tenant.getMarketplaceId() , tenantId, UserRole.VOLUNTEER, authorization, true);
		
		if (user == null) {
			return new ResponseEntity<Object>("Subscribe failed", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}
	
//	 * put core/tenant/{tid}/unsubscribe
//	 * 	ret (200 - if unsub successful; 400 if unsuccessful)
	@PutMapping("/{tenantId}/unsubscribe")
	public ResponseEntity<Object> unsubscribeFromTenant(@PathVariable String tenantId, @RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			return new ResponseEntity<Object>("No such tenant", HttpStatus.NOT_ACCEPTABLE);

		}
		
		user = coreUserService.unsubscribeUserFromTenant(user.getId(), tenant.getMarketplaceId(), tenantId, UserRole.VOLUNTEER, authorization, true);
		
		if (user == null) {
			return new ResponseEntity<Object>("Unsubscribe failed", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}
	

	// /new
	@PostMapping("/new")
	public Tenant createTenant(@RequestBody Tenant tenant) {
		return tenantService.createTenant(tenant);
	}

	// /update
	@PutMapping("/update")
	public Tenant updateTenant(@RequestBody Tenant tenant) {
		return tenantService.updateTenant(tenant);
	}
	
	
}
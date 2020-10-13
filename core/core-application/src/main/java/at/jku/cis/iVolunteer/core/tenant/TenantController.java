package at.jku.cis.iVolunteer.core.tenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer._mappers.xnet.XTenantMapper;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
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
	@Autowired private XTenantMapper xTenantMapper;

	/**
	 * Da core/userinfo keine Volunteers enthalten soll, sollten mit den Folgeden
	 * calls nur die Volunteers zurückgegeben werden
	 * 
	 * put core/tenant/{tid}/subscribe ret (200 - if sub successful; 400 if
	 * unsuccessful)
	 * 
	 * put core/tenant/{tid}/unsubscribe ret (200 - if unsub successful; 400 if
	 * unsuccessful)
	 * 
	 * get core/tenant/subscribed ret all subscribed tenants for current user
	 * 
	 * get core/tenant/unsubscribed ret all currently not subscribed tenants for
	 * current user
	 * 
	 * get core/tenant/all return all tenants
	 * 
	 */

	@GetMapping
	public List<Tenant> getAllTenants() {
		return tenantService.getAllTenants();
	}

	@GetMapping("/all")
	public List<XTenant> getAllTenantsX() {
		return xTenantMapper.toTargets(tenantService.getAllTenants());
	}

	@GetMapping("/subscribed")
	public List<XTenant> getSubscribedTenantsX() {
		CoreUser user = loginService.getLoggedInUser();

		List<String> tenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER)).map(t -> t.getTenantId())
				.collect(Collectors.toList());

		List<Tenant> ret = new ArrayList<>();
		tenantRepository.findAll(tenantIds).forEach(ret::add);
		return xTenantMapper.toTargets(ret);
	}

	@GetMapping("/unsubscribed")
	public List<XTenant> getUnsubscribedTenantsX() {
		CoreUser user = loginService.getLoggedInUser();

		List<String> subscribedTenantIds = user.getSubscribedTenants().stream()
				.filter(t -> t.getRole().equals(UserRole.VOLUNTEER)).map(t -> t.getTenantId())
				.collect(Collectors.toList());

		List<Tenant> ret = getAllTenants().stream()
				.filter(t -> subscribedTenantIds.stream().noneMatch(id -> t.getId().equals(id)))
				.collect(Collectors.toList());
		return xTenantMapper.toTargets(ret);
	}

	@PostMapping("/create")
	public ResponseEntity<Void> createTenantX(@RequestBody CreateTenantPayload payload) {
		if (payload.getTenant() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Tenant tenant = xTenantMapper.toSource(payload.getTenant());
		tenant.setMarketplaceId(payload.getMarketplaceId());
		tenantService.createTenant(xTenantMapper.toSource(payload.getTenant()));
		return ResponseEntity.ok().build();
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

	// * put core/tenant/{tid}/subscribe
	// * ret (200 - if sub successful; 400 if unsuccessful)
	@PutMapping("/{tenantId}/subscribe")
	public ResponseEntity<Object> subscribeToTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such tenant"));

		}
		user = coreUserService.subscribeUserToTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Subscribe failed"));

		}

		return ResponseEntity.ok().build();
	}

	// * put core/tenant/{tid}/unsubscribe
	// * ret (200 - if unsub successful; 400 if unsuccessful)
	@PutMapping("/{tenantId}/unsubscribe")
	public ResponseEntity<Object> unsubscribeFromTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			ResponseEntity.badRequest().body(new ErrorResponse("No such tenant"));
		}

		user = coreUserService.unsubscribeUserFromTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Unsubscribe failed"));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/new")
	public ResponseEntity<?> createTenant(@RequestBody Tenant tenant) {
		if (tenant == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Tenant must not be null"));
		}
		Tenant ret = tenantService.createTenant(tenant);

		Map<String, Object> returnMap = Collections.singletonMap("id", ret.getId());
		return ResponseEntity.ok(returnMap);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateTenant(@RequestBody Tenant tenant) {
		if (tenant == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Tenant must not be null"));
		}
		tenantService.updateTenant(tenant);
		return ResponseEntity.ok().build();

	}

}
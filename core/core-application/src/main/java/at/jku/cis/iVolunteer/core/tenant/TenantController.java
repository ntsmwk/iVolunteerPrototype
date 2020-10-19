package at.jku.cis.iVolunteer.core.tenant;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import at.jku.cis.iVolunteer._mappers.xnet.XTenantMapper;
import at.jku.cis.iVolunteer._mappers.xnet.XUserRoleMapper;
import at.jku.cis.iVolunteer._mappers.xnet.XCoreUserMapper;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XUser;
import at.jku.cis.iVolunteer.model.user.XUserRole;

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
	@Autowired private XCoreUserMapper xUserMapper;
	@Autowired private XUserRoleMapper xUserRoleMapper;

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
		return xTenantMapper.toTargets(tenantService.getSubscribedTenants(user));
	}

	@GetMapping("/unsubscribed")
	public List<XTenant> getUnsubscribedTenantsX() {
		CoreUser user = loginService.getLoggedInUser();
		return xTenantMapper.toTargets(tenantService.getUnsubscribedTenants(user));
	}

	@PostMapping("/create")
	public ResponseEntity<Void> createTenantX(@RequestBody CreateTenantPayload payload) {
		if (payload.getTenant() == null) {
			return ResponseEntity.badRequest().build();
		}

		Tenant tenant = xTenantMapper.toSource(payload.getTenant());
		if (payload.getMarketplaceId() == null) {
			tenant.setMarketplaceId(marketplaceService.findFirst().getId());
		} else {
			tenant.setMarketplaceId(payload.getMarketplaceId());
		}
		tenantService.createTenant(xTenantMapper.toSource(payload.getTenant()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{tenantId}")
	public XTenant getTenantByIdX(@PathVariable String tenantId) {
		return xTenantMapper.toTarget(tenantService.getTenantById(tenantId));
	}
	
	@GetMapping("/{tenantId}/not-x")
	public Tenant getTenantByIdNotX(@PathVariable String tenantId) {
		return tenantService.getTenantById(tenantId);
	}
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Void> updateTenantX(@PathVariable String tenantId, @RequestBody XTenant tenant) {
		if (tenant == null) {
			return ResponseEntity.badRequest().build();
		}
		Tenant t = xTenantMapper.toSource(tenant);
		Tenant original = tenantService.getTenantById(tenantId);

		original = original.updateTenant(t);
		tenantService.updateTenant(original);
		return ResponseEntity.ok().build();

	}

	@PostMapping("/{tenantId}/subscribe")
	public ResponseEntity<Void> subscribeToTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			return ResponseEntity.badRequest().build();
		}
		user = coreUserService.subscribeUserToTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{tenantId}/unsubscribe")
	public ResponseEntity<Void> unsubscribeFromTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			ResponseEntity.badRequest().build();
		}

		user = coreUserService.unsubscribeUserFromTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{tenantId}/userRole")
	public List<XUserRole> getCurrentUserRolesForTenantX(@PathVariable String tenantId) {
		CoreUser user = loginService.getLoggedInUser();
		List<UserRole> list = user.getSubscribedTenants().stream().filter(ts -> ts.getTenantId().equals(tenantId))
				.map(ts -> ts.getRole()).collect(Collectors.toList());
		return xUserRoleMapper.toTargets(list);
	}

	@PostMapping("/{tenantId}/userRole/add")
	public ResponseEntity<Void> addUserRoleToTenantX(@PathVariable String tenantId, @RequestBody List<XUserRole> roles,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		if (user == null || tenant == null) {
			return ResponseEntity.badRequest().build();
		}
		List<UserRole> userRoles = xUserRoleMapper.toSources(roles);
		for (UserRole role : userRoles) {
			boolean alreadySubscribed = user.getSubscribedTenants().stream()
					.filter(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == role).findAny()
					.orElse(null) != null;
			if (alreadySubscribed) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			user.addSubscribedTenant(tenant.getMarketplaceId(), tenantId, role);
		}
		coreUserService.updateUser(user, authorization, true);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{tenantId}/userRole/remove")
	public ResponseEntity<Void> removeUserRoleToTenantX(@PathVariable String tenantId,
			@RequestBody List<XUserRole> roles, @RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		if (user == null || tenant == null) {
			return ResponseEntity.badRequest().build();
		}
		List<UserRole> userRoles = xUserRoleMapper.toSources(roles);

		for (UserRole role : userRoles) {
			boolean notSubscribed = user.getSubscribedTenants().stream()
					.filter(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == role).findAny()
					.orElse(null) == null;
			if (notSubscribed) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			user.removeSubscribedTenant(tenant.getMarketplaceId(), tenantId, role);
		}
		coreUserService.updateUser(user, authorization, true);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{tenantId}/subscribed")
	public List<XUser> getAllSubscribedUsersX(@PathVariable String tenantId) {
		List<CoreUser> users = coreUserService.findAll();
		List<CoreUser> ret = users.stream().filter(u -> {
			TenantSubscription tenantSubscription = u.getSubscribedTenants().stream()
					.filter(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == UserRole.VOLUNTEER).findAny()
					.orElse(null);
			return tenantSubscription != null;
		}).collect(Collectors.toList());
		return xUserMapper.toTargets(ret);
	}

//	END X-net Endpoints

	@GetMapping("/name/{tenantName}")
	public String getTenantByName(@PathVariable String tenantName) {
		return tenantService.getTenantIdByName(tenantName);
	}

	@GetMapping("/id/{tenantId}")
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
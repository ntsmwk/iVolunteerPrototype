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

import at.jku.cis.iVolunteer._mappers.xnet.XCoreUserMapper;
import at.jku.cis.iVolunteer._mappers.xnet.XTenantMapper;
import at.jku.cis.iVolunteer._mappers.xnet.XUserRoleMapper;
import at.jku.cis.iVolunteer.core.badge.XCoreBadgeTemplateService;
import at.jku.cis.iVolunteer.core.configurator.ConfiguratorRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httprequests.InitConfiguratorRequest;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model._httpresponses.XTenantSubscribedResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.meta.core.property.Tuple;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XUser;
import at.jku.cis.iVolunteer.model.user.XUserRole;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private CoreUserService coreUserService;
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private XTenantMapper xTenantMapper;
	@Autowired
	private XCoreUserMapper xUserMapper;
	@Autowired
	private XUserRoleMapper xUserRoleMapper;
	@Autowired
	private XCoreBadgeTemplateService coreBadgeTemplateService;
	@Autowired
	private ConfiguratorRestClient configuratorRestClient;

	@GetMapping
	public List<Tenant> getAllTenants() {
		return tenantService.getAllTenants();
	}

	@GetMapping("/all")
	public List<XTenant> getAllTenantsX() {
		List<Tenant> tenants = tenantService.getAllTenants();
		return tenantService.toXTenantTargets(tenants);
	}

	@GetMapping("/subscribed")
	public List<XTenant> getSubscribedTenantsX() {
		CoreUser user = loginService.getLoggedInUser();
		List<Tenant> tenants = tenantService.getSubscribedTenants(user);

		return tenantService.toXTenantTargets(tenants);
	}

	@GetMapping("/unsubscribed")
	public List<XTenant> getUnsubscribedTenantsX() {
		CoreUser user = loginService.getLoggedInUser();
		List<Tenant> tenants = tenantService.getUnsubscribedTenants(user);

		return tenantService.toXTenantTargets(tenants);
	}

	@PostMapping("/new")
	public ResponseEntity<Object> createTenantX(@RequestBody CreateTenantPayload payload,
			@RequestHeader("Authorization") String authorization) {
		XTenant xTenant = payload.getTenant();
		if (xTenant == null) {
			return ResponseEntity.badRequest().build();
		}
		if (xTenant.getName() == null || xTenant.getAbbreviation() == null) {
			return ResponseEntity.badRequest()
					.body(new StringResponse("Tenant does not contain name or abbreviation."));
		}
		if (tenantService.getTenantByName(xTenant.getName()) != null) {
			return ResponseEntity.badRequest().body(new StringResponse("Tenant with same name already exists."));
		}

		Tenant tenant = xTenantMapper.toSource(xTenant);
		if (tenant.getMarketplaceId() == null) {
			if (payload.getMarketplaceId() == null) {
				tenant.setMarketplaceId(marketplaceService.findFirst().getId());
			} else {
				tenant.setMarketplaceId(payload.getMarketplaceId());
			}
		}
		tenant = tenantService.createTenant(tenant);

		CoreUser user = loginService.getLoggedInUser();
		Marketplace marketplace = marketplaceService.findById(tenant.getMarketplaceId());

		this.coreUserService.subscribeUserToTenant(user.getId(), marketplace.getId(), tenant.getId(),
				UserRole.TENANT_ADMIN, authorization, true);
		coreBadgeTemplateService.createBadgeTemplates(marketplace, tenant);

		InitConfiguratorRequest body = new InitConfiguratorRequest();
		body.setTenantIds(Collections.singletonList(new Tuple<>(tenant.getId(), tenant.getName())));
		body.setMpUrl(marketplace.getUrl());
		configuratorRestClient.initConfigurator(body, "iVolunteer");

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{tenantId}")
	public XTenantSubscribedResponse getTenantByIdX(@PathVariable String tenantId) {
		List<CoreUser> subscribedUsers = tenantService.getSubscribedUsers(tenantId);
		XTenantSubscribedResponse tenant = xTenantMapper
				.toTenantSubscribedResponse(tenantService.getTenantById(tenantId), subscribedUsers);
		CoreUser loggedInUser = loginService.getLoggedInUser();

		boolean alreadySubscribed = subscribedUsers.stream().anyMatch(u -> u.getId().equals(loggedInUser.getId()));
		tenant.setSubscribed(alreadySubscribed);
		return tenant;
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
	public ResponseEntity<Object> subscribeToTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			return ResponseEntity.notFound().build();
		}
		boolean alreadySubscribed = user.getSubscribedTenants().stream()
				.anyMatch(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == UserRole.VOLUNTEER);

		if (alreadySubscribed) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new StringResponse("User is already subscribed to tenant " + tenantId));
		}

		user = coreUserService.subscribeUserToTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new StringResponse("Could not subscribed to tenant " + tenantId));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{tenantId}/unsubscribe")
	public ResponseEntity<Object> unsubscribeFromTenant(@PathVariable String tenantId,
			@RequestHeader("Authorization") String authorization) {
		CoreUser user = loginService.getLoggedInUser();
		Tenant tenant = getTenantById(tenantId);

		if (tenant == null) {
			return ResponseEntity.notFound().build();
		}

		boolean alreadySubscribed = user.getSubscribedTenants().stream()
				.anyMatch(ts -> ts.getTenantId().equals(tenantId) && ts.getRole() == UserRole.VOLUNTEER);

		if (!alreadySubscribed) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new StringResponse("User is not subscribed to tenant " + tenantId));
		}

		user = coreUserService.unsubscribeUserFromTenant(user.getId(), tenant.getMarketplaceId(), tenantId,
				UserRole.VOLUNTEER, authorization, true);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new StringResponse("Could not subscribed to tenant " + tenantId));
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
		List<CoreUser> ret = tenantService.getSubscribedUsers(tenantId);
		return xUserMapper.toTargets(ret);
	}

	// END X-net Endpoints

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

	@PostMapping("/new/not-x")
	public ResponseEntity<?> createTenant(@RequestBody Tenant tenant) {
		if (tenant == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL));
		}
		Tenant ret = tenantService.createTenant(tenant);

		Marketplace mp = marketplaceService.findById(tenant.getMarketplaceId());

		InitConfiguratorRequest body = new InitConfiguratorRequest();
		body.setTenantIds(Collections.singletonList(new Tuple<>(tenant.getId(), tenant.getName())));
		body.setMpUrl(mp.getUrl());
		configuratorRestClient.initConfigurator(body, "iVolunteer");
		Map<String, Object> returnMap = Collections.singletonMap("id", ret.getId());

		return ResponseEntity.ok(returnMap);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateTenant(@RequestBody Tenant tenant) {
		if (tenant == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL));
		}
		tenantService.updateTenant(tenant);
		return ResponseEntity.ok().build();

	}

}
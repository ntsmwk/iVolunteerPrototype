package at.jku.cis.iVolunteer.core.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;


//TODO xnet done - test
@RestController
public class CoreUserController {

	@Autowired private CoreUserService coreUserService;

	@Autowired private CoreLoginService coreLoginService;
	@Autowired private TenantService tenantService;

	@GetMapping("/user/all")
	private List<CoreUser> findAll() {
		return coreUserService.findAll();
	}

	@GetMapping("/user/all/tenant/{tenantId}")
	private List<CoreUser> getAllByTenantId(@PathVariable("tenantId") String tenantId) {
		return coreUserService.getAllByTenantId(tenantId);
	}

	@GetMapping("/user/all/role/{role}")
	private List<CoreUser> getAllByUserRole(@PathVariable("role") UserRole userRole) {
		return coreUserService.getAllByUserRole(userRole);
	}

	@PutMapping("/user/all/roles")
	private List<CoreUser> getAllByUserRoles(@RequestBody List<UserRole> roles,
			@RequestParam("includeNoRole") boolean includeNoRole) {
		return coreUserService.getAllByUserRoles(roles, includeNoRole);
	}

	@GetMapping("/user/all/role/{role}/tenant/{tenantId}")
	private List<CoreUser> getAllByTenantIdAndUserRole(@PathVariable("role") UserRole userRole,
			@PathVariable("tenantId") String tenantId) {

		return coreUserService.getAllByTenantIdAndUserRole(userRole, tenantId);
	}

	@GetMapping("/user/{userId}")
	private CoreUser getByUserId(@PathVariable("userId") String userId) {
		return coreUserService.getByUserId(userId);
	}

	@GetMapping("/user/name/{username}")
	private CoreUser getByUserName(@PathVariable("username") String username) {
		return coreUserService.getByUserName(username);
	}

	@GetMapping("/user/find-by-ids")
	private List<CoreUser> getByUserId(@RequestBody List<String> userIds) {
		return coreUserService.getByUserId(userIds);
	}

	@GetMapping("/user/{userId}/marketplaces")
	private List<Marketplace> findRegisteredMarketplaces(@PathVariable("userId") String userId) {
		return coreUserService.findRegisteredMarketplaces(userId);
	}

	@PostMapping("/user/{userId}/register/{marketplaceId")
	private CoreUser registerToMarketplace(@PathVariable("userId") String userId,
			@PathVariable("marketplaceId") String marketplaceId, @RequestHeader("Authorization") String authorization) {
		return coreUserService.registerToMarketplace(userId, marketplaceId, authorization);
	}

//	@PostMapping("/user/new")
//	private String addNewUser(@RequestBody CoreUser user, @RequestHeader("Authorization") String authorization,
//			@RequestParam(value = "updateMarketplaces", required = false) boolean updateMarketplaces) {
//		CoreUser ret = coreUserService.addNewUser(user, authorization, updateMarketplaces);
//		return ret.getId();
//	}

//	return 200er oder 400 - kein user falls möglich
	@PutMapping("/user/update")
	private ResponseEntity<Object> updateUser(@RequestBody CoreUser user, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "updateMarketplaces", required = false) boolean updateMarketplaces) {
		
		if (user == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("User must not be null"));
		}
		
		user = coreUserService.updateUser(user, authorization, updateMarketplaces);
		
		return ResponseEntity.ok().build();
	}

	@PutMapping("/user/subscribe/{tenantId}")
	private ResponseEntity<Object> subscribeUserToTenant(@PathVariable("tenantId") String tenantId, @RequestHeader("Authorization") String authorization, @RequestBody String role) {
		CoreUser user = coreLoginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		
		if (user == null || tenant == null || role == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user and / or tenant and / or role"));
		}
		
		user = coreUserService.subscribeUserToTenant(user.getId(), tenant.getMarketplaceId(), tenantId, UserRole.getUserRole(role), authorization, true);
		return ResponseEntity.ok().build();

	}

	@PutMapping("/user/unsubscribe/{tenantId}")
	private ResponseEntity<Object> unsubscribeUserFromTenant(@PathVariable("tenantId") String tenantId,
			@RequestBody String role, @RequestHeader("Authorization") String authorization) {
		CoreUser user = coreLoginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		
		if (user == null || tenant == null || role == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user and / or tenant and / or role"));
		}
		
		user = coreUserService.unsubscribeUserFromTenant(user.getId(), tenant.getMarketplaceId(), tenantId, UserRole.getUserRole(role), authorization, true);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/user/subscribe/{tenantId}/user/{userId}")
	private ResponseEntity<Object> subscribeOtherUserToTenant(@PathVariable("tenantId") String tenantId, @PathVariable("userId") String userId, 
			@RequestHeader("Authorization") String authorization, @RequestBody String role) {
		CoreUser user = coreLoginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		if (user == null || tenant == null || role == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user and / or tenant and / or role"));
		}
		if (user.getSubscribedTenants().stream().noneMatch(tus -> tus.getTenantId().equals(tenantId) && (tus.getRole().equals(UserRole.TENANT_ADMIN) || tus.getRole().equals(UserRole.ADMIN)))) {
			return new ResponseEntity<Object>(new ErrorResponse("Only (tenant) admins my change other users subscriptions"), HttpStatus.FORBIDDEN);
		}
		
		CoreUser changeUser = getByUserId(userId);

		if (changeUser == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user"));
		}
		
		changeUser = coreUserService.subscribeUserToTenant(changeUser.getId(), tenant.getMarketplaceId(), tenantId, UserRole.getUserRole(role), authorization, true);
		return ResponseEntity.ok().build();

	}

	@PutMapping("/user/unsubscribe/{tenantId}/user/{userId}")
	private ResponseEntity<Object> unsubscribeOtherUserFromTenant(@PathVariable("tenantId") String tenantId, @PathVariable("userId") String userId, 
			@RequestBody String role, @RequestHeader("Authorization") String authorization) {
		CoreUser user = coreLoginService.getLoggedInUser();
		Tenant tenant = tenantService.getTenantById(tenantId);
		
		if (user == null || tenant == null || role == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user and / or tenant and / or role"));
		}
		if (user.getSubscribedTenants().stream().noneMatch(tus -> tus.getTenantId().equals(tenantId) && (tus.getRole().equals(UserRole.TENANT_ADMIN) || tus.getRole().equals(UserRole.ADMIN)))) {
			return new ResponseEntity<Object>(new ErrorResponse("Only (tenant) admins my change other users subscriptions"), HttpStatus.FORBIDDEN);
		}
		
		CoreUser changeUser = getByUserId(userId);

		if (changeUser == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("No such user"));
		}		
		user = coreUserService.unsubscribeUserFromTenant(changeUser.getId(), tenant.getMarketplaceId(), tenantId, UserRole.getUserRole(role), authorization, true);
		return ResponseEntity.ok().build();
	}

}
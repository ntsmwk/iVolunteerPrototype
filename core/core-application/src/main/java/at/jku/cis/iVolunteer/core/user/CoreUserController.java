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
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;


//TODO xnet done - test
@RestController
public class CoreUserController {

	@Autowired private CoreUserService coreUserService;

	@Autowired CoreLoginService coreLoginService;

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
			return new ResponseEntity<Object>("User must not be null", HttpStatus.NOT_ACCEPTABLE);
		}
		
		user = coreUserService.updateUser(user, authorization, updateMarketplaces);
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/subscribe/{marketplaceId}/{tenantId}/{role}")
	private CoreUser subscribeUserToTenant(@PathVariable("userId") String userId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
			@PathVariable("role") UserRole role, @RequestHeader("Authorization") String authorization) {
		return coreUserService.subscribeUserToTenant(userId, marketplaceId, tenantId, role, authorization, true);
	}

	@PutMapping("/user/{userId}/unsubscribe/{marketplaceId}/{tenantId}/{role}")
	private CoreUser unsubscribeUserFromTenant(@PathVariable("userId") String userId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
			@PathVariable("role") UserRole role, @RequestHeader("Authorization") String authorization) {
		return coreUserService.unsubscribeUserFromTenant(userId, marketplaceId, tenantId, role, authorization, true);
	}

}
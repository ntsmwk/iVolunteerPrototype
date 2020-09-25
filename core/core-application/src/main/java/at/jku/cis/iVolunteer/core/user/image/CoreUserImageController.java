package at.jku.cis.iVolunteer.core.user.image;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.image.UserImage;
import at.jku.cis.iVolunteer.model.user.UserRole;

@RestController
public class CoreUserImageController {

	@Autowired private CoreUserImageRepository userImageRepository;
	@Autowired private CoreUserService coreUserService;

	
	
	@GetMapping("/user/image/all")
	private List<UserImage> getAll() {
		return userImageRepository.findAll();
	}

	@GetMapping("/user/image/tenant/{tenantId}")
	private List<UserImage> getAllByTenantId(@PathVariable("tenantId") String tenantId) {
		List<CoreUser> users = coreUserService.getAllByTenantId(tenantId);
		if (users == null) { return null;}
		
		List<String> userIds = getUserIdsFromUsers(users);
		return getByUserIds(userIds);
	}

	@GetMapping("/user/image/role/{role}")
	private List<UserImage> getAllByUserRole(@PathVariable("role") UserRole userRole) {
		List<CoreUser> users = coreUserService.getAllByUserRole(userRole);
		if (users == null) {return null;}	
		
		List<String> userIds = getUserIdsFromUsers(users);
		return getByUserIds(userIds);
	}

	@PutMapping("/user/image/roles")
	private List<UserImage> getAllByUserRoles(@RequestBody List<UserRole> roles, @RequestParam("includeNoRole") boolean includeNoRole) {
		List<CoreUser> users = coreUserService.getAllByUserRoles(roles, includeNoRole);
		if (users == null) {return null;}	
		
		List<String> userIds = getUserIdsFromUsers(users);
		return getByUserIds(userIds);
	}

	@GetMapping("/user/image/role/{role}/tenant/{tenantId}")
	private List<UserImage> getAllByTenantIdAndUserRole(@PathVariable("role") UserRole userRole,
			@PathVariable("tenantId") String tenantId) {
		List<CoreUser> users = coreUserService.getAllByTenantIdAndUserRole(userRole, tenantId);
		if (users == null) {return null;}	
		
		List<String> userIds = getUserIdsFromUsers(users);
		return getByUserIds(userIds);
	}

	@GetMapping("/user/image/{userId}")
	private UserImage getByUserId(@PathVariable("userId") String userId) {
		return this.userImageRepository.findOne(userId);
	}
	
	@GetMapping("/user/image/multiple")
	private List<UserImage> getByUserIds(@RequestBody List<String> userIds) {
		List<UserImage> images = new ArrayList<>();
		userImageRepository.findAll(userIds).forEach(images::add);
		return images;
	}

	@PostMapping("/user/image/new")
	public UserImage addNewUserImage(@RequestBody UserImage userImage) {
		return userImageRepository.save(userImage);
	}

	@PutMapping("/user/image/update")
	private UserImage updateUserImage(@RequestBody UserImage userImage) {
		return userImageRepository.save(userImage);
	}
	
	@DeleteMapping("/user/image/{userId}")
	private void deleteUserImage(@PathVariable("userId") String userId) {
		userImageRepository.delete(userId);
	}
	
	private List<String> getUserIdsFromUsers(List<CoreUser> users) {
		List<String> userIds = users.stream().map(m -> m.getId()).collect(Collectors.toList());
		return userIds;
	}

	

}
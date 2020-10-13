package at.jku.cis.iVolunteer.core.image;

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
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.image.Image;
import at.jku.cis.iVolunteer.model.image.UserImage;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Deprecated
@RestController
public class ImageController {

	@Autowired private ImageService imageService;
	@Autowired private UserImageRepository userImageRepository;
	@Autowired private LoginService loginService;
	@Autowired private CoreUserService coreUserService;
	@Autowired private ImageRepository imageRepository;

	@GetMapping("/image/{imageId}")
	private Image findByImageId(@PathVariable String imageId) {
		CoreUser loggedInUser = loginService.getLoggedInUser();
		Image image = this.imageService.findByImageId(imageId);
		if (image instanceof UserImage) {
			String imageUserId = ((UserImage) image).getUserId();
			if (imageUserId == null || !imageUserId.equals(loggedInUser.getId())) {
				return null;
			}
		}
		return image;
	}

	@GetMapping("/image/role/{role}/tenant/{tenantId}")
	public List<UserImage> getAllByTenantIdAndUserRole(@PathVariable("role") UserRole userRole,
			@PathVariable("tenantId") String tenantId) {
		List<CoreUser> users = coreUserService.getAllByTenantIdAndUserRole(userRole, tenantId);
		List<String> userIds = getUserIdsFromUsers(users);
		return getByUserIds(userIds);
	}

	@PostMapping("/image/new")
	public Image addNewImage(@RequestBody Image image) {
		return imageRepository.save(image);
	}

	@PutMapping("/image/update")
	private Image updateUserImage(@RequestBody Image image) {
		return imageRepository.save(image);
	}

	@DeleteMapping("/image/{imageId}")
	private void deleteUserImage(@PathVariable("imageId") String imageId) {
		imageRepository.delete(imageId);
	}

	private List<UserImage> getByUserIds(@RequestBody List<String> userIds) {
		List<UserImage> images = new ArrayList<>();
		userImageRepository.findAll(userIds).forEach(images::add);
		return images;
	}

	private List<String> getUserIdsFromUsers(List<CoreUser> users) {
		List<String> userIds = users.stream().map(m -> m.getId()).collect(Collectors.toList());
		return userIds;
	}

}

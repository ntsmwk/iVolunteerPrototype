package at.jku.cis.iVolunteer.core.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.image.Image;
import at.jku.cis.iVolunteer.model.image.UserImage;

@RestController
public class ImageController {

	@Autowired private ImageService imageService;
	@Autowired private LoginService loginService;

	@GetMapping("/image/{imageId}")
	private Image findByImageId(@PathVariable String imageId) {
		CoreUser loggedInUser = loginService.getLoggedInUser();
		Image image = this.imageService.findByImageId(imageId);
		if (image instanceof UserImage) {
			String imageUserId = ((UserImage) image).getUserId();
			if (imageUserId != null && imageUserId.equals(loggedInUser.getId())) {
				return image;
			} else {
				return null;
			}
		} else {
			return image;
		}
	}

}

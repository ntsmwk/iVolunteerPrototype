package at.jku.cis.iVolunteer.core.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.image.Image;

@Deprecated
@Service
public class ImageService {
	@Autowired private ImageRepository imageRepository;
	@Autowired private LoginService loginService;

	public Image findByImageId(String imageId) {
		CoreUser user = loginService.getLoggedInUser();
		return imageRepository.findOne(imageId);
	}

}

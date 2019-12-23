package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.user.UserImagePath;

@RestController
public class CoreUserImagePathController {

	@Autowired UserImagePathRepository userImagePathRepository;
	
	@GetMapping("/user/image/all")
	public List<UserImagePath> getAllUserImagePaths() {
		return userImagePathRepository.findAll();
	}
	
	@GetMapping("/user/image/{userId}")
	public UserImagePath getUserImagePath(@PathVariable("userId") String userId) {
		return userImagePathRepository.findOne(userId);
	}
	
	@PutMapping("/user/image/get-multiple")
	public List<UserImagePath> getMultipleUserImagePaths(@RequestBody List<String> userIds) {
		List<UserImagePath> returnList = new ArrayList<UserImagePath>();
		userImagePathRepository.findAll(userIds).forEach(returnList::add);;
		return returnList;
	}
	
	@PostMapping("/user/image/save")
	public UserImagePath saveUserImagePath(@RequestBody UserImagePath imagePath) {
		return userImagePathRepository.save(imagePath);
	}
	
	@DeleteMapping("/user/image/{userId}")
	public void deleteUserImagePath(@PathVariable("userId") String userId) {
		userImagePathRepository.delete(userId);
	}

}

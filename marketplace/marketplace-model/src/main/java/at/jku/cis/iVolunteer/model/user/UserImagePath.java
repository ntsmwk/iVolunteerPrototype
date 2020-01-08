package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserImagePath {
	@Id private String userId;
	private String imagePath;

	public UserImagePath() {

	}

	public UserImagePath(String userId, String imagePath) {
		this.userId = userId;
		this.imagePath = imagePath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
	
}

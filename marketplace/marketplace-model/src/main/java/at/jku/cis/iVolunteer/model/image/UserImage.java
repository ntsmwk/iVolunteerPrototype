package at.jku.cis.iVolunteer.model.image;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image")
public class UserImage extends Image {
	private String userId;

	public UserImage() {

	}

	public UserImage(String userId, ImageWrapper image) {
		this.userId = userId;
		this.setImageWrapper(image);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

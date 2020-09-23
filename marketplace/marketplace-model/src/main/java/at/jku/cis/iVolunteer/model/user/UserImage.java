package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.image.ImageWrapper;

@Document
public class UserImage {
	@Id private String userId;
	private ImageWrapper image;

	public UserImage() {

	}

	public UserImage(String userId, ImageWrapper image) {
		this.userId = userId;
		this.image = image;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ImageWrapper getImage() {
		return image;
	}

	public void setImage(ImageWrapper image) {
		this.image = image;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof UserImage)) {
			return false;
		}
		return ((UserImage) obj).userId.equals(userId);
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}
	
	
	
	
}

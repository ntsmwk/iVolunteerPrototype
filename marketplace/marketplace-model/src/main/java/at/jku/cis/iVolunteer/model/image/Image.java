package at.jku.cis.iVolunteer.model.image;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Image {

	@Id private String id;
	private ImageWrapper imageWrapper;

	public Image() {

	}
	
	public Image(ImageWrapper imageWrapper) {
		this.imageWrapper = imageWrapper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ImageWrapper getImageWrapper() {
		return imageWrapper;
	}

	public void setImageWrapper(ImageWrapper imageWrapper) {
		this.imageWrapper = imageWrapper;
	}

}

package at.jku.cis.iVolunteer.model._httprequests;

public class Base64ImageUploadRequest {
	String image;
	String filename;
	
	public Base64ImageUploadRequest() {}
	public Base64ImageUploadRequest(String filename, String image) {
		this.filename = filename;
		this.image = image;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}

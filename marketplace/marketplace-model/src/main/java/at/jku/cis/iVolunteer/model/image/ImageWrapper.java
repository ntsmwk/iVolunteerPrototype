package at.jku.cis.iVolunteer.model.image;

public class ImageWrapper {

	private byte[] data;
	private String imageInfo;

	public ImageWrapper() {
	}

	public ImageWrapper(String imageDate) {
	}

	public ImageWrapper(String imageInfo, byte[] data) {
		this.data = data;
		this.imageInfo = imageInfo;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

}

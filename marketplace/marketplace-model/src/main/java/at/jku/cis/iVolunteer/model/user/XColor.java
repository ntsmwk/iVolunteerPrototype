package at.jku.cis.iVolunteer.model.user;


public class XColor {
	private String name;
	private String hex;
	private String rgb;

	public XColor() {
	}
	
	public XColor(String ourColor) {
		this.hex = ourColor;
	}

	public XColor(String name, String hex, String rgb) {
		this.name = name;
		this.hex = hex;
		this.rgb = rgb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

}

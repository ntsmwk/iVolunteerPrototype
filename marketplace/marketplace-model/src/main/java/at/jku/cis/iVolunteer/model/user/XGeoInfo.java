package at.jku.cis.iVolunteer.model.user;

import at.jku.cis.iVolunteer.model.meta.core.property.Location;

public class XGeoInfo {
	String name;
	Double latitude;
	Double longitude;

	public XGeoInfo() {
	}

	public XGeoInfo(String name, Double latitude, Double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public XGeoInfo(Location location) {
		if (location != null) {
			this.name = location.getLabel();
			if (location.isLongLatEnabled()) {
				this.latitude = location.getLatitude();
				this.longitude = location.getLongitude();
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}

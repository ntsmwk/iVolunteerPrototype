package at.jku.cis.iVolunteer.model.meta.core.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.task.GeoInformation;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

@Document
public class Location {

	private String label;
	private boolean longLatEnabled;
	private Double latitude;
	private Double longitude;

	public Location(String label, boolean longLatEnabled, double latitude, double longitude) {
		this.label = label;
		this.longLatEnabled = longLatEnabled;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location(String taskLocation, GeoInformation taskGeoInformation) {
		this.label = taskLocation;
		if (taskGeoInformation != null) {
			this.longLatEnabled = taskGeoInformation.isEnabled();
			this.latitude = taskGeoInformation.getLatitude();
			this.longitude = taskGeoInformation.getLongitude();
		}
	}

	public Location(String taskLocation) {
		this.label = taskLocation;
		this.longLatEnabled = false;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}

	public Location(XGeoInfo geoInfo) {
		if (geoInfo != null) {
			this.label = geoInfo.getName();
			this.longLatEnabled = geoInfo.getLatitude() != null && geoInfo.getLongitude() != null;
			this.longitude = geoInfo.getLongitude();
			this.latitude = geoInfo.getLatitude();
		}
	}

	public Location() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isLongLatEnabled() {
		return longLatEnabled;
	}

	public void setLongLatEnabled(boolean longLatEnabled) {
		this.longLatEnabled = longLatEnabled;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}

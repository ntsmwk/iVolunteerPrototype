package at.jku.cis.iVolunteer.configurator.model.meta.core.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Location {

	private String label;
	private boolean longLatEnabled;
	private double latitude;
	private double longitude;

	public Location(String label, boolean longLatEnabled, double latitude, double longitude) {
		this.label = label;
		this.longLatEnabled = longLatEnabled;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location() {}

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

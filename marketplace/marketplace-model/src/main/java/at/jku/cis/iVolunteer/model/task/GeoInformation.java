package at.jku.cis.iVolunteer.model.task;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GeoInformation {

	private boolean enabled;
	private double latitude;
	private double longitude;
	private int gridID;

	public GeoInformation() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public int getGridID() {
		return gridID;
	}

	public void setGridID(int gridID) {
		this.gridID = gridID;
	}

}

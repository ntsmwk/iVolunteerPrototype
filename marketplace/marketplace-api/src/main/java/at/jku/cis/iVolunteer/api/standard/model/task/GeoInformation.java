package at.jku.cis.iVolunteer.api.standard.model.task;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GeoInformation {

	private boolean enabled;
	private String latitude;
	private String longitude;
	private int gridID;

	public GeoInformation() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getGridID() {
		return gridID;
	}

	public void setGridID(int gridID) {
		this.gridID = gridID;
	}

}

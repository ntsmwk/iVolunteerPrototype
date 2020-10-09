package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.user.XGeoInfo;

public class XTaskSerialized {

	

	String id;
	String title;
	String description;
	Date startDate;
	Date endDate;
	String imagePath;
	boolean closed;
	XGeoInfo geoInfo;
	List<XDynamicField> dynamicFields = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public XGeoInfo getGeoInfo() {
		return geoInfo;
	}
	public void setGeoInfo(XGeoInfo geoInfo) {
		this.geoInfo = geoInfo;
	}
	public List<XDynamicField> getDynamicFields() {
		return dynamicFields;
	}
	public void setDynamicFields(List<XDynamicField> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}
	
	
	
}

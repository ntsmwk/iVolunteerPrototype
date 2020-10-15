package at.jku.cis.iVolunteer.model.core.tenant;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.user.XColor;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

public class XTenantSerialized {

	
	String id;
	String name;
	String abbreviation;
	String description;
	String homepage;
	String imagePath;
	String primaryColor;
	String secondaryColor;
	List<String> tags = new ArrayList<>();
	XGeoInfo geoInfo;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getPrimaryColor() {
		return primaryColor;
	}
	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}
	public String getSecondaryColor() {
		return secondaryColor;
	}
	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public XGeoInfo getGeoInfo() {
		return geoInfo;
	}
	public void setGeoInfo(XGeoInfo geoInfo) {
		this.geoInfo = geoInfo;
	}
	
	
}

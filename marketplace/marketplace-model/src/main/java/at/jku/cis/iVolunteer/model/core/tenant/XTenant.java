package at.jku.cis.iVolunteer.model.core.tenant;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.user.XColor;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;
import at.jku.cis.iVolunteer.model.user.XUser;

public class XTenant {

	private String id;
	private String name;
	private String abbreviation;
	private String description;
	private String homepage;
	private String imagePath;
	private String primaryColor;
	private String secondaryColor;
	private String marketplaceUrl;
	private List<String> tags = new ArrayList<>();
	private String landingpageMessage;
	private String landingpageTitle;
	private String landingpageText;
	private String landingpageImagePath;
	private XGeoInfo geoInfo;
	private List<XUser> subscribedVolunteers;
	
	public XTenant() {
	}

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

	public String getLandingpageMessage() {
		return landingpageMessage;
	}

	public void setLandingpageMessage(String landingpageMessage) {
		this.landingpageMessage = landingpageMessage;
	}

	public String getLandingpageTitle() {
		return landingpageTitle;
	}

	public void setLandingpageTitle(String landingpageTitle) {
		this.landingpageTitle = landingpageTitle;
	}

	public String getLandingpageText() {
		return landingpageText;
	}

	public void setLandingpageText(String landingpageText) {
		this.landingpageText = landingpageText;
	}

	public String getLandingpageImagePath() {
		return landingpageImagePath;
	}

	public void setLandingpageImagePath(String landingpageImagePath) {
		this.landingpageImagePath = landingpageImagePath;
	}

	public XGeoInfo getGeoInfo() {
		return geoInfo;
	}

	public void setGeoInfo(XGeoInfo geoInfo) {
		this.geoInfo = geoInfo;
	}

	public List<XUser> getSubscribedVolunteers() {
		return subscribedVolunteers;
	}

	public void setSubscribedVolunteers(List<XUser> subscribedVolunteers) {
		this.subscribedVolunteers = subscribedVolunteers;
	}

	public String getMarketplaceUrl() {
		return marketplaceUrl;
	}

	public void setMarketplaceUrl(String marketplaceUrl) {
		this.marketplaceUrl = marketplaceUrl;
	}

}

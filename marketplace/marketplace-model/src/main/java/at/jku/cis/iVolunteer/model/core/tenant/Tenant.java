package at.jku.cis.iVolunteer.model.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.user.Address;

public class Tenant {

	@Id private String id;
	private String name;
	private String description;
	private String abbreviation;
	
	private String homepage;

	private String imagePath;

	private String primaryColor;
	private String secondaryColor;

	private String marketplaceId;
	
	private Address address;
	private Location location;

	private List<String> tags = new ArrayList<>();

	private String landingpageTitle;
	private String landingpageMessage;
	private String landingpageText;
	private String landingpageImagePath;

	private List<SubscriptionRule> subscriptionRules = new ArrayList<>();

	
	public Tenant updateTenant(Tenant update) {
		this.name = update.getName() != null ? update.getName() : this.name;
		this.description = update.getDescription() != null ? update.getDescription() : this.description;
		this.homepage = update.getHomepage() != null ? update.getHomepage() : this.homepage;
		this.imagePath = update.getImagePath() != null ? update.getImagePath() : this.imagePath;
		this.primaryColor = update.getPrimaryColor() != null ? update.getPrimaryColor() : this.primaryColor;
		this.secondaryColor = update.getSecondaryColor() != null ? update.getSecondaryColor() : this.secondaryColor;
		this.marketplaceId = update.getMarketplaceId() != null ? update.getMarketplaceId() : this.marketplaceId;
		this.landingpageTitle = update.getLandingpageTitle() != null ? update.getLandingpageTitle() : this.landingpageTitle;
		this.landingpageMessage = update.getLandingpageMessage() != null ? update.getLandingpageMessage() : this.landingpageMessage;
		this.landingpageText = update.getLandingpageText() != null ? update.getLandingpageText() : this.landingpageText;
		this.landingpageImagePath = update.getLandingpageImagePath() != null ? update.getLandingpageImagePath() : this.landingpageImagePath;
		this.abbreviation = update.getAbbreviation() != null ? update.getAbbreviation() : this.abbreviation;
		this.address = update.getAddress() != null ? update.getAddress() : this.address;
		this.location = update.getLocation() != null ? update.getLocation() : this.location;
		this.tags = update.getTags() != null ? update.getTags() : this.tags;
		return this;
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

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getLandingpageTitle() {
		return landingpageTitle;
	}

	public void setLandingpageTitle(String landingpageTitle) {
		this.landingpageTitle = landingpageTitle;
	}

	public String getLandingpageMessage() {
		return landingpageMessage;
	}

	public void setLandingpageMessage(String landingpageMessage) {
		this.landingpageMessage = landingpageMessage;
	}

	public String getLandingpageText() {
		return landingpageText;
	}

	public void setLandingpageText(String landingpageText) {
		this.landingpageText = landingpageText;
	}

	public List<SubscriptionRule> getSubscriptionRules() {
		return subscriptionRules;
	}

	public void setSubscriptionRules(List<SubscriptionRule> subscriptionRules) {
		this.subscriptionRules = subscriptionRules;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLandingpageImagePath() {
		return landingpageImagePath;
	}

	public void setLandingpageImagePath(String landingpageImagePath) {
		this.landingpageImagePath = landingpageImagePath;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Tenant)) {
			return false;
		}
		Tenant tenant = (Tenant) o;
		return Objects.equals(id, tenant.id) && Objects.equals(name, tenant.name)
				&& Objects.equals(marketplaceId, tenant.marketplaceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, marketplaceId);
	}



}

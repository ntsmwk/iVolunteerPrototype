package at.jku.cis.iVolunteer.model.core.tenant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Tenant {

	@Id private String id;
	private String name;
	
	private String homepage;

	private byte[] image;

	private String primaryColor;
	private String secondaryColor;
	
	private String marketplaceId;
	
	private List<SubscriptionRule> subscriptionRules = new ArrayList<>();

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public List<SubscriptionRule> getSubscriptionRules() {
		return subscriptionRules;
	}

	public void setSubscriptionRules(List<SubscriptionRule> subscriptionRules) {
		this.subscriptionRules = subscriptionRules;
	}

}

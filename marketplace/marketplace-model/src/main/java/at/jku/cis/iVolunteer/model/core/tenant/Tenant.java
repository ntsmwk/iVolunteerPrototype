package at.jku.cis.iVolunteer.model.core.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Tenant {

	@Id private String id;
	private String name;
	private String description;

	private String homepage;

	private String imagePath;// TODO xnet rename "logo"? low prio

	private String primaryColor;
	private String secondaryColor;

	private String marketplaceId;

//	TODO xnet adresse hinzufügen
//  TODO xnet mitglieder volunteers: User[] (würden wir im Frontend später erwarten) aktuell kann das einfach leer sein.. nur die wichtigsten infos beim user

	private List<String> tags = new ArrayList<>();

	private String landingpageTitle;
	private String landingpageMessage;
	private String landingpageText;
	private String landingpageImageFileName;

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

	public String getLandingpageImageFileName() {
		return landingpageImageFileName;
	}

	public void setLandingpageImageFileName(String landingpageImageFileName) {
		this.landingpageImageFileName = landingpageImageFileName;
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

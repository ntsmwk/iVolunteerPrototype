package at.jku.cis.iVolunteer.model.marketplace.dto;

public class MarketplaceDTO {

	private String id;
	private String marketplaceId;
	private String url;

	public MarketplaceDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
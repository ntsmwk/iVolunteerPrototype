package at.jku.cis.iVolunteer.marketplace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MarketplaceService {

	@Value("${marketplace.identifier}") private String marketplaceId;
	@Value("${marketplace.uri}") private String marketplaceURL;

	public String getMarketplaceId() {
		return this.marketplaceId;
	}
	
	public String getMarketplaceURI() {
		return marketplaceURL;
	}
}

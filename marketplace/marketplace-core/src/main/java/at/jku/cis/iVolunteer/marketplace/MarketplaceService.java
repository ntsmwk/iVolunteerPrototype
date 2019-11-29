package at.jku.cis.iVolunteer.marketplace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MarketplaceService {

	@Value("${marketplace.identifier}") private String marketplaceId;

	public String getMarketplaceId() {
		return this.marketplaceId;
	}
}

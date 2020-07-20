package at.jku.cis.iVolunteer.core.marketplace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@Service
public class MarketplaceService {
	@Autowired private MarketplaceRepository marketplaceRepository;

	public List<Marketplace> findAll() {
		return marketplaceRepository.findAll();
	}

	public Marketplace findById(@PathVariable("marketplaceId") String marketplaceId) {
		return marketplaceRepository.findOne(marketplaceId);
	}
	
	public List<Marketplace> findAll(List<String> marketplaceIds) {
		List<Marketplace> marketplaces = new ArrayList<Marketplace>();
		marketplaceRepository.findAll(marketplaceIds).forEach(marketplaces::add);
		return marketplaces;
	}

	public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
		return marketplaceRepository.insert(marketplace);
	}

	public Marketplace updateMarketplace(@PathVariable("marketplaceId") String marketplaceId,
			@RequestBody Marketplace marketplace) {
		Marketplace orginalMarketplace = marketplaceRepository.findOne(marketplaceId);
		if (orginalMarketplace == null) {
			throw new NotAcceptableException();
		}
		orginalMarketplace.setName(marketplace.getName());
		orginalMarketplace.setShortName(marketplace.getShortName());
		orginalMarketplace.setUrl(marketplace.getUrl());
		return marketplaceRepository.save(orginalMarketplace);
	}

	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceRepository.delete(marketplaceId);
	}

}

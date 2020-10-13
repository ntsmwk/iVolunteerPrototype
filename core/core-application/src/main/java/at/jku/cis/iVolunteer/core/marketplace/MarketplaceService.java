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
	
	public Marketplace findFirst() {
		List<Marketplace> marketplaces = marketplaceRepository.findAll();
		
		if (marketplaces == null || marketplaces.size() <= 0) {
			return null;
		} 
		
		return marketplaces.get(0);
	}
	
	public Marketplace findByUrl(String url) {
		return marketplaceRepository.findByUrl(url);
	}
	
	

	public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
		return marketplaceRepository.insert(marketplace);
	}

	public Marketplace updateMarketplace(String marketplaceId, Marketplace marketplace) {
		Marketplace originalMarketplace = marketplaceRepository.findOne(marketplaceId);
		if (originalMarketplace == null) {
			throw new NotAcceptableException();
		}
		originalMarketplace = originalMarketplace.updateMarketplace(marketplace);
		return marketplaceRepository.save(originalMarketplace);
	}

	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceRepository.delete(marketplaceId);
	}

}

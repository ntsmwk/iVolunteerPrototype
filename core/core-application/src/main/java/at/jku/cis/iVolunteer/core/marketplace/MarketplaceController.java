package at.jku.cis.iVolunteer.core.marketplace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("marketplace")
public class MarketplaceController {

	@Autowired private MarketplaceRepository marketplaceRepository;

	@GetMapping
	public List<Marketplace> findAll() {
		return marketplaceRepository.findAll();
	}

	@GetMapping("{marketplaceId}")
	public Marketplace findById(@PathVariable("marketplaceId") String marketplaceId) {
		return marketplaceRepository.findOne(marketplaceId);
	}

	@PostMapping
	public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
		return marketplaceRepository.insert(marketplace);
	}

	@PutMapping("{marketplaceId}")
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

	@DeleteMapping("{marketplaceId}")
	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceRepository.delete(marketplaceId);
	}
}

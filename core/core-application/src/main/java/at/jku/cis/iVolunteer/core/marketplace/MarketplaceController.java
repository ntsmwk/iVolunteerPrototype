package at.jku.cis.iVolunteer.core.marketplace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private LoginService loginService;

	@GetMapping
	public List<Marketplace> findAll() {
		return marketplaceService.findAll();
	}
	

	@GetMapping("/subscribed")
	public List<Marketplace> getSubscribedMarketplaces() {
		CoreUser loggedInUser = loginService.getLoggedInUser();
		List<String> marketplaceIds = loggedInUser.getRegisteredMarketplaceIds();
		return marketplaceService.findAll(marketplaceIds);
	}

	@GetMapping("{marketplaceId}")
	public Marketplace findById(@PathVariable("marketplaceId") String marketplaceId) {
		return marketplaceService.findById(marketplaceId);
	}

	
	@PostMapping("{marketplaceId}/update")
	public ResponseEntity<Void> updateMarketplaceById(@PathVariable("marketplaceId") String marketplaceId,
			@RequestBody Marketplace marketplace) {
		try {
			marketplaceService.updateMarketplace(marketplaceId, marketplace);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok().build();
	}

//	END X-Net Endpoints

	@PutMapping("{marketplaceId}")
	public Marketplace updateMarketplace(@PathVariable("marketplaceId") String marketplaceId,
			@RequestBody Marketplace marketplace) {
		return marketplaceService.updateMarketplace(marketplaceId, marketplace);
	}

	@PostMapping
	public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
		return marketplaceService.createMarketplace(marketplace);
	}

	@DeleteMapping("{marketplaceId}")
	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceService.delete(marketplaceId);
	}
}

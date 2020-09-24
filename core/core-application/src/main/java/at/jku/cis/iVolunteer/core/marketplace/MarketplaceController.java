package at.jku.cis.iVolunteer.core.marketplace;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("marketplace")
public class MarketplaceController {
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private LoginService loginService;

	@GetMapping
	public List<String> findAll() {
		return marketplaceService.findAll().stream().map(mp -> mp.getId()).collect(Collectors.toList());
	}

	@GetMapping("/subscribed")
	public List<String> getSubscribedMarketplaces() {
		CoreUser user = loginService.getLoggedInUser();
		return user.getRegisteredMarketplaceIds();
	}
	
//	OTHER ENDPOINTS
	
	@GetMapping("{marketplaceId}")
	public Marketplace findById(@PathVariable("marketplaceId") String marketplaceId) {
		return marketplaceService.findById(marketplaceId);
	}


	@PostMapping
	public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
		return marketplaceService.createMarketplace(marketplace);
	}

	@PutMapping("{marketplaceId}")
	public Marketplace updateMarketplace(@PathVariable("marketplaceId") String marketplaceId,
			@RequestBody Marketplace marketplace) {
		return marketplaceService.updateMarketplace(marketplaceId, marketplace);
	}

	@DeleteMapping("{marketplaceId}")
	public void delete(@PathVariable("marketplaceId") String marketplaceId) {
		marketplaceService.delete(marketplaceId);
	}
}

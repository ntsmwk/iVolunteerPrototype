package at.jku.cis.iVolunteer.core.tenant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired TenantRepository tenantRepository;
	@Autowired TenantService tenantService;

	@GetMapping("/name/{tenantName}")
	public String getTenantByName(@PathVariable String tenantName) {
		return tenantRepository.findByName(tenantName).getId();
	}

	@GetMapping("/{tenantId}")
	public Tenant getTenantById(@PathVariable String tenantId) {
		return tenantRepository.findOne(tenantId);
	}
	
	@GetMapping("/volunteer/{volunteerId}")
	public List<Tenant> getTenantsByVolunteer(@PathVariable String volunteerId){
		return tenantService.getTenantsByVolunteer(volunteerId);
	}

	@GetMapping("/marketplace/{marketplaceId}")
	public List<Tenant> getTenantsByMarketplaceIds(@PathVariable String marketplaceId){
		return tenantRepository.findByMarketplaceId(marketplaceId);
	}
	
	@PostMapping
	public Tenant createTenant(@RequestBody Tenant tenant) {
		return tenantRepository.insert(tenant);
	}

	@PutMapping("{tenantId}")
	public Tenant updateTenant(@PathVariable("tenantId") String tenantId,
			@RequestBody Tenant tenant) {
		Tenant orginalTenant = tenantRepository.findOne(tenantId);
		if (orginalTenant == null) {
			throw new NotAcceptableException();
		}
		orginalTenant.setName(tenant.getName());
		orginalTenant.setPrimaryColor(tenant.getPrimaryColor());
		orginalTenant.setSecondaryColor(tenant.getSecondaryColor());
		orginalTenant.setMarketplaceId(tenant.getMarketplaceId());
		return tenantRepository.save(orginalTenant);
	}
	
}
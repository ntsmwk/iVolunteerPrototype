package at.jku.cis.iVolunteer.core.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tenant")
public class CoreTenantController {
	
	@Autowired CoreTenantRepository coreTenantRepository;
	
	
	@GetMapping("/name/{tenantName}")
	public String getCoreTenantByName(@PathVariable String tenantName) {
		return coreTenantRepository.findByName(tenantName).getId();
	}
	
	@GetMapping("/{tenantId}")
	public String getCoreTenantById(@PathVariable String tenantId) {
		return coreTenantRepository.findOne(tenantId).getId();
	}

}
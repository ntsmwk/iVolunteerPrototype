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
	
	
	@GetMapping("/{coreTenantName}")
	public String getCoreTenantId(@PathVariable String coreTenantName) {
		return coreTenantRepository.findByName(coreTenantName).getId();
	}

}
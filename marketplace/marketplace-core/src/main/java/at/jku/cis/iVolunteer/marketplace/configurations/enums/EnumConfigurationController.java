package at.jku.cis.iVolunteer.marketplace.configurations.enums;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.configurations.enums.EnumConfiguration;

@RestController
public class EnumConfigurationController {
	
	@Autowired EnumConfigurationRepository enumConfigurationRepository;

	@GetMapping("enum-configuration/all")
	private List<EnumConfiguration> getAllEnumConfigurations() {

		return enumConfigurationRepository.findAll();
	}
	
	@GetMapping("enum-configuration/all/{tenantId}")
	private List<EnumConfiguration> getAllEnumConfigurationsForTenant(@PathVariable("tenantId") String tenantId) {
		return enumConfigurationRepository.findByTenantId(tenantId);
	}
	
	@GetMapping("enum-configuration/{id}")
	private EnumConfiguration getEnumConfigurationById(@PathVariable("id") String id) {

		return enumConfigurationRepository.findOne(id);
	}
	
	@GetMapping("enum-configuration/by-name/{name}")
	private EnumConfiguration getEnumConfigurationByName(@PathVariable("name") String name) {
		return enumConfigurationRepository.findByName(name);
	}
	
	@PostMapping("enum-configuration/new")
	private EnumConfiguration newEnumConfiguration(@RequestBody EnumConfiguration enumConfiguration) {
		return enumConfigurationRepository.save(enumConfiguration);
	}
	
	@PostMapping("enum-configuration/new-empty")
	private EnumConfiguration newEmptyEnumConfiguration(@RequestBody String[] params) {
		EnumConfiguration enumConfiguration = new EnumConfiguration(params[0], params[1]);
		return enumConfigurationRepository.save(enumConfiguration);
	}
		
	@PutMapping("enum-configuration/{id}/save") 
	private EnumConfiguration replaceEnumConfiguration(@RequestBody EnumConfiguration enumConfiguration) {
		return enumConfigurationRepository.save(enumConfiguration);
	}
	
	@DeleteMapping("enum-configuration/{id}/delete")
	private void deleteEnumConfiguration(@PathVariable("id") String id) {
		enumConfigurationRepository.delete(id);
	}
	
	
}

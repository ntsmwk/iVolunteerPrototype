package at.jku.cis.iVolunteer.marketplace.configurations.enums;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.configurations.enums.EnumDefinition;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;

@RestController
public class EnumDefinitionController {
	
	@Autowired EnumDefinitionRepository enumDefinitionRepository;

	@GetMapping("enum-definition/all")
	private List<EnumDefinition> getAllEnumConfigurations() {

		return enumDefinitionRepository.findAll();
	}
	
	@GetMapping("enum-definition/all/{tenantId}")
	private List<EnumDefinition> getAllEnumDefinitionssForTenant(@PathVariable("tenantId") String tenantId) {
		return enumDefinitionRepository.findByTenantId(tenantId);
	}
	
	@GetMapping("enum-definition/{id}")
	private EnumDefinition getEnumDefinitionById(@PathVariable("id") String id) {
		return enumDefinitionRepository.findOne(id);
	}
	
	@GetMapping("enum-definition/by-name/{name}")
	private EnumDefinition getEnumDefinitionByName(@PathVariable("name") String name) {
		return enumDefinitionRepository.findByName(name);
	}
	
	@PostMapping("enum-configuration/new")
	private EnumDefinition newEnumDefinition(@RequestBody EnumDefinition enumConfiguration) {
		return enumDefinitionRepository.save(enumConfiguration);
	}
	
	@PostMapping("enum-definition/new-empty")
	private EnumDefinition newEmptyEnumDefinition(@RequestBody String[] params) {
		if (params.length != 3) {
			return null;
		}
		EnumDefinition enumConfiguration = new EnumDefinition(params[0], params[1], params[2]);
		return enumDefinitionRepository.save(enumConfiguration);
	}
		
	@PutMapping("enum-definition/{id}/save") 
	private EnumDefinition replaceEnumDefinition(@RequestBody EnumDefinition enumDefinition) {
		return enumDefinitionRepository.save(enumDefinition);
	}
	
	@DeleteMapping("enum-definition/{id}/delete")
	private void deleteEnumDefinition(@PathVariable("id") String id) {
		enumDefinitionRepository.delete(id);
	}
	
	@PutMapping("enum-definition/delete-multiple")
	private List<EnumDefinition> deleteMultipleMatchingConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this.enumDefinitionRepository::delete);
		return this.enumDefinitionRepository.findAll();
	}
	
	
}

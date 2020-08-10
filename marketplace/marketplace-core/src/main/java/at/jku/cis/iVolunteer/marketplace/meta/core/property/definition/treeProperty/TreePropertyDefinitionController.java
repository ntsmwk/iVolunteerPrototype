package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

@RestController
public class TreePropertyDefinitionController {

	@Autowired TreePropertyDefinitionRepository enumDefinitionRepository;

	@GetMapping("/meta/core/property-definition/tree/all")
	private List<TreePropertyDefinition> getAllEnumConfigurations() {

		return enumDefinitionRepository.findAll();
	}

	@GetMapping("/meta/core/property-definition/tree/all/{tenantId}")
	private List<TreePropertyDefinition> getAllEnumDefinitionssForTenant(@PathVariable("tenantId") String tenantId) {
		return enumDefinitionRepository.findByTenantId(tenantId);
	}

	@GetMapping("/meta/core/property-definition/tree/{id}")
	private TreePropertyDefinition getEnumDefinitionById(@PathVariable("id") String id) {
		return enumDefinitionRepository.findOne(id);
	}

	@GetMapping("/meta/core/property-definition/tree/by-name/{name}")
	private TreePropertyDefinition getEnumDefinitionByName(@PathVariable("name") String name) {
		return enumDefinitionRepository.findByName(name);
	}

	@PostMapping("/meta/core/property-configuration/tree/new")
	private TreePropertyDefinition newEnumDefinition(@RequestBody TreePropertyDefinition enumConfiguration) {
		return enumDefinitionRepository.save(enumConfiguration);
	}

	@PostMapping("/meta/core/property-definition/tree/new-empty")
	private TreePropertyDefinition newEmptyEnumDefinition(@RequestBody String[] params) {
		if (params.length != 4) {
			return null;
		}
		TreePropertyDefinition enumDefinition = new TreePropertyDefinition(params[0], params[1], Boolean.parseBoolean(params[2]), params[3]);
		return enumDefinitionRepository.save(enumDefinition);
	}

	@PutMapping("/meta/core/property-definition/tree/save")
	private TreePropertyDefinition replaceEnumDefinition(@RequestBody TreePropertyDefinition enumDefinition) {
		
		return enumDefinitionRepository.save(enumDefinition);
	}

	@DeleteMapping("/meta/core/property-definition/tree/{id}/delete")
	private void deleteEnumDefinition(@PathVariable("id") String id) {
		enumDefinitionRepository.delete(id);
	}

	@PutMapping("/meta/core/enum-definition/tree/delete-multiple")
	private List<TreePropertyDefinition> deleteMultipleMatchingConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this.enumDefinitionRepository::delete);
		return this.enumDefinitionRepository.findAll();
	}

}

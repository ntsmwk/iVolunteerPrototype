package at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@RestController
public class FlatPropertyDefinitionController {

	@Autowired FlatPropertyDefinitionRepository propertyDefinitionRepository;

	@GetMapping("/property-definition/flat/all")
	private List<FlatPropertyDefinition<Object>> getAllPropertyDefinitions() {
		return propertyDefinitionRepository.findAll();
	}
	
	@GetMapping("/property-definition/flat/all/tenant/{tenantId}")
	private List<FlatPropertyDefinition<Object>> getAllPropertyDefinitions(@PathVariable("tenantId") String tenantId) {
		return propertyDefinitionRepository.getByTenantId(tenantId); 
	}

	@GetMapping("/property-definition/flat/{id}")
	private FlatPropertyDefinition<Object> getPropertyDefinitionById(@PathVariable("id") String id) {
	
		FlatPropertyDefinition<Object> findOne = propertyDefinitionRepository.findOne(id);
		return findOne;
		
	}

	@PostMapping("/property-definition/flat/new/multiple")
	private List<FlatPropertyDefinition<Object>> createNewPropertyDefintions(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {

		return propertyDefinitionRepository.save(propertyDefinitions);
	}

	@PutMapping("/property-definition/flat/{id}/update/multiple")
	private List<FlatPropertyDefinition<Object>> updatePropertyDefinitions(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		return this.createNewPropertyDefintions(propertyDefinitions);
	}
	
	@PostMapping("/property-definition/flat/new")
	private ResponseEntity<Object> createNewPropertyDefintion(@RequestBody FlatPropertyDefinition<Object> propertyDefinition) {
		
		 if (propertyDefinitionRepository.getByNameAndTenantId(propertyDefinition.getName(), propertyDefinition.getTenantId()).size() > 0) {
			 return ResponseEntity.badRequest().build();
		 };
		
		propertyDefinition = propertyDefinitionRepository.save(propertyDefinition);
		return ResponseEntity.ok(propertyDefinition);
	}

	@PutMapping("/property-definition/flat/{id}/update")
	private ResponseEntity<Object>  updatePropertyDefinition(
			@RequestBody FlatPropertyDefinition<Object> propertyDefinition) {
		 return this.createNewPropertyDefintion(propertyDefinition);
	}

	@DeleteMapping("/property-definition/flat/{id}/delete")
	private void deletePropertyDefinition(@PathVariable("id") String id) {
		propertyDefinitionRepository.delete(id);
	}

}

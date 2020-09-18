package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@RestController
public class FlatPropertyDefinitionController {

	@Autowired FlatPropertyDefinitionRepository propertyDefinitionRepository;

	@GetMapping("/meta/core/property-definition/flat/all/tenant/{tenantId}")
	private List<FlatPropertyDefinition<Object>> getAllPropertyDefinitions(@PathVariable("tenantId") String tenantId) {
		return propertyDefinitionRepository.findByTenantId(tenantId);
	}

	@GetMapping("/meta/core/property-definition/flat/{id}/tenant/{tenantId}")
	private FlatPropertyDefinition<Object> getPropertyDefinitionById(@PathVariable("id") String id, 
			@PathVariable("tenantId") String tenantId) {
	
		FlatPropertyDefinition<Object> findOne = propertyDefinitionRepository.getByIdAndTenantId(id, tenantId);
		return findOne;
		
	}

	@PostMapping("/meta/core/property-definition/flat/new")
	private List<FlatPropertyDefinition<Object>> createNewPropertyDefintion(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		
//		for (PropertyDefinition<Object> pd : propertyDefinitions) {
//			pd.setCustom(true);
//		}

		return propertyDefinitionRepository.save(propertyDefinitions);
	}

	@PutMapping("/meta/core/property-definition/flat/{id}/update")
	private List<FlatPropertyDefinition<Object>> updatePropertyDefinition(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		return this.createNewPropertyDefintion(propertyDefinitions);
	}

	@DeleteMapping("/meta/core/property-definition/flat/{id}/delete")
	private void deletePropertyDefinition(@PathVariable("id") String id) {
		propertyDefinitionRepository.delete(id);
	}

}

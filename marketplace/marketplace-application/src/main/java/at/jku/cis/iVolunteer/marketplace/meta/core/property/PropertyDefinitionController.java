package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyDefinitionDTO;

@RestController
public class PropertyDefinitionController {
	
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired PropertyDefinitionMapper propertyDefinitionMapper;

	@GetMapping("/meta/core/property/definition/all")
	private List<PropertyDefinitionDTO<Object>> getAllPropertyDefinitions() {
		return propertyDefinitionMapper.toDTOs(propertyDefinitionRepository.findAll());
	}
	
	@GetMapping("/meta/core/property/definition/{id}") 
	private PropertyDefinitionDTO<Object> getPropertyDefinitionById(@PathVariable("id") String id) {
		return propertyDefinitionMapper.toDTO(propertyDefinitionRepository.findOne(id));
	}
	
	@PostMapping("/meta/core/property/definition/new")
	private List<PropertyDefinitionDTO<Object>> createNewPropertyDefintion(@RequestBody List<PropertyDefinitionDTO<Object>> propertyDefinitionDTOs) {
		List<PropertyDefinition<Object>> propertyDefinitions = propertyDefinitionMapper.toEntities(propertyDefinitionDTOs);
		
		for (PropertyDefinition<Object> pd : propertyDefinitions) {
			pd.setCustom(true);
		}
		
		return propertyDefinitionMapper.toDTOs(propertyDefinitionRepository.save(propertyDefinitions));
	}
	
	@PutMapping("/meta/core/property/definition/{id}/update") 
	private List<PropertyDefinitionDTO<Object>> updatePropertyDefinition(@RequestBody List<PropertyDefinitionDTO<Object>> propertyDefinitionDTOs) {
		
		
		return this.createNewPropertyDefintion(propertyDefinitionDTOs);
	}
	
	@DeleteMapping("/meta/core/property/definition/{id}/delete")
	private void deletePropertyDefinition(@PathVariable("id") String id) {
		propertyDefinitionRepository.delete(id);
	}

	
	

	
	
	
	
	
	
	
	
	
	
}

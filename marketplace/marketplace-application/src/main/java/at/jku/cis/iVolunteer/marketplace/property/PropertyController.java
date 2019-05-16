package at.jku.cis.iVolunteer.marketplace.property;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.property.MultiplePropertyRetMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyListItemMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.MultiplePropertyRetDTO;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;

@RestController
public class PropertyController {
	
	@Autowired private PropertyListItemMapper propertyListItemMapper;
	@Autowired private PropertyMapper propertyMapper;
	
	@Autowired private PropertyRepository propertyRepository;
	@Autowired private MultiplePropertyRetMapper multiplePropertyRetMapper;
			
	@GetMapping("/properties/list") 
	public List<PropertyListItemDTO<Object>> getPropertiesList() {
		List<PropertyListItemDTO<Object>> retVal = propertyListItemMapper.toDTOs(propertyRepository.findAll());		
		return retVal;	
	}
	
	@GetMapping("/properties/full")
	public List<PropertyDTO<Object>> getPropertiesFull() {
		List<PropertyDTO<Object>> retVal = propertyMapper.toDTOs(propertyRepository.findAll());
		return retVal;
	}
	
	@GetMapping("/properties/{id}") 
	public PropertyDTO<Object> getPropertyByID(@PathVariable("id") String id) {
		return propertyMapper.toDTO(propertyRepository.findOne(id));
	}
	
	@PostMapping("/properties/new/single")
	public void addSingleProperty(@RequestBody PropertyDTO<Object> dto) {
		SingleProperty<Object> p = (SingleProperty<Object>) propertyMapper.toEntity(dto);
		
		//fix the ids for Default Values
		if (p.getLegalValues() != null && p.getDefaultValues() != null) {
			for (ListEntry<Object> val : p.getLegalValues()) {
				
				try {
					ListEntry<Object> defaultValue = p.getDefaultValues().stream().filter(entry -> entry.value.equals(val.value)).findFirst().get();
					defaultValue.id = val.id;
				} catch (NoSuchElementException e) {
					continue;
				}
			}	
		}
		p.setCustom(true);
		this.propertyRepository.save(p);
					
	}
	
	@PostMapping("/properties/new/multiple")
	public void addMultipleProperty(@RequestBody MultiplePropertyRetDTO dto) {		
		MultipleProperty mp = new MultipleProperty(multiplePropertyRetMapper.toEntity(dto));
				
		for (String id : dto.getPropertyIDs()) {		
			Property p = propertyRepository.findOne(id);			
			mp.getProperties().add(p);
		}
		this.propertyRepository.save(mp);
	}
	
	@DeleteMapping("/properties/{id}")
	public void deleteProperty(@PathVariable("id") String id) {
		this.propertyRepository.delete(id);
	}
}

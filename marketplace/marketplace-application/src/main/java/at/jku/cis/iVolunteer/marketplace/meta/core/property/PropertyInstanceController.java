package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassInstanceMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.dto.PropertyInstanceDTO;

@RestController
public class PropertyInstanceController {
	
	@Autowired ClassInstanceRepository classInstanceRepository;
	@Autowired PropertyInstanceMapper propertyInstanceMapper;
	@Autowired ClassInstanceMapper classInstanceMapper;

	@GetMapping("/meta/core/property/instance/{classInstanceId}/all")
	List<PropertyInstanceDTO<Object>> getAllPropertyInstances(
			@PathVariable("classDefintionId") String classDefinitionId) {

		ClassInstance classInstance = classInstanceRepository.findOne(classDefinitionId);
		if (classInstance == null) {
			return null;
		}
		
		return propertyInstanceMapper.toDTOs(classInstance.getProperties());		
	}

	@GetMapping("/meta/core/property/instance/{classInstanceId}/{propertyInstanceId}")
	PropertyInstanceDTO<Object> getPropertyInstanceById(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("propertyInstanceId") String propertyInstanceId) {
		
		ClassInstance classInstance = classInstanceRepository.findOne(classDefinitionId);
		if (classInstance == null) {
			return null;
		}
		
		PropertyInstance<Object> propertyInstance = classInstance.getProperties().stream().filter(p -> p.getId().equals(propertyInstanceId)).findFirst().get();
		
		return propertyInstanceMapper.toDTO(propertyInstance);
	}

	@PutMapping("/meta/core/property/instance/{classInstanceId}/update")
	List<PropertyInstanceDTO<Object>> updatePropertyInstances(
			@PathVariable("classInstanceId") String classInstanceId,
			@RequestBody List<PropertyInstanceDTO<Object>> propertyInstanceDTOs) {
		
		ClassInstance classInstance = classInstanceRepository.findOne(classInstanceId);
		if (classInstance == null) {
			return null;
		}

		List<PropertyInstance<Object>> propertyInstances = propertyInstanceMapper.toEntities(propertyInstanceDTOs);
		
		for (PropertyInstance<Object> propertyInstance : propertyInstances) {
			int index = findIndexOfPropertyInstance(classInstance, propertyInstance.getId());
			
			if (index != -1) {
				propertyInstances.set(index, propertyInstance);
			}
		}
		
		classInstance.setProperties(propertyInstances);
		classInstanceRepository.insert(classInstance);
		
		return propertyInstanceMapper.toDTOs(propertyInstances);
	}

	@PatchMapping("/meta/core/property/instance/{classInstanceId}/clear/all")
	List<PropertyInstanceDTO<Object>> clearPropertyInstanceValues(
			@PathVariable("classInstanceId") String classInstanceId) {
		
		ClassInstance classInstance = classInstanceRepository.findOne(classInstanceId);
		
		for (PropertyInstance<?> propertyInstance : classInstance.getProperties()) {
			propertyInstance.resetValues();
		}
		
		ClassInstance ret = classInstanceRepository.save(classInstance);
		
		return propertyInstanceMapper.toDTOs(ret.getProperties());
	}

	@PatchMapping("/meta/core/property/instance/{classInstanceId}/clear/{propertyInstanceId}")
	PropertyInstanceDTO<Object> clearPropertyInstanceValue(
			@PathVariable("classInstanceId") String classInstanceId,
			@PathVariable("propertyInstanceId") String propertyInstanceId) {

		ClassInstance classInstance = classInstanceRepository.findOne(classInstanceId);
		
		if (classInstance == null) {
			return null;
		}
		
		int index = findIndexOfPropertyInstance(classInstance, propertyInstanceId);
		if (index == -1) {
			return null;
		}
		
		classInstance.getProperties().get(index).resetValues();
		
		ClassInstance ret = classInstanceRepository.save(classInstance);
		
		return propertyInstanceMapper.toDTO(ret.getProperties().get(index));	
	}

	private int findIndexOfPropertyInstance(ClassInstance classInstance, String propertyInstanceId) {
		int i = 0;
		
		for (PropertyInstance<Object> p : classInstance.getProperties()) {
			if (p.getId().equals(propertyInstanceId)) {
				return i;
			} else {
				i++;
			}
		}
		
		return -1;
	}
	
}

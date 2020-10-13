package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@RestController
public class PropertyInstanceController {

	@Autowired ClassInstanceRepository classInstanceRepository;

	@GetMapping("/meta/core/property/instance/{classInstanceId}/all")
	List<PropertyInstance<Object>> getAllPropertyInstances(@PathVariable("classDefintionId") String classDefinitionId) {

		ClassInstance classInstance = classInstanceRepository.findOne(classDefinitionId);
		if (classInstance == null) {
			return null;
		}

		return classInstance.getProperties();
	}

	@GetMapping("/meta/core/property/instance/{classInstanceId}/{propertyInstanceId}")
	PropertyInstance<Object> getPropertyInstanceById(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("propertyInstanceId") String propertyInstanceId) {

		ClassInstance classInstance = classInstanceRepository.findOne(classDefinitionId);
		if (classInstance == null) {
			return null;
		}

		PropertyInstance<Object> propertyInstance = classInstance.getProperties().stream()
				.filter(p -> p.getId().equals(propertyInstanceId)).findFirst().get();

		return propertyInstance;
	}

	@PutMapping("/meta/core/property/instance/{classInstanceId}/update")
	List<PropertyInstance<Object>> updatePropertyInstances(@PathVariable("classInstanceId") String classInstanceId,
			@RequestBody List<PropertyInstance<Object>> propertyInstanceDTOs) {

		ClassInstance classInstance = classInstanceRepository.findOne(classInstanceId);
		if (classInstance == null) {
			return null;
		}

		List<PropertyInstance<Object>> propertyInstances = propertyInstanceDTOs;

		for (PropertyInstance<Object> propertyInstance : propertyInstances) {
			int index = findIndexOfPropertyInstance(classInstance, propertyInstance.getId());

			if (index != -1) {
				propertyInstances.set(index, propertyInstance);
			}
		}

		classInstance.setProperties(propertyInstances);
		classInstanceRepository.insert(classInstance);

		return propertyInstances;
	}

	@PatchMapping("/meta/core/property/instance/{classInstanceId}/clear/all")
	List<PropertyInstance<Object>> clearPropertyInstanceValues(
			@PathVariable("classInstanceId") String classInstanceId) {

		ClassInstance classInstance = classInstanceRepository.findOne(classInstanceId);

		for (PropertyInstance<?> propertyInstance : classInstance.getProperties()) {
			propertyInstance.resetValues();
		}

		ClassInstance ret = classInstanceRepository.save(classInstance);

		return ret.getProperties();
	}

	@PatchMapping("/meta/core/property/instance/{classInstanceId}/clear/{propertyInstanceId}")
	PropertyInstance<Object> clearPropertyInstanceValue(@PathVariable("classInstanceId") String classInstanceId,
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

		return ret.getProperties().get(index);
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

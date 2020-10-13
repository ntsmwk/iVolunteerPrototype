package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.ClassPropertyRequestObject;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@RestController
public class ClassPropertyController {

	@Autowired private ClassPropertyService classPropertyService;


	@GetMapping("/meta/core/property/class/{classDefinitionId}/all")
	List<ClassProperty<Object>> getAllClassPropertiesFromClass(@PathVariable("classDefinitionId") String classDefinitionId) {
		return classPropertyService.getAllClassPropertiesFromClass(classDefinitionId);
	}

	@GetMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}")
	ClassProperty<Object> getClassPropertyById(@PathVariable("classDefinitionId") String classDefinitionId, @PathVariable("classPropertyId") String classPropertyId) {
		return classPropertyService.getClassPropertyById(classDefinitionId, classPropertyId);
	}

	@PutMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}/update")
	ClassProperty<Object> updateClassProperty(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("classPropertyId") String classPropertyId,
			@RequestBody ClassProperty<Object> updatedClassProperty) {

		return classPropertyService.updateClassProperty(classDefinitionId, classPropertyId, updatedClassProperty);
	}
	

	@PutMapping("meta/core/property/class/get-classproperty-from-definition-by-id")
	private List<ClassProperty<Object>> getClassPropertyFromPropertyDefinitionById(
			@RequestBody ClassPropertyRequestObject requestObject) {
		return classPropertyService.getClassPropertyFromDefinitionById(requestObject.getFlatPropertyDefinitionIds(), requestObject.getTreePropertyDefinitionIds());
	}

	@PutMapping("meta/core/property/class/{id}/add-properties-by-id")
	private List<ClassProperty<Object>> addPropertiesToClassDefinitionById(@PathVariable("id") String id, @RequestBody List<String> propertyIds) {
		return classPropertyService.addPropertiesToClassDefinitionById(id, propertyIds);
	}

	@PutMapping("meta/core/property/class/{id}/add-properties")
	private List<ClassProperty<Object>> addPropertiesToClassDefinition(@PathVariable("id") String id,
			@RequestBody List<ClassProperty<Object>> properties) {
		return classPropertyService.addPropertiesToClassDefinition(id, properties);
	}

	@PutMapping("/meta/core/property/class/{id}/remove-properties")
	private ClassDefinition removePropertiesFromClassDefinition(@PathVariable("id") String id,
			@RequestBody List<String> idsToRemove) {
		return classPropertyService.removePropertiesFromClassDefinition(id, idsToRemove);
	}
}
package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@RestController
public class ClassPropertyController {

	@Autowired private ClassPropertyService classPropertyService;

	@GetMapping("/meta/core/property/class/{classDefinitionId}/all/tenant/{tenantId}")
	List<ClassProperty<Object>> getAllClassPropertiesFromClass(
			@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("tenantId") String tenantId) {
		return classPropertyService.getAllClassPropertiesFromClass(classDefinitionId, tenantId);
	}

	@GetMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}/tenant/{tenantId}")
	ClassProperty<Object> getClassPropertyById(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("classPropertyId") String classPropertyId,
			@PathVariable("tenantId") String tenantId) {
		return classPropertyService.getClassPropertyById(classDefinitionId, classPropertyId, tenantId);
	}

	@PutMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}/update")
	ClassProperty<Object> updateClassProperty(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("classPropertyId") String classPropertyId,
			@RequestBody ClassProperty<Object> updatedClassProperty) {

		return classPropertyService.updateClassProperty(classDefinitionId, classPropertyId, updatedClassProperty);
	}
}
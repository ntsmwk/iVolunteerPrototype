package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@RestController
public class ClassDefinitionPropertyController {

	@Autowired private ClassDefinitionPropertyService classDefinitionPropertyService;

	@PutMapping("meta/core/class/definition/get-classproperty-from-propertydefinition-by-id")
	private List<ClassProperty<Object>> getClassPropertyFromPropertyDefinitionById(
			@RequestBody List<String> propertyIds) {
		return classDefinitionPropertyService.getClassPropertyFromPropertyDefinitionById(propertyIds);
	}

	@PutMapping("meta/core/class/definition/{id}/add-properties-by-id")
	private List<ClassProperty<Object>> addPropertiesToClassDefinitionById(@PathVariable("id") String id,
			@RequestBody List<String> propertyIds) {
		return classDefinitionPropertyService.addPropertiesToClassDefinitionById(id, propertyIds);
	}

	@PutMapping("meta/core/class/definition/{id}/add-properties")
	private List<ClassProperty<Object>> addPropertiesToClassDefinition(@PathVariable("id") String id,
			@RequestBody List<ClassProperty<Object>> properties) {
		return classDefinitionPropertyService.addPropertiesToClassDefinition(id, properties);
	}

	@PutMapping("/meta/core/class/definition/{id}/remove-properties")
	private ClassDefinition removePropertiesFromClassDefinition(@PathVariable("id") String id,
			@RequestBody List<String> idsToRemove) {
		return classDefinitionPropertyService.removePropertiesFromClassDefinition(id, idsToRemove);
	}

}

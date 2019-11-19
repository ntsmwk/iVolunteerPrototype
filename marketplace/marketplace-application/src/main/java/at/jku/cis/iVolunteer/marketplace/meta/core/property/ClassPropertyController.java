package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@RestController
public class ClassPropertyController {

	@Autowired ClassDefinitionRepository classDefinitionRepository;

	@GetMapping("/meta/core/property/class/{classDefinitionId}/all")
	List<ClassProperty<Object>> getAllClassPropertiesFromClass(
			@PathVariable("classDefinitionId") String classDefinitionId) {

		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);
		if (classDefinition != null) {
			return classDefinition.getProperties();
		}
		return null;
	}

	@GetMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}")
	ClassProperty<Object> getClassPropertyById(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("classPropertyId") String classPropertyId) {

		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);
		if (classDefinition != null) {
			return findClassProperty(classDefinition, classPropertyId);
		}
		return null;
	}

	@PutMapping("/meta/core/property/class/{classDefinitionId}/{classPropertyId}/update")
	ClassProperty<Object> updateClassProperty(@PathVariable("classDefinitionId") String classDefinitionId,
			@PathVariable("classPropertyId") String classPropertyId,
			@RequestBody ClassProperty<Object> updatedClassProperty) {

		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);

		if (classDefinition != null) {
			int index = findIndexOfClassProperty(classDefinition, classPropertyId);
			classDefinition.getProperties().set(index, updatedClassProperty);
			classDefinitionRepository.save(classDefinition);
			return classDefinition.getProperties().get(index);

		}
		return null;
	}

	private ClassProperty<Object> findClassProperty(ClassDefinition classDefinition, String classPropertyId) {
		// @formatter:off
		return classDefinition
				.getProperties()
				.stream()
				.filter(p -> p.getId().equals(classPropertyId))
				.findFirst().orElse(null);
		// @formatter:on
	}

	private int findIndexOfClassProperty(ClassDefinition classDefinition, String classPropertyId) {
		// @formatter:off
		return IntStream.range(0, classDefinition.getProperties().size())
			     .filter(i -> classPropertyId.equals(classDefinition.getProperties().get(i).getId()))
			     .findFirst()
			     .orElse(-1);
		// @formatter:on		
	}

}

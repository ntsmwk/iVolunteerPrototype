package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Service
public class ClassPropertyService {

	@Autowired ClassDefinitionRepository classDefinitionRepository;

	public List<ClassProperty<Object>> getAllClassPropertiesFromClass(String classDefinitionId, String tenantId) {
		ClassDefinition classDefinition = classDefinitionRepository.findById(classDefinitionId, tenantId);
		if (classDefinition != null) {
			return classDefinition.getProperties();
		}
		return null;
	}

	public ClassProperty<Object> getClassPropertyById(String classDefinitionId, String classPropertyId, String tenantId) {
		ClassDefinition classDefinition = classDefinitionRepository.findById(classDefinitionId, tenantId);
		if (classDefinition != null) {
			return findClassProperty(classDefinition, classPropertyId);
		}
		return null;
	}

	public ClassProperty<Object> updateClassProperty(String classDefinitionId, String classPropertyId,
			ClassProperty<Object> updatedClassProperty) {
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

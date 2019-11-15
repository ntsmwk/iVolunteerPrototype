package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class ClassDefinitionPropertyService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private ClassPropertyMapper classPropertyMapper;
	@Autowired private ClassDefinitionMapper classDefinitionMapper;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	List<ClassPropertyDTO<Object>> getClassPropertyFromPropertyDefinitionById(List<String> propertyIds) {
		List<PropertyDefinition<Object>> definitionProperties = Lists
				.newLinkedList(propertyDefinitionRepository.findAll(propertyIds));
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(definitionProperties);
		return classPropertyMapper.toDTOs(classProperties);
	}

	List<ClassPropertyDTO<Object>> addPropertiesToClassDefinitionById(String id,
			@RequestBody List<String> propertyIds) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		List<PropertyDefinition<Object>> definitionProperties = Lists
				.newLinkedList(propertyDefinitionRepository.findAll(propertyIds));
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(definitionProperties);

		if (clazz.getProperties() == null) {
			clazz.setProperties(new ArrayList<>());
		}

		clazz.getProperties().addAll(classProperties);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	List<ClassPropertyDTO<Object>> addPropertiesToClassDefinition(String id,
			List<ClassPropertyDTO<Object>> properties) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);

		List<ClassProperty<Object>> props = classPropertyMapper.toEntities(properties);
		clazz.getProperties().addAll(props);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(
			List<PropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();

		for (PropertyDefinition<Object> d : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(d));
		}

		return cProps;
	}

	ClassDefinitionDTO removePropertiesFromClassDefinition(String id, List<String> idsToRemove) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		ArrayList<ClassProperty<Object>> remainingObjects = clazz.getProperties().stream()
				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId)))
				.collect(Collectors.toCollection(ArrayList::new));

		clazz.setProperties(remainingObjects);
		clazz = classDefinitionRepository.save(clazz);
		return classDefinitionMapper.toDTO(clazz);
	}

}

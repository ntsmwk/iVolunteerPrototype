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

@Service
public class ClassDefinitionPropertyService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private ClassPropertyMapper classPropertyMapper;
	@Autowired private ClassDefinitionMapper classDefinitionMapper;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	List<ClassPropertyDTO<Object>> getClassPropertyFromPropertyDefinitionById(List<String> propertyIds) {
		// @formatter:off
		return classPropertyMapper.toDTOs(
				createClassPropertiesFromDefinitions(propertyDefinitionRepository.findAll(propertyIds)));
		// @formatter:on
	}

	List<ClassPropertyDTO<Object>> addPropertiesToClassDefinitionById(String id,
			@RequestBody List<String> propertyIds) {
		// @formatter:off
 		List<ClassProperty<Object>> classProperties = 
				createClassPropertiesFromDefinitions(propertyDefinitionRepository.findAll(propertyIds));
		// @formatter:on

		ClassDefinition clazz = storeClassProperties(id, classProperties);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	List<ClassPropertyDTO<Object>> addPropertiesToClassDefinition(String id,
			List<ClassPropertyDTO<Object>> properties) {
		ClassDefinition clazz = storeClassProperties(id, classPropertyMapper.toEntities(properties));
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	ClassDefinitionDTO removePropertiesFromClassDefinition(String id, List<String> idsToRemove) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);

		// @formatter:off
		ArrayList<ClassProperty<Object>> remainingObjects = 
				clazz
					.getProperties()
					.stream()
					.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId)))
					.collect(Collectors.toCollection(ArrayList::new));
		// @formatter:on

		clazz.setProperties(remainingObjects);
		clazz = classDefinitionRepository.save(clazz);
		return classDefinitionMapper.toDTO(clazz);
	}

	private ClassDefinition storeClassProperties(String id, List<ClassProperty<Object>> classProperties) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		if (clazz.getProperties() == null) {
			clazz.setProperties(new ArrayList<>());
		}
		clazz.getProperties().addAll(classProperties);
		clazz = classDefinitionRepository.save(clazz);
		return clazz;
	}

	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(
			Iterable<PropertyDefinition<Object>> propertyDefinitions) {

		List<ClassProperty<Object>> cProps = new ArrayList<>();
		for (PropertyDefinition<Object> d : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(d));
		}
		return cProps;
	}

}

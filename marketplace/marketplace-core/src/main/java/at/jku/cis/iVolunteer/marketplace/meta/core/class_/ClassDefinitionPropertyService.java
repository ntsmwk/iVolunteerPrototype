package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Service
public class ClassDefinitionPropertyService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	List<ClassProperty<Object>> getClassPropertyFromPropertyDefinitionById(List<String> propertyIds, String tenantId) {
		return createClassPropertiesFromDefinitions(propertyDefinitionRepository.getByIdAndTenantId(propertyIds, tenantId));
	}

	// TODO: Philipp: tenantId check required?
	List<ClassProperty<Object>> addPropertiesToClassDefinitionById(String id, @RequestBody List<String> propertyIds) {
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(
				propertyDefinitionRepository.findAll(propertyIds));

		ClassDefinition clazz = storeClassProperties(id, classProperties);
		return clazz.getProperties();
	}

	List<ClassProperty<Object>> addPropertiesToClassDefinition(String id, List<ClassProperty<Object>> properties) {
		ClassDefinition clazz = storeClassProperties(id, properties);
		return clazz.getProperties();
	}

	ClassDefinition removePropertiesFromClassDefinition(String id, List<String> idsToRemove) {
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
		return classDefinitionRepository.save(clazz);
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

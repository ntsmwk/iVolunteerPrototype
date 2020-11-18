package at.jku.cis.iVolunteer.configurator.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator._mapper.property.TreePropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.configurator._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

@Service
public class ClassPropertyService {

	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired private TreePropertyDefinitionToClassPropertyMapper enumDefinitionToClassPropertyMapper;

	List<ClassProperty<Object>> getClassPropertyFromDefinitionById(List<String> flatPropertyIds, List<String> treePropertyIds) {
		List<FlatPropertyDefinition<Object>> flatProperties = new ArrayList<>();
		List<TreePropertyDefinition> treeProperties = new ArrayList<>();
		
		if (flatPropertyIds != null) {
			propertyDefinitionRepository.findAll(flatPropertyIds).forEach(flatProperties::add);
		}
		if (treePropertyIds != null) {
			treePropertyDefinitionRepository.findAll(treePropertyIds).forEach(treeProperties::add);
		}
		
		List<ClassProperty<Object>> flatClassProperties = createClassPropertiesFromDefinitions(flatProperties);
		List<ClassProperty<Object>> treeClassProperties = createClassPropertiesFromEnumDefinitions(treeProperties);
		
		flatClassProperties.addAll(treeClassProperties);
		return flatClassProperties;
	}
	
	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		for (FlatPropertyDefinition<Object> pd : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		}
		return cProps;
	}
	
	private List<ClassProperty<Object>> createClassPropertiesFromEnumDefinitions(List<TreePropertyDefinition> enums) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		for (TreePropertyDefinition ed : enums) {
			cProps.add(enumDefinitionToClassPropertyMapper.toTarget(ed));
		}
		return cProps;
	}

}

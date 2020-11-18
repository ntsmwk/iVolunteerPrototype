package at.jku.cis.iVolunteer.configurator._mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.configurator.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.configurator.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyEntry;

@Component
public class TreePropertyDefinitionToClassPropertyMapper
		implements OneWayMapper<TreePropertyDefinition, ClassProperty<Object>> {

	@Autowired
	CollectionService collectionService;

	@SuppressWarnings("unchecked")
	@Override
	public ClassProperty<Object> toTarget(TreePropertyDefinition source) {

		if (source == null) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		ClassProperty classProperty = new ClassProperty<TreePropertyEntry>();

		classProperty.setId(source.getId());
		classProperty.setName(source.getName());

		classProperty.setAllowedValues(collectionService.collectTreePropertyDefinitions(source));

		classProperty.setType(PropertyType.TREE);

		// if (source.getPropertyConstraints() != null) {
		// classProperty
		// .setPropertyConstraints(new
		// ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
		// }

		classProperty.setMultiple(source.isMultiple());
		classProperty.setRequired(source.isRequired());

		return classProperty;
	}

	@Override
	public List<ClassProperty<Object>> toTargets(List<TreePropertyDefinition> sources) {

		if (sources == null) {
			return null;
		}

		List<ClassProperty<Object>> classProperties = new ArrayList<>();
		for (TreePropertyDefinition enumDefinition : sources) {
			toTarget(enumDefinition);
		}

		return classProperties;
	}

}

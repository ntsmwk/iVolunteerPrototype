package at.jku.cis.iVolunteer.configurator._mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.configurator.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Component
public class PropertyDefinitionToClassPropertyMapper
		implements OneWayMapper<FlatPropertyDefinition<Object>, ClassProperty<Object>> {

	@Override
	public ClassProperty<Object> toTarget(FlatPropertyDefinition<Object> source) {
		if (source == null) {
			return null;
		}

		ClassProperty<Object> classProperty = new ClassProperty<Object>();

		classProperty.setId(source.getId());
		classProperty.setName(source.getName());

		if (source.getAllowedValues() != null) {
			classProperty.setAllowedValues(new ArrayList<Object>(source.getAllowedValues()));
		}

		classProperty.setType(source.getType());

		if (source.getPropertyConstraints() != null) {
			classProperty
					.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
		}

		classProperty.setMultiple(source.isMultiple());
		classProperty.setRequired(source.isRequired());

		return classProperty;
	}

	@Override
	public List<ClassProperty<Object>> toTargets(List<FlatPropertyDefinition<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<ClassProperty<Object>> targets = new ArrayList<ClassProperty<Object>>();
		for (FlatPropertyDefinition<Object> definition : sources) {
			targets.add(this.toTarget(definition));
		}

		return targets;
	}

}

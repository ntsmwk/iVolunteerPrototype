package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicField;;

@Component
public class XClassPropertyDynamicFieldMapper implements AbstractMapper<ClassProperty<Object>, XDynamicField> {

	@Override
	public XDynamicField toTarget(ClassProperty<Object> source) {
		if (source == null) {
			return null;
		}

		XDynamicField field = new XDynamicField();

		field.setId(source.getId());
		field.setName(source.getName());
//		field.setDescription(source.getDescription());
		field.setCustom(false); // TODO
		field.setMultiple(source.isMultiple());
		field.setType(source.getType());
		field.setAllowedValues(source.getAllowedValues());
		field.setUnit(source.getUnit());
		field.setRequired(source.isRequired());
		field.setRequiredMessage(null);
		field.setFieldConstraints(source.getPropertyConstraints());
//		field.setTimestamp(source.getTimestamp());
		field.setVisible(source.isVisible());
		field.setTabId(source.getTabId());

		return field;
	}

	@Override
	public List<XDynamicField> toTargets(List<ClassProperty<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<XDynamicField> targets = new ArrayList<>();

		for (ClassProperty<Object> source : sources) {
			targets.add(toTarget(source));
		}

		return targets;
	}

	@Override
	public ClassProperty<Object> toSource(XDynamicField target) {
		if (target == null) {return null; }
		ClassProperty<Object> property = new ClassProperty<>();

		property.setId(target.getId());
		property.setName(target.getName());
//		property.setDescription(target.getDescription());
//		property.setCustom(target.isCustom());
		property.setMultiple(target.isMultiple());
		property.setType(target.getType());
		property.setAllowedValues(target.getAllowedValues());
		property.setUnit(target.getUnit());
		property.setRequired(target.isRequired());
		property.setPropertyConstraints(target.getFieldConstraints());
//		property.setTimestamp(target.getTimestamp());
		property.setVisible(target.isVisible());
		property.setTabId(target.getTabId());
		
		return property;
	}

	@Override
	public List<ClassProperty<Object>> toSources(List<XDynamicField> targets) {
		if (targets == null) {return null;}
		
		List<ClassProperty<Object>> sources = new ArrayList<>();
		for (XDynamicField target : targets) {
			sources.add(toSource(target));
		}
		return sources;
	}

	

}

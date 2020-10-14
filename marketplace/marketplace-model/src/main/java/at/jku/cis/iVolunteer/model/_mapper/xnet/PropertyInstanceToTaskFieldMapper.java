package at.jku.cis.iVolunteer.model._mapper.xnet;
// TODO remove
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
//import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
//import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
//import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
//import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
//import at.jku.cis.iVolunteer.model.task.TaskField;;
//
//@Component
//public class PropertyInstanceToTaskFieldMapper implements AbstractMapper<PropertyInstance<Object>, TaskField> {
//
//	@Override
//	public TaskField toTarget(PropertyInstance<Object> source) {
//		if (source == null) {
//			return null;
//		}
//		TaskField field = new TaskField(source);
//		return field;
//	}
//
//	@Override
//	public List<TaskField> toTargets(List<PropertyInstance<Object>> sources) {
//		if (sources == null) {
//			return null;
//		}
//
//		List<TaskField> targets = new ArrayList<>();
//		for (PropertyInstance<Object> pi : sources) {
//			targets.add(toTarget(pi));
//		}
//
//		return targets;
//	}
//
//	@Override
//	public PropertyInstance<Object> toSource(TaskField target) {
//		if (target == null) {
//			return null;
//		}
//
//		PropertyInstance<Object> pi = new PropertyInstance<>();
//		pi.setId(target.getKey());
//		pi.setName(target.getLabel());
//		pi.setType(target.getType());
//		pi.setRequired(target.isRequired());
//		pi.setUnit(target.getUnit());
//		pi.setAllowedValues(target.getAllowedValues());
//		pi.setPropertyConstraints(target.getConstraints());
//		pi.setValues(target.getValues());
//		return pi;
//	}
//
//	@Override
//	public List<PropertyInstance<Object>> toSources(List<TaskField> targets) {
//		if (targets == null) {
//			return null;
//		}
//
//		List<PropertyInstance<Object>> sources = new ArrayList<>();
//		for (TaskField tf : targets) {
//			sources.add(toSource(tf));
//		}
//
//		return sources;
//	}
//
//}

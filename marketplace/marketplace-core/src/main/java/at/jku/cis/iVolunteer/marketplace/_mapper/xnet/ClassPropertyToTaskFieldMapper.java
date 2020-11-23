package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.task.TaskField;;

@Component
public class ClassPropertyToTaskFieldMapper implements AbstractMapper<ClassProperty<Object>, TaskField> {

	@Override
	public TaskField toTarget(ClassProperty<Object> source) {
		if (source == null) {
			return null;
		}
		TaskField field = new TaskField(source);
		return field;
	}

	@Override
	public List<TaskField> toTargets(List<ClassProperty<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<TaskField> targets = new ArrayList<>();
		for (ClassProperty<Object> cp : sources) {
			targets.add(toTarget(cp));
		}

		return targets;
	}

	@Override
	public ClassProperty<Object> toSource(TaskField target) {
		if (target == null) {
			return null;
		}

		ClassProperty<Object> cp = new ClassProperty<>();
		cp.setId(target.getKey());
		cp.setName(target.getLabel());
		cp.setType(target.getType());
		cp.setRequired(target.isRequired());
		cp.setUnit(target.getUnit());
		cp.setAllowedValues(target.getAllowedValues());
		cp.setPropertyConstraints(target.getConstraints());
		return cp;
	}

	@Override
	public List<ClassProperty<Object>> toSources(List<TaskField> targets) {
		if (targets == null) {
			return null;
		}

		List<ClassProperty<Object>> sources = new ArrayList<>();
		for (TaskField tf : targets) {
			sources.add(toSource(tf));
		}

		return sources;
	}

}

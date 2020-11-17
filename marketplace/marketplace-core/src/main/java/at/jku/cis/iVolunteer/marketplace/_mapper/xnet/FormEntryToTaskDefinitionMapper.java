package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.DynamicTaskBlock;
import at.jku.cis.iVolunteer.model.task.RequiredTaskBlock;
import at.jku.cis.iVolunteer.model.task.TaskDefinition;

@Component
public class FormEntryToTaskDefinitionMapper implements AbstractMapper<FormEntry, TaskDefinition> {

	@Autowired
	ClassPropertyToTaskFieldMapper classPropertyToTaskFieldMapper;

	@Override
	public TaskDefinition toTarget(FormEntry source) {

		if (source == null) {
			return null;
		}

		TaskDefinition taskDefinition = new TaskDefinition();
		RequiredTaskBlock required = new RequiredTaskBlock();
		List<DynamicTaskBlock> dynamic = new ArrayList<>();

		int listEnd = source.getClassDefinitions().size() - 1;

		for (int i = listEnd; i >= 0; i--) {
			if (source.getClassDefinitions().get(i).getLevel() > 1) {
				// required.setTitle(source.getClassDefinitions().get(i).getName());
				// requiredFields.addAll(classPropertyToTaskFieldMapper.toTargets(source.getClassDefinitions().get(i).getProperties()));
				//
				// } else {
				DynamicTaskBlock dynamicBlock = new DynamicTaskBlock();
				dynamicBlock.setTitle(source.getClassDefinitions().get(i).getName());
				dynamicBlock.setFields(new ArrayList<>());
				dynamicBlock.getFields().addAll(
						classPropertyToTaskFieldMapper.toTargets(source.getClassDefinitions().get(i).getProperties()));
				dynamic.add(dynamicBlock);
			} else if (source.getClassDefinitions().get(i).getLevel() == 1) {
				required.setTitle(source.getClassDefinitions().get(i).getName());
			}
		}

		required.setExpired(false);
		required.setName(source.getClassDefinitions().get(0).getName());
		required.setId(source.getClassDefinitions().get(0).getId());
		required.setImage(source.getClassDefinitions().get(0).getImagePath());

		required.setStartDate(
				classPropertyToTaskFieldMapper.toTarget(findProperty("Starting Date", source.getClassProperties())));
		required.setEndDate(
				classPropertyToTaskFieldMapper.toTarget(findProperty("End Date", source.getClassProperties())));
		required.setDescripiton(
				classPropertyToTaskFieldMapper.toTarget(findProperty("Description", source.getClassProperties())));
		required.setPlace(
				classPropertyToTaskFieldMapper.toTarget(findProperty("Location", source.getClassProperties())));

		taskDefinition.setDynamic(dynamic);
		taskDefinition.setRequired(required);

		return taskDefinition;
	}

	private ClassProperty<Object> findProperty(String name, List<ClassProperty<Object>> properties) {
		ClassProperty<Object> property = properties.stream().filter(p -> p.getName().equals(name)).findAny()
				.orElse(null);
		return property;
	}

	@Override
	public List<TaskDefinition> toTargets(List<FormEntry> sources) {
		if (sources == null) {
			return null;
		}

		List<TaskDefinition> targets = new LinkedList<>();
		for (FormEntry entry : sources) {
			targets.add(toTarget(entry));
		}

		return targets;
	}

	@Override
	public FormEntry toSource(TaskDefinition target) {
		System.err.println("not implemented");
		return null;
	}

	@Override
	public List<FormEntry> toSources(List<TaskDefinition> targets) {
		System.err.println("not implemented");
		return null;
	}

}

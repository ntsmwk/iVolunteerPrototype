package at.jku.cis.iVolunteer.marketplace._mapper.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.TaskBlock;
import at.jku.cis.iVolunteer.model.task.TaskDefinition;
import at.jku.cis.iVolunteer.model.task.TaskField;;

@Component
public class FormEntryToTaskDefinitionMapper implements AbstractMapper<FormEntry, TaskDefinition> {

	@Autowired ClassPropertyToTaskFieldMapper classPropertyToTaskFieldMapper;
	
	@Override
	public TaskDefinition toTarget(FormEntry source) {
		
		if (source == null) { return null;}
		
		TaskDefinition taskDefinition = new TaskDefinition();
		TaskBlock required = new TaskBlock();
		List<TaskBlock> dynamic = new ArrayList<>();
		
		int listEnd = source.getClassDefinitions().size() - 1;
		List<TaskField> requiredFields = new ArrayList<>();
		
		for (int i = listEnd; i >= 0; i--) {
			if (source.getClassDefinitions().get(i).getLevel() <= 1) {
				required.setTitle(source.getClassDefinitions().get(i).getName());
				requiredFields.addAll(classPropertyToTaskFieldMapper.toTargets(source.getClassDefinitions().get(i).getProperties()));
			
			} else {
				TaskBlock dynamicBlock = new TaskBlock();
				dynamicBlock.setTitle(source.getClassDefinitions().get(i).getName());
				dynamicBlock.setFields(new ArrayList<>());
				dynamicBlock.getFields().addAll(classPropertyToTaskFieldMapper.toTargets(source.getClassDefinitions().get(i).getProperties()));
				dynamic.add(dynamicBlock);
			}
		}
		
		required.setFields(requiredFields);
		taskDefinition.setRequired(required);
		taskDefinition.setDynamic(dynamic);

		return taskDefinition;
	}
	

	@Override
	public List<TaskDefinition> toTargets(List<FormEntry> sources) {
		if (sources == null) { return null;}
		
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

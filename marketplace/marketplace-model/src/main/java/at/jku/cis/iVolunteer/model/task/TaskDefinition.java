package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;


public class TaskDefinition {

	TaskBlock required;
	List<TaskBlock> dynamic = new ArrayList<>();
	
	public TaskBlock getRequired() {
		return required;
	}
	public void setRequired(TaskBlock required) {
		this.required = required;
	}
	public List<TaskBlock> getDynamic() {
		return dynamic;
	}
	public void setDynamic(List<TaskBlock> dynamic) {
		this.dynamic = dynamic;
	}
	
	
}

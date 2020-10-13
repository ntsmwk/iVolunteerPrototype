package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;


public class TaskDefinition {

	RequiredTaskBlock required;
	List<DynamicTaskBlock> dynamic = new ArrayList<>();
	
	public RequiredTaskBlock getRequired() {
		return required;
	}
	public void setRequired(RequiredTaskBlock required) {
		this.required = required;
	}
	public List<DynamicTaskBlock> getDynamic() {
		return dynamic;
	}
	public void setDynamic(List<DynamicTaskBlock> dynamic) {
		this.dynamic = dynamic;
	}
	
	
}

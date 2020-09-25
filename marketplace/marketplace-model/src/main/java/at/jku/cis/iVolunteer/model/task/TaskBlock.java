package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;

public class TaskBlock {
	
	String title;
	List<TaskField> fields = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<TaskField> getFields() {
		return fields;
	}
	public void setFields(List<TaskField> fields) {
		this.fields = fields;
	}
	
}

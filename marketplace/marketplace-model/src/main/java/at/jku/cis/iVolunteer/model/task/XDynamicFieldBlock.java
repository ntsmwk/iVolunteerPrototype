package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;

public class XDynamicFieldBlock {
	
	String title;
	List<XDynamicField> fields = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<XDynamicField> getFields() {
		return fields;
	}
	public void setFields(List<XDynamicField> fields) {
		this.fields = fields;
	}
	
}

package at.jku.cis.iVolunteer.model.meta.form;

import java.util.List;

public class FormConfiguration {

	String name;
	List<FormEntry> formEntries;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FormEntry> getFormEntries() {
		return formEntries;
	}
	public void setFormEntries(List<FormEntry> formEntries) {
		this.formEntries = formEntries;
	}
	
}

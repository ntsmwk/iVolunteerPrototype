package at.jku.cis.iVolunteer.model.meta.form;

public class FormConfiguration {

	String id;
	String name;
	FormEntry formEntry;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FormEntry getFormEntry() {
		return formEntry;
	}

	public void setFormEntry(FormEntry formEntry) {
		this.formEntry = formEntry;
	}


}

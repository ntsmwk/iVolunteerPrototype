package at.jku.cis.iVolunteer.model.meta.form;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class EnumRepresentation {
	
	String id;
	List<EnumEntry> enumEntries;
	List<EnumEntry> selectedEntries;
	ClassDefinition classDefinition;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<EnumEntry> getEnumEntries() {
		return enumEntries;
	}
	public void setEnumEntries(List<EnumEntry> enumEntries) {
		this.enumEntries = enumEntries;
	}
	public List<EnumEntry> getSelectedEntries() {
		return selectedEntries;
	}
	public void setSelectedEntries(List<EnumEntry> selectedEntries) {
		this.selectedEntries = selectedEntries;
	}
	
	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}
	
	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}
	
	
}

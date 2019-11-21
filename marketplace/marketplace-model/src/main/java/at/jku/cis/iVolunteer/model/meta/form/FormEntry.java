package at.jku.cis.iVolunteer.model.meta.form;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class FormEntry {

	private String positionLevel;
	private List<ClassProperty<Object>> classProperties;
	private List<ClassDefinition> classDefinitions;
	
	private List<FormEntry> subEntries;
	
	
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	
	public List<ClassProperty<Object>> getClassProperties() {
		return classProperties;
	}
	public void setClassProperties(List<ClassProperty<Object>> classProperties) {
		this.classProperties = classProperties;
	}
	
	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}
	public void setClassDefinitons(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}
	
	public List<FormEntry> getSubEntries() {
		return subEntries;
	}
	public void setSubEntries(List<FormEntry> subEntries) {
		this.subEntries = subEntries;
	}
	
	

	

}

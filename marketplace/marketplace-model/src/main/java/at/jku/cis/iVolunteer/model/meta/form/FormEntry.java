package at.jku.cis.iVolunteer.model.meta.form;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class FormEntry {

	private List<ClassProperty<Object>> classProperties = new ArrayList<>();
	private List<ClassDefinition> classDefinitions = new ArrayList<>();
	
	private List<EnumRepresentation> enumRepresentations = new ArrayList<>();
	
	private List<FormEntry> subEntries = new ArrayList<>();

	private String imagePath;

	public List<ClassProperty<Object>> getClassProperties() {
		return classProperties;
	}

	public void setClassProperties(List<ClassProperty<Object>> classProperties) {
		this.classProperties = classProperties;
	}

	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	public List<EnumRepresentation> getEnumRepresentations() {
		return enumRepresentations;
	}

	public void setEnumRepresentations(List<EnumRepresentation> enumRepresentations) {
		this.enumRepresentations = enumRepresentations;
	}

	public List<FormEntry> getSubEntries() {
		return subEntries;
	}

	public void setSubEntries(List<FormEntry> subEntries) {
		this.subEntries = subEntries;
	}


	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	


	

}

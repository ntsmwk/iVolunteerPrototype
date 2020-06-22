package at.jku.cis.iVolunteer.model.meta.form;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.configurations.enums.EnumDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class FormEntry {

	private String id;
	private List<ClassProperty<Object>> classProperties = new ArrayList<>();
	private List<ClassDefinition> classDefinitions = new ArrayList<>();	
	private List<EnumDefinition> enumDefinitions = new ArrayList<>();
	
	private List<FormEntry> subEntries = new ArrayList<>();

	private String imagePath;
	
	
	public FormEntry(String id) {
		this.id = id;
	}
	
	public FormEntry() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	public List<EnumDefinition> getEnumDefinitions() {
		return enumDefinitions;
	}

	public void setEnumDefinitions(List<EnumDefinition> enumDefinitions) {
		this.enumDefinitions = enumDefinitions;
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

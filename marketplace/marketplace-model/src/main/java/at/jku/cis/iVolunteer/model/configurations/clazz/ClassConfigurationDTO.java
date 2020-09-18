package at.jku.cis.iVolunteer.model.configurations.clazz;

import java.util.List;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;

public class ClassConfigurationDTO extends IVolunteerObject {

	private ClassConfiguration classConfiguration;
	private List<ClassDefinition> classDefinitions;
	private List<Relationship> relationships;
	
	public ClassConfigurationDTO() {};
	
	public ClassConfigurationDTO(ClassConfiguration classConfiguration, List<ClassDefinition> classDefinitions, List<Relationship> relationships) {
		this.classConfiguration = classConfiguration;
		this.classDefinitions = classDefinitions;
		this.relationships = relationships;
	}
	
	public ClassConfiguration getClassConfiguration() {
		return classConfiguration;
	}
	public void setClassConfiguration(ClassConfiguration classConfiguration) {
		this.classConfiguration = classConfiguration;
	}
	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}
	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}
	public List<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}
	
	
}

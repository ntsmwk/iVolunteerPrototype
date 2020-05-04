package at.jku.cis.iVolunteer.model.meta.form;

import java.util.List;

import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;

public class FormConfigurationPreviewRequest {
	
	List<ClassDefinition> classDefinitions;
	List<Relationship> relationships;
	ClassDefinition rootClassDefinition;
	
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
	public ClassDefinition getRootClassDefinition() {
		return rootClassDefinition;
	}
	public void setRootClassDefinition(ClassDefinition rootClassDefinition) {
		this.rootClassDefinition = rootClassDefinition;
	}
	
	

}



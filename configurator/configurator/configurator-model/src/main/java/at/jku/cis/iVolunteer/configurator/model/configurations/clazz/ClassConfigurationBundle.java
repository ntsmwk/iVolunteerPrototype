package at.jku.cis.iVolunteer.configurator.model.configurations.clazz;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;

public class ClassConfigurationBundle {
	ClassConfiguration classConfiguration;
	List<ClassDefinition> classDefinitions;
	List<Relationship> relationships;

	
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

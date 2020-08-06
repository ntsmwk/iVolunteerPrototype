package at.jku.cis.iVolunteer.model.meta.form;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

public class FormConfigurationPreviewRequest {

	List<ClassDefinition> classDefinitions;
	List<RelationshipDTO> relationships;
	ClassDefinition rootClassDefinition;

	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	public List<RelationshipDTO> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<RelationshipDTO> relationships) {
		this.relationships = relationships;
	}

	public ClassDefinition getRootClassDefinition() {
		return rootClassDefinition;
	}

	public void setRootClassDefinition(ClassDefinition rootClassDefinition) {
		this.rootClassDefinition = rootClassDefinition;
	}

}

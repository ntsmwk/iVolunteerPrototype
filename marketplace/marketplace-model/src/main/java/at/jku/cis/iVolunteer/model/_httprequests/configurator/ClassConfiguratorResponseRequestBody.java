package at.jku.cis.iVolunteer.model._httprequests.configurator;

import java.util.List;

import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

public class ClassConfiguratorResponseRequestBody {
//	--body: class-configuraton + classDefinitions + relationships

	private String action;
	private ClassConfiguration classConfiguration;
	private List<ClassDefinition> classDefinitions;
	private List<RelationshipDTO> relationships;
	private List<String> idsToDelete;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	public List<RelationshipDTO> getRelationships() {
		return relationships;
	}
	public void setRelationships(List<RelationshipDTO> relationships) {
		this.relationships = relationships;
	}
	public List<String> getIdsToDelete() {
		return idsToDelete;
	}
	public void setIdsToDelete(List<String> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}
	
	
	
	
}

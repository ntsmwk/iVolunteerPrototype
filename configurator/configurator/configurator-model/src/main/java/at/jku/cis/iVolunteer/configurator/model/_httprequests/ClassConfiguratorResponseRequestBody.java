package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;


public class ClassConfiguratorResponseRequestBody {
	private String action;
	private ClassConfiguration classConfiguration;
	private List<ClassDefinition> classDefinitions;
	private List<RelationshipDTO> relationships;	
	private List<String> idsToDelete;

	private List<FlatPropertyDefinition<Object>> flatPropertyDefinitions;
	private List<TreePropertyDefinition> treePropertyDefinitions;
	
	
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
	public List<FlatPropertyDefinition<Object>> getFlatPropertyDefinitions() {
		return flatPropertyDefinitions;
	}
	public void setFlatPropertyDefinitions(List<FlatPropertyDefinition<Object>> flatPropertyDefinitions) {
		this.flatPropertyDefinitions = flatPropertyDefinitions;
	}
	public List<TreePropertyDefinition> getTreePropertyDefinitions() {
		return treePropertyDefinitions;
	}
	public void setTreePropertyDefinitions(List<TreePropertyDefinition> treePropertyDefinitions) {
		this.treePropertyDefinitions = treePropertyDefinitions;
	}
	
	
	
	
	
}

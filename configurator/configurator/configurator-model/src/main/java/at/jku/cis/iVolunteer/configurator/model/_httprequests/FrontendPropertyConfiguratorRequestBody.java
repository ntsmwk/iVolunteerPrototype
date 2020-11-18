package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public class FrontendPropertyConfiguratorRequestBody {

	String action; //"save" or "delete"
//	List<String> flatPropertyDefinitionIds;
//	List<String> treePropertyDefinitionIds;
//	
	List<FlatPropertyDefinition<Object>> flatPropertyDefinitions; 
	List<TreePropertyDefinition> treePropertyDefinitions;
	String url;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
//	public List<String> getFlatPropertyDefinitionIds() {
//		return flatPropertyDefinitionIds;
//	}
//	public void setFlatPropertyDefinitionIds(List<String> flatPropertyDefinitionIds) {
//		this.flatPropertyDefinitionIds = flatPropertyDefinitionIds;
//	}
//	public List<String> getTreePropertyDefinitionIds() {
//		return treePropertyDefinitionIds;
//	}
//	public void setTreePropertyDefinitionIds(List<String> treePropertyDefinitionIds) {
//		this.treePropertyDefinitionIds = treePropertyDefinitionIds;
//	}
	
	public String getUrl() {
		return url;
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
	public void setUrl(String url) {
		this.url = url;
	}
	
}

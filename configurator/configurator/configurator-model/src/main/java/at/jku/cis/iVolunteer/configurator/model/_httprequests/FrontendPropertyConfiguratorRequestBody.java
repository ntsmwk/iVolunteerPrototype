package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public class FrontendPropertyConfiguratorRequestBody {

	String action;

	List<FlatPropertyDefinition<Object>> flatPropertyDefinitions; 
	List<TreePropertyDefinition> treePropertyDefinitions;
	String url;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
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

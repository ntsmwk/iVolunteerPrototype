package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

public class FrontendPropertyConfiguratorRequestBody {

	String action; //"save" or "delete"
	List<String> flatPropertyDefinitionIds;
	List<String> treePropertyDefinitionIds;
	String url;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getFlatPropertyDefinitionIds() {
		return flatPropertyDefinitionIds;
	}
	public void setFlatPropertyDefinitionIds(List<String> flatPropertyDefinitionIds) {
		this.flatPropertyDefinitionIds = flatPropertyDefinitionIds;
	}
	public List<String> getTreePropertyDefinitionIds() {
		return treePropertyDefinitionIds;
	}
	public void setTreePropertyDefinitionIds(List<String> treePropertyDefinitionIds) {
		this.treePropertyDefinitionIds = treePropertyDefinitionIds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

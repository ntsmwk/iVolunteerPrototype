package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public class PropertyConfiguratorResponseRequestBody {
	
	String action;
	
	String tenantId;
	List<FlatPropertyDefinition<Object>> flatPropertyDefinitions;
	List<TreePropertyDefinition> treePropertyDefinitions;
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

package at.jku.cis.iVolunteer.model.meta.core;

import java.util.List;


public class ClassPropertyRequestObject {

	List<String> flatPropertyDefinitionIds;
	List<String> treePropertyDefinitionIds;
	
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
	


	

}

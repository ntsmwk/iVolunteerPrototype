package at.jku.cis.iVolunteer.model.configurable.configurables;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;

@Document
public class Relationship extends ConfigurableObject {
	
	String nextObjectId;
	String relationshipType;
	
	public Relationship() {
		super.setConfigurableType("relationship");
	}

	public String getId() {
		return super.getId();
	}
	
	public void setId(String id) {
		super.setId(id);
	}

	public String getConfigurableType() {
		return super.getConfigurableType();
	}

	public String getNextObjectId() {
		return nextObjectId;
	}

	public void setNextObjectId(String nextObjectId) {
		this.nextObjectId = nextObjectId;
	}
	
	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
	
	
	
	
	

	

}

package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="relationship")
public class Inheritance extends Relationship {
	
	//TODO @MWE ?? why needed
	String superClassId;
	
	public Inheritance() {
		this.relationshipType = RelationshipType.INHERITANCE;
	}
	
	public Inheritance(String child, String parent, String superClassId) {
		super(child, parent);
		this.superClassId = superClassId;
		this.relationshipType = RelationshipType.INHERITANCE;
	}
	
	public Inheritance(Relationship relationship) {
		super(relationship.source, relationship.target);
		this.id = relationship.getId();
		this.relationshipType = relationship.getRelationshipType();
	}
	
	public String getSuperClassId() {
		return superClassId;
	}
	
	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}
	
	
	
	
	
	
	

	

}

package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Inheritance extends Relationship {
	
	String superClassId;
	
	public Inheritance() {
		this.relationshipType = RelationshipType.INHERITANCE;
	}
	
	public Inheritance(String classId1, String classId2, String superClassId) {
		this.superClassId = superClassId;
		this.classId1 = classId1;
		this.classId2 = classId2;
		this.relationshipType = RelationshipType.INHERITANCE;
	}
	
	public Inheritance(Relationship relationship) {
		this.id = relationship.getId();
		this.classId1 = relationship.getClassId1();
		this.classId2 = relationship.getClassId2();
		this.relationshipType = relationship.getRelationshipType();
	}
	
	public String getSuperClassId() {
		return superClassId;
	}
	
	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}
	
	
	
	
	
	
	

	

}

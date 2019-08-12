package at.jku.cis.iVolunteer.model.configurable.class_.relationship;

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
	
	public String getSuperClassId() {
		return superClassId;
	}
	
	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}
	
	
	
	
	
	
	

	

}

package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Relationship {
	
	@Id String id;
	
	String classId1;
	String classId2;
		
	RelationshipType relationshipType;
	
	public Relationship() {
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getClassId1() {
		return classId1;
	}
	
	public void setClassId1(String classId1) {
		this.classId1 = classId1;
	}
	
	public String getClassId2() {
		return classId2;
	}
	
	public void setClassId2(String classId2) {
		this.classId2 = classId2;
	}
	

	
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
	

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Relationship)) {
			return false;
		}
		return ((Relationship) obj).id.equals(id);
	}
	
	
	
	
	

	

}

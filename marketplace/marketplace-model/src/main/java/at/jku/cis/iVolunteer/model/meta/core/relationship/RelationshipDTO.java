package at.jku.cis.iVolunteer.model.meta.core.relationship;


public class RelationshipDTO {
	
	//General Fields
	String id;
	
	String classId1;
	String classId2;
		
	RelationshipType relationshipType;
	
	//Association Fields
	AssociationParameter param1;
	AssociationParameter param2;
	
	//Inheritance Fields
	String superClassId;

	
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

	public AssociationParameter getParam1() {
		return param1;
	}

	public void setParam1(AssociationParameter param1) {
		this.param1 = param1;
	}

	public AssociationParameter getParam2() {
		return param2;
	}

	public void setParam2(AssociationParameter param2) {
		this.param2 = param2;
	}

	public String getSuperClassId() {
		return superClassId;
	}

	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}
	
	
}

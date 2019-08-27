package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Association extends Relationship {
	
	AssociationParameter param1;
	AssociationParameter param2;
	
	public Association() {
		this.relationshipType = RelationshipType.ASSOCIATION;
	}
	
	public Association(String classId1, String classId2, AssociationParameter param1, AssociationParameter param2) {
		this.classId1 = classId1;
		this.classId2 = classId2;
		this.param1 = param1;
		this.param2 = param2;
		this.relationshipType = RelationshipType.ASSOCIATION;
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
	
	public void setParam2(AssociationParameter param1) {
		this.param2 = param1;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Association)) {
			return false;
		}
		return ((Association) obj).id.equals(id);
	}
	
	
	

	

}

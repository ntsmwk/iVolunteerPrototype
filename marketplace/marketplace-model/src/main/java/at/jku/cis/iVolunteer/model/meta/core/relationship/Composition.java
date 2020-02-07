package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
public class Composition extends Relationship {

	public Composition() {
		this.relationshipType = RelationshipType.COMPOSITION;
	}

	public Composition(String child, String parent, String superClassId) {
		super(child, parent);
		this.relationshipType = RelationshipType.COMPOSITION;
	}

	public Composition(Relationship relationship) {
		super(relationship.source, relationship.target);
		this.id = relationship.getId();
		this.relationshipType = relationship.getRelationshipType();
	}
	
	//superclass is always targetclass!
	public String getSuperClass() {
		return this.target;
	}

}

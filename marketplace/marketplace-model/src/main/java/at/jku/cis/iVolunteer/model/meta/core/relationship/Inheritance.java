package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
public class Inheritance extends Relationship {

	public Inheritance() {
		this.relationshipType = RelationshipType.INHERITANCE;
	}

	public Inheritance(String child, String parent) {
		super(child, parent);
		this.relationshipType = RelationshipType.INHERITANCE;
	}

	public Inheritance(Relationship relationship) {
		super(relationship.source, relationship.target);
		this.id = relationship.getId();
		this.relationshipType = relationship.getRelationshipType();
	}

}

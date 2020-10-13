package at.jku.cis.iVolunteer.model.meta.core.relationship;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
public class Relationship {

	@Id protected String id;

	protected String source;
	protected String target;

	protected RelationshipType relationshipType;

	public Relationship() {
	}

	public Relationship(String source, String target) {
		this.source = source;
		this.target = target;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

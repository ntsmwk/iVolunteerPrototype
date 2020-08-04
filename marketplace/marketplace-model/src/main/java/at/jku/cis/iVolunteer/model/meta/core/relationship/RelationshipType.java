package at.jku.cis.iVolunteer.model.meta.core.relationship;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RelationshipType {
	INHERITANCE("INHERITANCE"), ASSOCIATION("ASSOCIATION"), AGGREGATION("AGGREGATION");

	private final String relationshipType;

	private RelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	@Override
	public String toString() {
		return relationshipType;
	}

	public String getRelationshipType() {
		return this.relationshipType;
	}

	@JsonCreator
	public static RelationshipType getFromRelationshipType(String type) {
		for (RelationshipType t : RelationshipType.values()) {
			if (t.getRelationshipType().equals(type)) {
				return t;
			}
		}
		throw new IllegalArgumentException();
	}
}

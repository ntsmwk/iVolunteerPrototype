package at.jku.cis.iVolunteer.configurator.model.meta.core.relationship;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
public class Association extends Relationship {
	
	AssociationCardinality sourceCardinality;
	AssociationCardinality targetCardinality;
	
	public Association() {
		this.relationshipType = RelationshipType.ASSOCIATION;
	}
	
	public Association(String source, String target, AssociationCardinality sourceCardinality, AssociationCardinality targetCardinality) {
		this.setSource(source);
		this.setTarget(target);
		this.sourceCardinality = sourceCardinality;
		this.targetCardinality = targetCardinality;
		this.relationshipType = RelationshipType.ASSOCIATION;
	}
	
	public Association(Relationship relationship) {
		this.id = relationship.getId();
		this.setSource(relationship.source);
		this.setTarget(relationship.target);
		this.relationshipType = relationship.getRelationshipType();
	}

	public AssociationCardinality getSourceCardinality() {
		return sourceCardinality;
	}

	public void setSourceCardinality(AssociationCardinality sourceCardinality) {
		this.sourceCardinality = sourceCardinality;
	}

	public AssociationCardinality getTargetCardinality() {
		return targetCardinality;
	}

	public void setTargetCardinality(AssociationCardinality targetCardinality) {
		this.targetCardinality = targetCardinality;
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

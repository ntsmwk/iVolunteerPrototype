package at.jku.cis.iVolunteer.model.meta.core.relationship;


public class RelationshipDTO {
	
	private String id;

	private String source;
	private String target;

	private RelationshipType relationshipType;
	
	private AssociationCardinality sourceCardinality;
	private AssociationCardinality targetCardinality;
	
	
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
		if (!(obj instanceof RelationshipDTO)) {
			return false;
		}
		return ((RelationshipDTO) obj).id.equals(id);
	}

}

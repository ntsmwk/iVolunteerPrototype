package at.jku.cis.iVolunteer.model.meta.core.relationship;

public enum AssociationCardinality {
	NONE(""), ONE("ONE"), N("N");
	
	private final String associationCardinality;
	
	private AssociationCardinality (String associationCardinality) {
		this.associationCardinality = associationCardinality;
	}

	@Override
	public String toString() {
		return associationCardinality;
	}
	
	public String getAssociationCardinality() {
		return this.associationCardinality;
	}
	
	public static AssociationCardinality getFromRelationshipType(String type){
        for(AssociationCardinality t : AssociationCardinality.values()){
            if(t.getAssociationCardinality().equals(type)){
                return t;
            }
        }
        throw new IllegalArgumentException();
    }	
}

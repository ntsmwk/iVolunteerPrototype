package at.jku.cis.iVolunteer.model.meta.core.relationship;

public enum AssociationParameter {
	NONE(""), ONE("1"), ZEROONE("0...1"), ZEROSTAR("0...*"), ONESTAR("1...*");
	
	private final String associationParameter;
	
	private AssociationParameter (String associationParameter) {
		this.associationParameter = associationParameter;
	}

	@Override
	public String toString() {
		return associationParameter;
	}
	
	public String getAssociationParameter() {
		return this.associationParameter;
	}
	
	public static AssociationParameter getFromRelationshipType(String type){
        for(AssociationParameter t : AssociationParameter.values()){
            if(t.getAssociationParameter().equals(type)){
                return t;
            }
        }
        throw new IllegalArgumentException();
    }	
}

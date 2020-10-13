package at.jku.cis.iVolunteer.model.meta.constraint;


public enum ConstraintType {
	MIN("min"), MAX("max"), MIN_LENGTH("min_length"), MAX_LENGTH("max_length"), PATTERN("pattern");

	private final String type;

	private ConstraintType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static ConstraintType getFromConstraintType(String type) {
		for(ConstraintType t : ConstraintType.values()){
            if(t.getType().equals(type)){
                return t;
            }
        }
        throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return type;
	}

}


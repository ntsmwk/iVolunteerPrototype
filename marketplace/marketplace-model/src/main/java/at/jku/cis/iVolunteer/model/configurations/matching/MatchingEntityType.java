package at.jku.cis.iVolunteer.model.configurations.matching;


public enum MatchingEntityType {
	PROPERTY("PROPERTY"), CLASS("CLASS");

	private final String type;

	private MatchingEntityType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static MatchingEntityType getFromMatchingEntityType(String type) {
		for(MatchingEntityType t : MatchingEntityType.values()){
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


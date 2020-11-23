package at.jku.cis.iVolunteer.configurator.model.configurations.matching;


public enum MatchingOperatorType {
	EQUAL("EQUAL"), LESS("LESS"), GREATER("GREATER"), LESS_EQUAL("LESS_EQUAL"), GREATER_EQUAL("GREATER_EQUAL"), EXISTS("EXISTS"), ALL("ALL");

	private final String type;

	private MatchingOperatorType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static MatchingOperatorType getFromMatchingOperatorType(String type) {
		for(MatchingOperatorType t : MatchingOperatorType.values()){
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


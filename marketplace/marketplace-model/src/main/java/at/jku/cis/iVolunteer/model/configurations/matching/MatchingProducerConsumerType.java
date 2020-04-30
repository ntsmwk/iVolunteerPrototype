package at.jku.cis.iVolunteer.model.configurations.matching;


public enum MatchingProducerConsumerType {
	PROPERTY("PROPERTY"), CLASS("CLASS");

	private final String type;

	private MatchingProducerConsumerType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static MatchingProducerConsumerType getFromMatchingProducerConsumerType(String type) {
		for(MatchingProducerConsumerType t : MatchingProducerConsumerType.values()){
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


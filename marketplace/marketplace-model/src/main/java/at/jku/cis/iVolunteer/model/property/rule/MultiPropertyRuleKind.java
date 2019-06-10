package at.jku.cis.iVolunteer.model.property.rule;


public enum MultiPropertyRuleKind {
	REQUIRED_OTHER("REQUIRED_OTHER"), MAX_OTHER("MAX_OTHER"), MIN_OTHER("MIN_OTHER");
	
	private final String kind;
	
	private MultiPropertyRuleKind (String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public static MultiPropertyRuleKind getFromRuleKind(String kind){
        for(MultiPropertyRuleKind k : MultiPropertyRuleKind.values()){
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
    }
	
}

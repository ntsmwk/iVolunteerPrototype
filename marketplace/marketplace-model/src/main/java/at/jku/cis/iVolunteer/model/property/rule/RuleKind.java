package at.jku.cis.iVolunteer.model.property.rule;


public enum RuleKind {
	REQUIRED("REQUIRED"), REQUIRED_TRUE("REQUIRED_TRUE"), REGEX_PATTERN("REGEX_PATTERN"),
	MAX("MAX"), MIN("MIN"), MAX_LENGTH("MAX_LENGTH"), MIN_LENGTH("MIN_LENGTH"), 
	/*EQUAL("EQUAL"),  GREATER("GREATER"), LESS("LESS")*/ ;
	
	private final String kind;
	
	private RuleKind (String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public static RuleKind getFromRuleKind(String kind){
        for(RuleKind k : RuleKind.values()){
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
    }
	
}

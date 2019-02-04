package at.jku.cis.iVolunteer.model.property.rule;


public enum RuleKind {
	MANDATORY("MANDATORY"), MAX("MAX"), MIN("MIN"), GREATER("GREATER"), GREATER_OR_EQUAL("GREATER_OR_EQUAL"), 
		LESS("LESS"), LESS_OR_EQUAL("LESS_OR_EQUAL"), EQUAL("EQUAL");
	
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

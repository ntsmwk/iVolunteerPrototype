package at.jku.cis.iVolunteer.model.property.rule;


public enum SinglePropertyRuleKind {
	REQUIRED("REQUIRED"), REQUIRED_TRUE("REQUIRED_TRUE"), REGEX_PATTERN("REGEX_PATTERN"),
	MAX("MAX"), MIN("MIN"), MAX_LENGTH("MAX_LENGTH"), MIN_LENGTH("MIN_LENGTH");
	
	private final String kind;
	
	private SinglePropertyRuleKind (String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public static SinglePropertyRuleKind getFromRuleKind(String kind){
        for(SinglePropertyRuleKind k : SinglePropertyRuleKind.values()){
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
    }
	
}

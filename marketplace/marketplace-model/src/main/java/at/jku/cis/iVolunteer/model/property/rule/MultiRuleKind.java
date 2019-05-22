package at.jku.cis.iVolunteer.model.property.rule;


public enum MultiRuleKind {
	REQUIRED_OTHER("REQUIRED_OTHER"), MAX_OTHER("MAX_OTHER"), MIN_OTHER("MIN_OTHER");
	
	private final String kind;
	
	private MultiRuleKind (String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public static MultiRuleKind getFromRuleKind(String kind){
        for(MultiRuleKind k : MultiRuleKind.values()){
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
    }
	
}

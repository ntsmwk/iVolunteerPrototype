package at.jku.cis.iVolunteer.model.property;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum LegalValueKind {
	WHOLE_NUMBERS("WHOLE_NUMBERS"), FLOAT_NUMBERS("FLOAT_NUMBERS"), ALL("ALL"), BOOLEAN("BOOLEAN"), 
		REGEX("REGEX"), LIST("LIST");
	
	String kind;
	
	private LegalValueKind(String kind) {
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		return kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	@JsonCreator
	public static LegalValueKind gromRuleKind(String kind){
        for(LegalValueKind k : LegalValueKind.values()) {
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
    }
	

}

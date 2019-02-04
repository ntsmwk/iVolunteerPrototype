package at.jku.cis.iVolunteer.model.property;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LegalValueKind {
	WHOLE_NUMBERS("whole_numbers"), FLOAT_NUMBERS("float_numbers"), ALL("all"), BOOLEAN("boolean"), 
		REGEX("regex"), LIST("list");
	
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

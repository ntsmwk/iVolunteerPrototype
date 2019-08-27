package at.jku.cis.iVolunteer.model.meta.core.property;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PropertyType {
	TEXT("TEXT"), LONG_TEXT("LONG_TEXT"), WHOLE_NUMBER("WHOLE_NUMBER"), FLOAT_NUMBER("FLOAT_NUMBER"), BOOL("BOOL"), 
	DATE("DATE"), LIST("LIST"), GRAPH("GRAPH"), MAP("MAP"), MULTI("MULTI"); 

	private final String kind;

	private PropertyType(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	@JsonCreator
	public static PropertyType getFromPropertyKind(String kind) {
		for(PropertyType k : PropertyType.values()){
            if(k.getKind().equals(kind)){
                return k;
            }
        }
        throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return kind;
	}

}


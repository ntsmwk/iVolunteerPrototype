package at.jku.cis.iVolunteer.model.meta.core.property;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PropertyType {
	TEXT("TEXT"), LONG_TEXT("LONG_TEXT"), WHOLE_NUMBER("WHOLE_NUMBER"), FLOAT_NUMBER("FLOAT_NUMBER"), BOOL("BOOL"), 
	DATE("DATE"), ENUM("ENUM"), 
	
	//TODO remove
	LEVEL_LIST("LEVEL_LIST"), GRAPH("GRAPH"), MAP("MAP"), MULTI("MULTI"), LIST("LIST")
	; 

	private final String type;

	private PropertyType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	@JsonCreator
	public static PropertyType getPropertyType(String type) {
		for(PropertyType k : PropertyType.values()){
            if(k.getType().equals(type)){
                return k;
            }
        }
        throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return type;
	}

}


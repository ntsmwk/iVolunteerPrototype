package at.jku.cis.iVolunteer.model.property;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PropertyKind {
	TEXT("TEXT"), LONG_TEXT("LONG_TEXT"), WHOLE_NUMBER("WHOLE_NUMBER"), FLOAT_NUMBER("FLOAT_NUMBER"), BOOL("BOOL"), 
	DATE("DATE"), LIST("LIST"), GRAPH("GRAPH"), MAP("MAP"), MULTIPLE("MULTIPLE"); 

	private final String kind;

	private PropertyKind(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	@JsonCreator
	public static PropertyKind getFromPropertyKind(String kind) {
		for(PropertyKind k : PropertyKind.values()){
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


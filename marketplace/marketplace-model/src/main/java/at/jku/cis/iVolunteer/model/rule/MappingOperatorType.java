package at.jku.cis.iVolunteer.model.rule;

public enum MappingOperatorType {
	EQ("="), LT("<"), LE("<="), GT(">"), GE(">="), NE("!=");

	private String value;

	private MappingOperatorType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

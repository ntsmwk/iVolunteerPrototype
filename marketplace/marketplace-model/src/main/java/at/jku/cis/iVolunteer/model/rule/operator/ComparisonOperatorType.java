package at.jku.cis.iVolunteer.model.rule.operator;

public enum ComparisonOperatorType implements OperatorType {
	EQ("="), LT("<"), LE("<="), GT(">"), GE(">="), NE("!=");

	private String value;

	private ComparisonOperatorType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

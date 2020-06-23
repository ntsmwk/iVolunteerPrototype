package at.jku.cis.iVolunteer.model.rule.engine;

public enum RuleStatus {
	OK("OK"), ERROR("Error"), FIRED("fired"), NOT_FIRED("not fired");

	private String value;

	private RuleStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

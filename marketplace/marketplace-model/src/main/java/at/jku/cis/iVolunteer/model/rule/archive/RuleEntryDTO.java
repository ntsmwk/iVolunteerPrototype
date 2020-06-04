package at.jku.cis.iVolunteer.model.rule.archive;

public class RuleEntryDTO {
	
	private String key;
	public Object value;

	public RuleEntryDTO() {
	}

	public RuleEntryDTO(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}

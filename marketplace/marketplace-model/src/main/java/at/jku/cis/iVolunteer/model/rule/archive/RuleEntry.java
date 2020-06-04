package at.jku.cis.iVolunteer.model.rule.archive;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RuleEntry {

	private String key;
	private Object value;

	public RuleEntry() {
	}
	
	public RuleEntry(String key, Object value) {
		super();
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

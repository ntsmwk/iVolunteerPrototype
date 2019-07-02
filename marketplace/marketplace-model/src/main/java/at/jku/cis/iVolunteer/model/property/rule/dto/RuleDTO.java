package at.jku.cis.iVolunteer.model.property.rule.dto;

import at.jku.cis.iVolunteer.model.property.rule.RuleKind;

public class RuleDTO {
	

	String id;
	
	RuleKind kind;
	long value;
	String data;
	
	String message;
	
	/* Getters and Setters */ 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RuleKind getKind() {
		return kind;
	}

	public void setKind(RuleKind kind) {
		this.kind = kind;
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	/* Overrides */
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RuleDTO)) {
			return false;
		}
		return ((RuleDTO) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "\nRuleDTO [id=" + id + ", kind=" + kind + ", value=" + value + ", data=" + data + ", message=" + message
				+ "]";
	}
	
	

}


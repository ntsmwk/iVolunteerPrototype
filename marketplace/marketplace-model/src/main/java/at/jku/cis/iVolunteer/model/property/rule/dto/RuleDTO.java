package at.jku.cis.iVolunteer.model.property.rule.dto;

import at.jku.cis.iVolunteer.model.property.rule.RuleKind;

public class RuleDTO {
	

	String id;
	
	String kind;
	long value;
	String data;
	
	String key;
	String keyOther;
	
	String message;
	
	/* Getters and Setters */ 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
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
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKeyOther() {
		return keyOther;
	}
	
	public void setKeyOther(String keyOther) {
		this.keyOther = keyOther;
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


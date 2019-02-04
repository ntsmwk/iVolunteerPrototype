package at.jku.cis.iVolunteer.model.property.rule.dto;

import at.jku.cis.iVolunteer.model.property.rule.RuleKind;

public class RuleDTO {
	

	String id;
	
	RuleKind kind;
	int value;
	String regex;
	
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
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public void setRegex(String regex) {
		this.regex = regex;
	}
	
//	public String getKind() {
//		return kind;
//	}
//
//	public void setKind(String kind) {
//		this.kind = kind;
//	}
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

}


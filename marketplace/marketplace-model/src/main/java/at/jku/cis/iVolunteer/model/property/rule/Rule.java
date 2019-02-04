package at.jku.cis.iVolunteer.model.property.rule;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Rule {
	
	@Id
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
		if (!(obj instanceof Rule)) {
			return false;
		}
		return ((Rule) obj).id.equals(id);
	}

}


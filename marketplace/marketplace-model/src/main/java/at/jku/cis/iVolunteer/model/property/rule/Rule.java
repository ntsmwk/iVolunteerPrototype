package at.jku.cis.iVolunteer.model.property.rule;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Rule {
	
	@Id
	String id;
	
	RuleKind kind;
	long value;
	String data; //can be a Regular Expression or a key to a different property
	
	String message;

	
	/* Getters and Setters */ 
	
	public Rule() {}
	
	public Rule(RuleKind kind) {
		this.kind = kind;
		this.id = kind.getKind().toLowerCase();
	}
	
	public Rule(RuleKind kind, long value) {
		this.kind = kind;
		this.value = value;
		this.id = kind.getKind().toLowerCase();
	}
	
	public Rule(RuleKind kind, String data) {
		this.kind = kind;
		this.data = data;
		this.id = kind.getKind().toLowerCase();
	}

	
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
		if (!(obj instanceof Rule)) {
			return false;
		}
		return ((Rule) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "\nRule [id=" + id + ", kind=" + kind + ", value=" + value + ", data=" + data + ", message=" + message
				+ "]\n";
	}
	
	

}


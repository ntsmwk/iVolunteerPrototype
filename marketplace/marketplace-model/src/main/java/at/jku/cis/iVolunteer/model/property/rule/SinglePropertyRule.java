package at.jku.cis.iVolunteer.model.property.rule;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class SinglePropertyRule {
	
	@Id
	String id;
	
	SinglePropertyRuleKind kind;
	long value;
	String data; //can be a Regular Expression or a key to a different property
	
	String message;

	
	/* Getters and Setters */ 
	
	public SinglePropertyRule() {}
	
	public SinglePropertyRule(SinglePropertyRuleKind kind) {
		this.kind = kind;
		this.id = kind.getKind().toLowerCase();
	}
	
	public SinglePropertyRule(SinglePropertyRuleKind kind, long value) {
		this.kind = kind;
		this.value = value;
		this.id = kind.getKind().toLowerCase();
	}
	
	public SinglePropertyRule(SinglePropertyRuleKind kind, String data) {
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

	public SinglePropertyRuleKind getKind() {
		return kind;
	}

	public void setKind(SinglePropertyRuleKind kind) {
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
		if (!(obj instanceof SinglePropertyRule)) {
			return false;
		}
		return ((SinglePropertyRule) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "\nRule [id=" + id + ", kind=" + kind + ", value=" + value + ", data=" + data + ", message=" + message
				+ "]\n";
	}
	
	

}


package at.jku.cis.iVolunteer.model.property.rule;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class MultiPropertyRule {
	
	@Id
	String id;
	
	MultiPropertyRuleKind kind;
	long value;
	String key; //of property this rule is applied to
	String keyOther; //of property the rule refers to
	
	String message;

	
	/* Getters and Setters */ 
	
	public MultiPropertyRule() {}
	
	public MultiPropertyRule(MultiPropertyRuleKind kind, String key, String keyOther) {
		this.kind = kind;
		this.key = key;
		this.keyOther = keyOther;
		this.id = kind.getKind().toLowerCase();
	}
	
	public MultiPropertyRule(MultiPropertyRuleKind kind, String key, String keyOther, long value) {
		this.kind = kind;
		this.key = key;
		this.keyOther = keyOther;
		this.value = value;
		this.id = kind.getKind().toLowerCase();
	}


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MultiPropertyRuleKind getKind() {
		return kind;
	}

	public void setKind(MultiPropertyRuleKind kind) {
		this.kind = kind;
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
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
		if (!(obj instanceof MultiPropertyRule)) {
			return false;
		}
		return ((MultiPropertyRule) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "\nRule [id=" + id + ", kind=" + kind + ", value=" + value + ", key=" + key + ", message=" + message
				+ "]\n";
	}
	
	

}


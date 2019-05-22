package at.jku.cis.iVolunteer.model.property.rule;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class MultiRule {
	
	@Id
	String id;
	
	MultiRuleKind kind;
	long value;
	String key; //of property this rule is applied to
	String keyOther; //of property the rule refers to
	
	String message;

	
	/* Getters and Setters */ 
	
	public MultiRule() {}
	
	public MultiRule(MultiRuleKind kind, String key, String keyOther) {
		this.kind = kind;
		this.key = key;
		this.keyOther = keyOther;
		this.id = kind.getKind().toLowerCase();
	}
	
	public MultiRule(MultiRuleKind kind, String key, String keyOther, long value) {
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

	public MultiRuleKind getKind() {
		return kind;
	}

	public void setKind(MultiRuleKind kind) {
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
		if (!(obj instanceof MultiRule)) {
			return false;
		}
		return ((MultiRule) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "\nRule [id=" + id + ", kind=" + kind + ", value=" + value + ", key=" + key + ", message=" + message
				+ "]\n";
	}
	
	

}


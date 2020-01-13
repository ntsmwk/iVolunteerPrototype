package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SourceRuleEntry {

	private String classDefinitionId;
	private Object value;

	public SourceRuleEntry() {
	}

	public SourceRuleEntry(String classDefinitionId, Object value) {
		super();
		this.classDefinitionId = classDefinitionId;
		this.setValue(value);
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}

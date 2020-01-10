package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SourceRuleEntry {

	private String classDefinitionId;
	private String classPropertyId;
	private MappingOperatorType mappingOperatorType;
	private Object value;

	public SourceRuleEntry() {
	}

	public SourceRuleEntry(String classDefinitionId, String classPropertyId, MappingOperatorType mappingOperatorType,
			Object value) {
		super();
		this.classDefinitionId = classDefinitionId;
		this.classPropertyId = classPropertyId;
		this.mappingOperatorType = mappingOperatorType;
		this.setValue(value);
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}

	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}


	public String getClassPropertyId() {
		return classPropertyId;
	}

	public void setClassPropertyId(String classPropertyId) {
		this.classPropertyId = classPropertyId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}

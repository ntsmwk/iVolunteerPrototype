package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SourceRuleEntry {

	private String classDefinitionId;
	private String propertyDefinitionId;
	private MappingOperatorType mappingOperatorType;
	private String value;

	public SourceRuleEntry() {
	}

	public SourceRuleEntry(String classDefinitionId, String attributeId, MappingOperatorType mappingOperatorType,
			String value) {
		super();
		this.classDefinitionId = classDefinitionId;
		this.setPropertyDefinitionId(attributeId);
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPropertyDefinitionId() {
		return propertyDefinitionId;
	}

	public void setPropertyDefinitionId(String propertyDefinitionId) {
		this.propertyDefinitionId = propertyDefinitionId;
	}

}

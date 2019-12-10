package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SourceRuleEntry {

	private String classDefinitionId;
	private String attributeId;
	private MappingOperatorType mappingOperatorType;
	private String value;

	public SourceRuleEntry() {
	}

	public SourceRuleEntry(String classDefinitionId, String attributeId, MappingOperatorType mappingOperatorType,
			String value) {
		super();
		this.classDefinitionId = classDefinitionId;
		this.attributeId = attributeId;
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
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

}

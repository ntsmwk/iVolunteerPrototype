package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SourceRuleEntry {

	private String classDefinitionId;
	private MappingOperator mappingOperator;

	public SourceRuleEntry() {
	}

	public MappingOperator getMappingOperator() {
		return mappingOperator;
	}

	public void setMappingOperator(MappingOperator mappingOperator) {
		this.mappingOperator = mappingOperator;
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

}

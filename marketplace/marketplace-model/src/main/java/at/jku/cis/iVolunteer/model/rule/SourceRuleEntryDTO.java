package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class SourceRuleEntryDTO {

	private ClassDefinition classDefinition;
	private MappingOperator mappingOperator;

	public SourceRuleEntryDTO() {
	}

	public SourceRuleEntryDTO(ClassDefinition classDefinition, MappingOperator mappingOperator) {
		super();
		this.classDefinition = classDefinition;
		this.mappingOperator = mappingOperator;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public MappingOperator getMappingOperator() {
		return mappingOperator;
	}

	public void setMappingOperator(MappingOperator mappingOperator) {
		this.mappingOperator = mappingOperator;
	}

}

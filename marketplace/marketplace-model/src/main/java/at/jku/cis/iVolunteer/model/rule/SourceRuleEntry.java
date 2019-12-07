package at.jku.cis.iVolunteer.model.rule;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document
public class SourceRuleEntry {

	private ClassDefinition source;
	private MappingOperator mappingOperator;

	public SourceRuleEntry() {
	}
	
	public ClassDefinition getSource() {
		return source;
	}

	public void setSource(ClassDefinition source) {
		this.source = source;
	}

	public MappingOperator getMappingOperator() {
		return mappingOperator;
	}

	public void setMappingOperator(MappingOperator mappingOperator) {
		this.mappingOperator = mappingOperator;
	}

}

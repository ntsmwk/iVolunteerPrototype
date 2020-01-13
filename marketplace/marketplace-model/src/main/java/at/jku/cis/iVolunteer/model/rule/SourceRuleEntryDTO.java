package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class SourceRuleEntryDTO {

	private ClassDefinition classDefinition;

	public SourceRuleEntryDTO() {
	}

	public SourceRuleEntryDTO(ClassDefinition classDefinition) {
		super();
		this.classDefinition = classDefinition;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}
}
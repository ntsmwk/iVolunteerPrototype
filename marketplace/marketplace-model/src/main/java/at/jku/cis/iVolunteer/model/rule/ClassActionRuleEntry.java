package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

public class ClassActionRuleEntry extends SourceRuleEntry {
	
	private List<AttributeSourceRuleEntry> attributeSourceRules = new ArrayList<>();
	private ClassRuleActionType classRuleActionType;

	public ClassActionRuleEntry() {
	}

	public ClassActionRuleEntry(String classDefinitionId, Object value,
			ClassRuleActionType classRuleActionType) {
		super(classDefinitionId, value);
		this.classRuleActionType = classRuleActionType;
	}

	public ClassRuleActionType getClassRuleActionType() {
		return classRuleActionType;
	}

	public void setClassRuleActionType(ClassRuleActionType classRuleActionType) {
		this.classRuleActionType = classRuleActionType;
	}
	
	public List<AttributeSourceRuleEntry> getAttributeSourceRules() {
		return attributeSourceRules;
	}

	public void setAttributeSourceRules(List<AttributeSourceRuleEntry> attributeSourceRules) {
		this.attributeSourceRules = attributeSourceRules;
	}


}

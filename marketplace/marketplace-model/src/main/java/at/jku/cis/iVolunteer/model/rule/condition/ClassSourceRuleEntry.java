package at.jku.cis.iVolunteer.model.rule.condition;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.rule.archive.SourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class ClassSourceRuleEntry extends SourceRuleEntry {

	private List<SourceRuleEntry> attributeSourceRules = new ArrayList<>();

	public ClassSourceRuleEntry() {
	}

	public ClassSourceRuleEntry(String classDefinitionId, Object value, OperatorType operatorType) {
		super(classDefinitionId, value, operatorType);
	}
	
	public List<SourceRuleEntry> getAttributeSourceRules() {
		return attributeSourceRules;
	}

	public void setAttributeSourceRules(List<SourceRuleEntry> attributeSourceRules) {
		this.attributeSourceRules = attributeSourceRules;
	}

}

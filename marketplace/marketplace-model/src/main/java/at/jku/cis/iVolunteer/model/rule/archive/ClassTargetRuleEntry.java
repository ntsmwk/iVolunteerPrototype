package at.jku.cis.iVolunteer.model.rule.archive;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.rule.operator.ActionType;

public class ClassTargetRuleEntry extends TargetRuleEntry {
	
	private List<RuleEntry> attributes = new ArrayList<>();

	public ClassTargetRuleEntry() {
	}

	public ClassTargetRuleEntry(String classDefinitionId, ActionType actionType) {
		super(classDefinitionId, null, actionType);
	}
	
	public List<RuleEntry> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<RuleEntry> attributes) {
		this.attributes = attributes;
	}


}

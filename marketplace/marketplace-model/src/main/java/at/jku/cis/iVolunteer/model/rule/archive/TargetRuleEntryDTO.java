package at.jku.cis.iVolunteer.model.rule.archive;

import at.jku.cis.iVolunteer.model.rule.operator.ActionType;

public class TargetRuleEntryDTO extends RuleEntryDTO {

	private ActionType actionType;

	public TargetRuleEntryDTO() {
		
	}
	
	public TargetRuleEntryDTO(String key, ActionType actionType) {
		super(key, null);
		this.actionType = actionType;
	}
	
    public ActionType getActionType() {
    	return actionType;
    }
    
    public void setActionType(ActionType actionType) {
    	this.actionType = actionType;
    }
	
}
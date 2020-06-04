package at.jku.cis.iVolunteer.model.rule.archive;

import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class SourceRuleEntryDTO extends RuleEntryDTO {

	private OperatorType operatorType;

	public SourceRuleEntryDTO() {
		
	}
	
	public SourceRuleEntryDTO(String key, Object value, OperatorType operatorType) {
		super(key, value);
		this.operatorType = operatorType;
	}
	
    public OperatorType getOperatorType() {
    	return operatorType;
    }
    
    public void setOperatorType(OperatorType operatorType) {
    	this.operatorType = operatorType;
    }
	
}
package at.jku.cis.iVolunteer.model.rule.archive;

import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class SourceRuleEntry extends RuleEntry {
	
	private OperatorType operatorType;
	
	public SourceRuleEntry() {
		
	}
	
	public SourceRuleEntry(String key, Object value, OperatorType operatorType) {
		super(key, value);
		this.operatorType = operatorType;
	}

	public SourceRuleEntry(SourceRuleEntryDTO dto) {
		this(dto.getKey(), dto.getValue(), dto.getOperatorType());
	}
	
	public OperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorType OperatorType) {
		this.operatorType = OperatorType;
	}

}

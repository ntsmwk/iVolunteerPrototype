package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public abstract class Condition{
	
	private OperatorType operatorType;
	
	public Condition() {
		
	}
	
	public Condition(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

}

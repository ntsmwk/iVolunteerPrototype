package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class GeneralCondition {

	private String attributeName;
	private Object value;
	private ComparisonOperatorType operatorType;

	public GeneralCondition() {
	}

	public GeneralCondition(String attributeName, Object value, ComparisonOperatorType operator) {
		this.operatorType = operator;
		this.attributeName = attributeName;
		this.value = value;
	}

	public ComparisonOperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(ComparisonOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}

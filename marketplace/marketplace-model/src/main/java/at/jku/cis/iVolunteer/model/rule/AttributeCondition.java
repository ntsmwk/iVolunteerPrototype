package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class AttributeCondition {

	private String classPropertyId;
	private Object value;
	private ComparisonOperatorType operatorType;

	public ComparisonOperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(ComparisonOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public AttributeCondition(String classPropertyId, Object value, ComparisonOperatorType operator) {
		this.operatorType = operator;
		this.classPropertyId = classPropertyId;
		this.value = value;
	}

	public AttributeCondition(String classPropertyId, Object value) {
		this(classPropertyId, value, null);
	}

	public void setOperator(ComparisonOperatorType operator) {
		this.operatorType = operator;
	}

	public AttributeCondition() {

	}

	public String getClassPropertyId() {
		return classPropertyId;
	}

	public void setClassPropertyId(String classPropertyId) {
		this.classPropertyId = classPropertyId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}

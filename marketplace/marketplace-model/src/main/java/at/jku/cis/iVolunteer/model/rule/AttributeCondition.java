package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class AttributeCondition extends Condition {
	
	private String classPropertyId;
	private Object value;
	
	public AttributeCondition(String classPropertyId, Object value, ComparisonOperatorType operator) {
		super(operator);
		this.classPropertyId = classPropertyId;
		this.value = value;
	}
	
	public AttributeCondition() {
		super(null);
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

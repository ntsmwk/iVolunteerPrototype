package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;

public class ClassCondition extends Condition {

	private String classDefinitionId;
	private Object value;
	private List<AttributeCondition> attributeConditions = new ArrayList<AttributeCondition>();
	private AggregationOperatorType operatorType;
	private String classPropertyId;

	public ClassCondition(String classDefinitionId, Object value, AggregationOperatorType aggregationOperator) {
		this.operatorType = aggregationOperator;
		this.classDefinitionId = classDefinitionId;
		this.value = value;
	}

	public ClassCondition() {

	}

	public AggregationOperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(AggregationOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getClassPropertyId() {
		return classPropertyId;
	}
	
	public void setClassPropertyId(String classPropertyId) {
		this.classPropertyId = classPropertyId;
	}

	public List<AttributeCondition> getAttributeConditions() {
		return attributeConditions;
	}

	public void setAttributeConditions(List<AttributeCondition> attributeConditions) {
		this.attributeConditions = attributeConditions;
	}

	public void addAttributeCondition(AttributeCondition attrCondition) {
		attributeConditions.add(attrCondition);
	}

}

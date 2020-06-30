package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class ClassCondition extends Condition {
	
	private String classDefinitionId;
	private Object value;
	private List<AttributeCondition> attributeConditions;
	
	public ClassCondition(String classDefinitionId, Object value, AggregationOperatorType aggregationOperator) {
		this(aggregationOperator);
		this.classDefinitionId = classDefinitionId;
		this.value = value;
	}
	
	public ClassCondition(OperatorType operatorType) {
		super(operatorType);
		attributeConditions = new ArrayList<AttributeCondition>();
	}

	public ClassCondition() {
		attributeConditions = new ArrayList<AttributeCondition>();
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
	
	public List<AttributeCondition> getAttributeConditions(){
		return attributeConditions;
	}
	
	public void setAttributeConditions(List<AttributeCondition> attributeConditions) {
		this.attributeConditions = attributeConditions;
	}
	
	public void addAttributeCondition(AttributeCondition attrCondition) {
		attributeConditions.add(attrCondition);
	}

	
	
	
}


